/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.storage;

import com.google.cloud.storage.Blob;

import javax.annotation.Nonnull;

/**
 * Created by MAO Hieng on 5/30/18.
 * <p>
 *
 * </p>
 */
public class Image extends StorageBlob {

    String thumbnail;

    /**
     * Default constructor.
     **/
    public Image() {
        super(null, null);
    }

    public Image(String link, String blob) {
        super(link, blob);
    }

    public Image(String link, String blob, String thumbnail) {
        this(link, blob);
        this.thumbnail = thumbnail;
    }

    public Image(@Nonnull Blob blob) {
        super(blob);
    }

    public Image(@Nonnull Blob blob, String thumbnail) {
        this(blob.getMediaLink(), blob.getName(), thumbnail);
    }
//
//    /**
//     * @implNote Backend used.
//     */
//    public static Image of(@Nonnull Blob blob, @Nullable String thumbnail) {
//        return new Image(blob.getMediaLink(), thumbnail, blob.getName());
//    }
//
//    @Nullable
//    public static Image of(String link, String thumbnail, String blob) {
//        if (link == null || link.isEmpty()) {
//            return null;
//        }
//
//        return new Image(link, thumbnail, blob);
//    }

    public String getThumbnail() {
        return thumbnail;
    }
}
