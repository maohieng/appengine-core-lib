package com.jommobile.appengine.db;

import javax.annotation.Nonnull;

/**
 * Base class for a database migration.
 *
 * @author maohieng on 1/14/19.
 */
public abstract class Migration {

    public final int startVersion;
    public final int endVersion;

    public Migration(int startVersion, int endVersion) {
        this.startVersion = startVersion;
        this.endVersion = endVersion;
    }

    public abstract void migrate(@Nonnull JomDatabase database);

    @Override
    public String toString() {
        return "Migration(" + startVersion + ", " + endVersion + ")";
    }
}
