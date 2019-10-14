package com.userlogmanager.form;

/**
 * 从前端接收到的分页参数
 */
public class PageRequest {

    private Integer currentPage = 1; //当前页码
    private Integer pageLimit = 20;  //每一页显示数据量.

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }
}
