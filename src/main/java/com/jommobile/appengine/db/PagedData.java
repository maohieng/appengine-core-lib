package com.jommobile.appengine.db;

import java.util.List;

public interface PagedData<T> {

    /**
     * Gets next page token if available.
     *
     * @return
     */
    public String getNextPageToken();

    /**
     * Gets paged data as {@link List}.
     *
     * @return
     */
    public List<T> getItems();

    /**
     * Gets maximum item if available from the given query.
     *
     * @return
     */
    public int getMaxItemCount();

    /**
     * Gets item count limited per page.
     *
     * @return
     */
    public int getLimitItemCount();

    public String getStartedPageToken();

}
