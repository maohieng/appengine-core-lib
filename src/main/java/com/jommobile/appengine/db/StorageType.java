package com.jommobile.appengine.db;

/**
 * Created by MAO Hieng on 9/25/18.
 * <p>
 *
 * </p>
 */
public enum StorageType {
    DATASTORE("datastore"),
    CLOUDSQL("cloudsql"),
    FIRESTORE("firestore");

    private final String name;

    StorageType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static StorageType of(String name) {
        if (name != null && !name.isEmpty())
            switch (name) {
                case "firestore":
                    return FIRESTORE;
                case "datastore":
                    return DATASTORE;
                case "cloudsql":
                    return CLOUDSQL;
                default:
                    throw new IllegalArgumentException(name + " is invalid.");
            }
        else
            throw new IllegalArgumentException("name must not be null or empty.");
    }
}
