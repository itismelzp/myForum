package com.forum.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhiping03 on 2018/9/17.
 */
public class Page implements Serializable {

    private static int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private long start;

    private List data;

    private long totalCount;

    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
    }

    public Page(long start, long totalCount, int pageSize, List data) {
        this.start = start;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.data = data;
    }

    public long getCurrentPageNO() {
        return start / pageSize + 1;
    }

    /**
     * 取总页数.
     */
    public long getTotalPageCount() {
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
     *
     * @see #getStartOfPage(int,int)
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置.
     *
     * @param pageNo   从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 该页是否有下一页.
     */
    public boolean isHasNextPage() {
        return getCurrentPageNO() < getTotalPageCount();
    }

    /**
     * 该页是否有上一页.
     */
    public boolean isHasPreviewPage() {
        return getCurrentPageNO() > 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getStart() {
        return start;
    }

    public List getData() {
        return data;
    }

    /**
     * 取总记录数.
     */
    public long getTotalCount() {
        return totalCount;
    }

}
