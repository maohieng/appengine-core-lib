package com.jommobile.appengine.db;

import com.jommobile.appengine.utils.Logs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by MAO Hieng on 9/10/18.
 * <p>
 * Base database class
 * </p>
 */
public abstract class JomDatabase {

    private static final Logger log = Logs.makeLogger(JomDatabase.class);

    private final MigrationContainer migrationContainer;

    protected JomDatabase(Builder b) {
        this.migrationContainer = b.migrationContainer;
    }

    public abstract Object createKeyOf(Class<?> kindClass, long id, @Nullable Object parentKey);

    public abstract Class<?> getParentKindOf(Class<?> entityClass);

//    public Object getIdOfEntity(Object entityObj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        if (entityObj instanceof LongId) {
//            LongId longIdEntity = (LongId) entityObj;
//            return longIdEntity.getId();
//        } else if (entityObj instanceof StringId) {
//            StringId stringIdEntity = (StringId) entityObj;
//            return stringIdEntity.getId();
//        } else {
//            Class<?> entityObjClass = entityObj.getClass();
//
//            return entityObjClass.getMethod("getId").invoke(entityObj);
//        }
//    }

    public abstract int getVersion();

    /**
     * Initializes database SDK if required.
     * It should be called at any Servlet listener of the server's bootstrap.
     */
    public abstract void initialize();

    protected boolean checkAndMigrate(int dbVersion) {
        final int newVersion = getVersion();
        log.info("Check migration: dbVersion=" + dbVersion + ", currentVersion=" + newVersion);
        if (dbVersion >= newVersion)
            return false;

        Map<Integer, Migration> targetMap = migrationContainer.migrations.get(dbVersion);
        if (targetMap != null) {
            Migration migration = targetMap.get(newVersion);
            if (migration != null) {
                log.info("Start migration: " + migration);
                migration.migrate(this);
                return true;
            }
        }

        return false;
    }

    public static abstract class Builder<T extends JomDatabase> {
        final MigrationContainer migrationContainer;

        public Builder() {
            this.migrationContainer = new MigrationContainer();
        }

        public Builder<T> addMigrations(@Nonnull Migration... migrations) {
            this.migrationContainer.addMigrations(migrations);
            return this;
        }

        public abstract T build();
    }

    static class MigrationContainer {
        /**
         * start of end of migration
         */
        private Map<Integer, Map<Integer, Migration>> migrations = new HashMap<>();

        void addMigrations(@Nonnull Migration... migrations) {
            for (Migration migration : migrations) {
                addMigration(migration);
            }
        }

        private void addMigration(Migration migration) {
            final int start = migration.startVersion;
            final int end = migration.endVersion;
            Map<Integer, Migration> targetMap = this.migrations.computeIfAbsent(start, k -> new HashMap<>());
            Migration existing = targetMap.get(end);
            if (existing != null) {
                // log override existing with migration;
                targetMap.replace(end, migration);
            } else {
                targetMap.put(end, migration);
            }
        }

    }
}
