/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.storage;

import com.google.api.gax.paging.Page;
import com.google.api.gax.retrying.RetrySettings;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import org.threeten.bp.Duration;

import javax.annotation.Nonnull;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MAO Hieng on 5/22/18.
 * <p>
 * A single tone class to store a storage service instance and provides the APIs to manage file(s).
 * </p>
 */
public class StorageServiceManager {

    private static class LazyHolder {
        static final StorageServiceManager INSTANCE = new StorageServiceManager();
    }

    public static StorageServiceManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    // https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/bookshelf

    // See https://cloud.google.com/storage/docs/reference/libraries#client-libraries-install-java

    // https://github.com/GoogleCloudPlatform/google-cloud-java/blob/master/google-cloud-examples/src/main/java/com/google/cloud/examples/storage/StorageExample.java

    // private static final Logger logger = Logger.getLogger(StorageServiceManager.class.getSimpleName());

    /**
     * Used below to determine the size of chucks to read in. Should be > 1kb and < 10MB
     */
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    private final Storage storage;

    private StorageServiceManager() {
        if (LazyHolder.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated.");
        }

        /**
         * This is where backoff parameters are configured. Here it is aggressively retrying with
         * backoff, up to 10 times but taking no more that 15 seconds total to do so.
         */
        RetrySettings retrySettings = RetrySettings.newBuilder()
                .setInitialRetryDelay(Duration.ofMillis(20))
                .setMaxRetryDelay(Duration.ofMillis(30))
                .setMaxAttempts(10)
                .setTotalTimeout(Duration.ofSeconds(15))
                .build();

        storage = StorageOptions
                /*.getDefaultInstance()*/
                .newBuilder()
                .setRetrySettings(retrySettings)
                .build()
                .getService();
    }

//    public Storage storage() {
//        return storage;
//    }

    public List<Bucket> listBuckets() {
        Page<Bucket> buckets = storage.list(Storage.BucketListOption.pageSize(5)/*,Storage.BucketListOption.prefix(prefix)*/);
        List<Bucket> bucketList = new ArrayList<>();
        for (Bucket bucket : buckets.iterateAll()) {
            bucketList.add(bucket);
        }

        return bucketList;
    }

    @SuppressWarnings("deprecation")
    public Blob uploadFile(@Nonnull Part filePart, @Nonnull BlobInfo blobInfo) throws IOException {
        return storage.create(blobInfo, filePart.getInputStream());
    }

    public Blob uploadFile(@Nonnull Part filePart, @Nonnull String fileName, @Nonnull String bucketName) throws IOException {
        BlobInfo blobInfo = BlobInfo
                .newBuilder(bucketName, fileName)
                // Modify access list to allow all users with link to read file
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                .setContentType(filePart.getContentType())
                .build();

        return uploadFile(filePart, blobInfo);
    }

    public void uploadBigFile(@Nonnull Part filePart, BlobInfo blobInfo) throws IOException {
        // https://github.com/GoogleCloudPlatform/google-cloud-java/issues/1232
        WriteChannel writer = storage.writer(blobInfo);
        InputStream input = filePart.getInputStream();
        boolean uploadFailed = false;
        Exception writeException = null;

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        try {
            while ((bytesRead = input.read(buffer)) >= 0) {
                writer.write(ByteBuffer.wrap(buffer, 0, bytesRead));
            }
        } catch (Exception e) {
            uploadFailed = true;
            writeException = e;
        }

        if (!uploadFailed) {
            writer.close();
        } else {
            throw new IOException(writeException);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Transfer the data from the inputStream to the outputStream. Then close both streams.
     */
    private void copy(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = input.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
        } finally {
            input.close();
            output.close();
        }
    }

}
