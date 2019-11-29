/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.db;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by MAO Hieng on 9/5/18.
 * <p>
 *
 * </p>
 */
public interface AsyncDao<T, K> extends Dao<T, K> {

    AsyncResult<K> saveAsync(T entity);

    AsyncResult<Map<K, T>> saveAsync(@Nonnull T... entities);

    AsyncResult<Map<K, T>> saveAsync(@Nonnull Iterable<T> entities);

    AsyncResult<Void> deleteAsync(T entity);

    AsyncResult<Void> deleteAsync(@Nonnull Iterable<T> entities);

}
