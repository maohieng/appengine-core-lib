package com.jommobile.appengine.storage;

import com.jommobile.appengine.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

// TODO: 1/3/2020 implement this as a general service for helper class, for encapsulation!
public interface StorageFileUploadService {

    static final String ATTR_MAX_NUMB_IMAGE = "maxNumbImage";
    static final String ATTR_MAX_NUMB_AUDIO = "maxNumbAudio";
    // TODO: 1/3/2020 get all used key attrs here

    public static class Factory {
        public static StorageFileUploadService create(String bucketName) {
            return new StorageFileUploadServiceImp(bucketName);
        }
    }

    static String getImagesDir() {
        if (Constants.LOCAL_DEV)
            return "local_server/images/";
        else
            return "server/images/";
    }

    static String getAudiosDir(){
        if (Constants.LOCAL_DEV)
            return "local_server/audios/";
        else
            return "server/audios/";
    }

    static String getDocumentsDir(){
        if (Constants.LOCAL_DEV)
            return "local_server/documents/";
        else
            return "server/documents/";
    }

    ///////////////////////////////////////////////////////////////////////////
    // Upload Images
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Used with /fragment/input_images.jsp.
     */
    @Nullable
    public abstract List<Image> uploadImages(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException;

    /**
     * Used with /fragment/input_image.jsp.
     */
    @Nullable
    public abstract Image uploadImage(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException;

    @Nullable
    public abstract Image uploadImage(String moduleName, @Nonnull Part filePart,
                                      String imageUrl, String thumbnail, String blobName,
                                      long entityId) throws IllegalArgumentException, IOException;

    /**
     * Used with /fragment/firebase_input_images.jsp, it uses javascript to upload files.
     * Extracts the parameters and creates image thumbnails.
     */
    @Nullable
    public abstract List<Image> firebaseImagesUploaded(HttpServletRequest req);

    ///////////////////////////////////////////////////////////////////////////
    // Upload Audios
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Used with /fragment/input_audios.jsp.
     */
    @Nullable
    public abstract List<Audio> uploadAudios(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException;

    /**
     * Used with /fragment/input_audio.jsp.
     */
    @Nullable
    public abstract Audio uploadAudio(HttpServletRequest req, long entityId, String moduleName) throws IOException, ServletException;

    @Nullable
    public abstract Audio uploadAudio(String moduleName, @Nonnull Part filePart,
                                      String downloadUrl, String blobName, long entityId) throws IllegalArgumentException, IOException;

    /**
     * Used with /fragment/firebase_input_audios.jsp, it uses javascript to upload files.
     */
    @Nullable
    public abstract List<Audio> firebaseAudiosUploaded(HttpServletRequest req);

    ///////////////////////////////////////////////////////////////////////////
    // Any files
    ///////////////////////////////////////////////////////////////////////////

    @Nullable
    public abstract StorageBlob uploadFile(String fullDirRef, @Nonnull Part filePart,
                                           String downloadUrl, String blobName, long entityId) throws IllegalArgumentException, IOException;
}
