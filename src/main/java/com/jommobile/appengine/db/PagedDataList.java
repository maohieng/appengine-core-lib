package com.jommobile.appengine.db;

import java.util.List;

/**
 * Created by MAO Hieng on 10/25/18.
 * <p>
 *
 * </p>
 */
public class PagedDataList<T> implements PagedData<T> {

    /**
     * Builder of {@link PagedDataList}
     */
    public static class Builder<T> {
        private List<T> items;
        private String nextPageToken;
        private int maxItemCount;
        private String startedToken;
        private int limitCount;

        public Builder<T> setItems(List<T> items) {
            this.items = items;
            return this;
        }

        public Builder<T> setNextPageToken(String nextPageToken) {
            this.nextPageToken = nextPageToken;
            return this;
        }

        public Builder<T> setMaxItemCount(int maxItemCount) {
            this.maxItemCount = maxItemCount;
            return this;
        }

        public Builder<T> setStartedPageToken(String startedToken) {
            this.startedToken = startedToken;
            return this;
        }

        public Builder<T> setLimitItemCount(int limitCount) {
            this.limitCount = limitCount;
            return this;
        }

        public PagedDataList<T> build() {
            return new PagedDataList<T>(items, nextPageToken, limitCount, startedToken, maxItemCount);
        }
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    private final List<T> items;
    private final String nextPageToken;
    private final int maxItemCount;
    private final int limitItemCount;
    private final String startedToken;

    protected PagedDataList(List<T> items, String nextPageToken, int limitItemCount, String startedToken, int maxItemCount) {
        this.items = items;
        this.nextPageToken = nextPageToken;
        this.limitItemCount = limitItemCount;
        this.startedToken = startedToken;
        this.maxItemCount = maxItemCount;
    }

    @Override
    public List<T> getItems() {
        return items;
    }

    @Override
    public String getNextPageToken() {
        return nextPageToken;
    }

    @Override
    public int getMaxItemCount() {
        return maxItemCount;
    }

    @Override
    public int getLimitItemCount() {
        return limitItemCount;
    }

    @Override
    public String getStartedPageToken() {
        return startedToken;
    }
}
