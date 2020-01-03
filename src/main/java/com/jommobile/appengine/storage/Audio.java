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
public class Audio extends StorageBlob {

    /**
     * Default constructor.
     **/
    public Audio() {
        this(null, null);
    }

    public Audio(String link, String blob) {
        super(link, blob);
    }

    public Audio(@Nonnull Blob blob) {
        super(blob);
    }

//    /**
//     * @implNote Backend used.
//     */
//    public static Audio create(@Nonnull Blob blob) {
//        return new Audio(blob);
//    }
//
//    @Nullable
//    public static Audio create(String link, String blob) {
//        if (link == null || link.isEmpty()) {
//            return null;
//        }
//
//        return new Audio(link, blob);
//    }
}
