package com.keeay.anepoch.base.commons.base.page;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Description: 冠美分页对象
 * Author: pany
 * Time - 2023/1/30 - 10:30
 */
public class GmPage<T> implements Serializable {
    public GmPage() {

    }

    public GmPage(Long currentPage, Long pageSize, Long totalCount, List<T> dataList) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.dataList = dataList;
        if (Objects.nonNull(totalCount) && Objects.nonNull(pageSize)) {
            this.pageCount = totalCount % this.pageSize == 0 ? (totalCount / this.pageSize) : (totalCount / this.pageSize + 1);
        }
    }

    public static <T> GmPage<T> of(Long currentPage, Long pageSize, Long totalCount, List<T> dataList) {
        return new GmPage<T>()
                .setCurrentPage(currentPage)
                .setPageSize(pageSize)
                .setDataList(dataList)
                .setTotalCount(totalCount);
    }

    /**
     * 当前页
     */
    private Long currentPage;
    /**
     * 页面数据数量大小
     */
    private Long pageSize;
    /**
     * 数据总条数
     */
    private Long totalCount;
    /**
     * 总页数
     */
    private Long pageCount;
    /**
     * 当前数据集
     */
    private List<T> dataList;
    /**
     * 入参
     */
    private T queryData;

    public Long getCurrentPage() {
        return currentPage;
    }

    public GmPage<T> setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public GmPage<T> setPageSize(Long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public GmPage<T> setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public Long getPageCount() {
        if (Objects.nonNull(totalCount) && Objects.nonNull(pageSize)) {
            return totalCount % this.pageSize == 0 ? (totalCount / this.pageSize) : (totalCount / this.pageSize + 1);
        }
        return null;
    }

    public GmPage<T> setPageCount(Long pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public GmPage<T> setDataList(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

    public T getQueryData() {
        return queryData;
    }

    public GmPage<T> setQueryData(T queryData) {
        this.queryData = queryData;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
