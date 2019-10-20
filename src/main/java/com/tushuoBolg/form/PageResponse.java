package com.tushuoBolg.form;


/**
 * 返回到前端的分页数据
 */
public class PageResponse {
    private Integer total;    //数据总量
    private Integer pageSize; //每页所展示的数据条数
    private Integer currentPage;    //当前页码

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
