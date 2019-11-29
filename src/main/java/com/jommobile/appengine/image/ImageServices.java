/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.image;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import javax.annotation.Nonnull;

/**
 * Created by MAO Hieng on 5/29/18.
 * <p>
 *
 * </p>
 */
public final class ImageServices {

    private static class SingletonHolder {
        private static final ImagesService service = ImagesServiceFactory.getImagesService();
    }

    private static final int THUMBNAIL_SIZE = 110; // pixel

    private ImageServices() {
        //no instance
    }

    public static ImagesService getService() {
        return SingletonHolder.service;
    }

    private static String getStorageFileName(String fileName, String bucketName) {
        return "/gs/" + bucketName + "/" + fileName;
    }

    public static Image getImage(@Nonnull String fileName, @Nonnull String bucketName) {
        return ImagesServiceFactory.makeImageFromFilename(getStorageFileName(fileName, bucketName));
    }

    public static String getImageThumbnailURL(@Nonnull String fileName, @Nonnull String bucketName) throws IllegalArgumentException {
        ServingUrlOptions options = ServingUrlOptions.Builder
                .withGoogleStorageFileName(getStorageFileName(fileName, bucketName))
                .imageSize(THUMBNAIL_SIZE)
                .crop(true);

        return getService().getServingUrl(options);
    }

}
