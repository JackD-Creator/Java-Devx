package com.enterprise.db.model;

import java.util.List;

public class PageResult<T> {

    public List<T> data;
    public long total;
    public int page;
    public int size;
    public int totalPages;

    public static <T> PageResult<T> of(List<T> data, long total, int page, int size) {
        PageResult<T> result = new PageResult<>();
        result.data       = data;
        result.total      = total;
        result.page       = page;
        result.size       = size;
        result.totalPages = (int) Math.ceil((double) total / size);
        return result;
    }

    public boolean hasNext() {
        return page < totalPages - 1;
    }

    public boolean hasPrevious() {
        return page > 0;
    }
}
