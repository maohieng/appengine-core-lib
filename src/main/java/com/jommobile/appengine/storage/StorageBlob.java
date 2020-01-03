/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.storage;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.cloud.storage.Blob;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;

/**
 * Created by MAO Hieng on 5/30/18.
 * <p>
 * Model represent storage blob info in our system. It's used as embedded entity as well.
 * </p>
 */
public class StorageBlob {

    // Note: do not final this variable. It will not saved into Datastore. Why? I don't know.
    String link;
    /**
     * File name in Google Storage.
     */
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    String blob;

    public StorageBlob(String link, String blob) {
        this.link = link;
        this.blob = blob;
    }

    public StorageBlob(@Nonnull Blob blob) {
        this(blob.getMediaLink(), blob.getName());
    }

    public String getLink() {
        return link;
    }


    public String getBlob() {
        return blob;
    }

    @Override
    public String toString() {
        return link;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof StorageBlob)) {
            return false;
        }

        StorageBlob sb = (StorageBlob) obj;

        if (Strings.isNullOrEmpty(sb.link) && Strings.isNullOrEmpty(sb.blob)) {
            return Strings.isNullOrEmpty(link) && Strings.isNullOrEmpty(blob);
        } else if (Strings.isNullOrEmpty(sb.link)) {
            return Strings.isNullOrEmpty(link) && sb.blob.equals(blob);
        } else if (Strings.isNullOrEmpty(sb.blob)) {
            return sb.link.equals(link) && Strings.isNullOrEmpty(blob);
        } else {
            return sb.link.equals(link) && sb.blob.equals(blob);
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (blob != null ? blob.hashCode() : 0);
        return result;
    }
}
