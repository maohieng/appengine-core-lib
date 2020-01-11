/*
 * Copyright (c) 2018. Boombile.
 */

package com.jommobile.appengine.db;

import javax.annotation.Nonnull;
import java.util.Map;

public interface Dao<T, K> {

    T loadByKey(K key);

    K save(T entity);

    Map<K, T> save(@Nonnull T... entities);

    Map<K, T> save(@Nonnull Iterable<T> entities);

    void delete(T obj);

    void delete(@Nonnull Iterable<T> obj);

}
