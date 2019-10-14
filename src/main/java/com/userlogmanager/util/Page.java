package com.userlogmanager.util;

import javax.persistence.Transient;

/**
 * 分页数据
 * User: moyan
 * Date: 13-11-10
 * Time: 下午10:34
 */
public class Page<T> {

    private Integer pageNumber = 1;
    private Integer limit = 20;

    private Iterable<T> data;
    private Long rowCount;
    private Long totalPage;

    public Page() {
    }

    public Page(Integer pageNumber, Integer limit) {
        this.pageNumber = pageNumber;
        this.limit = limit;
    }

    public Page(Page page){
        this.pageNumber = page.getPageNumber();
        this.limit = page.getLimit();
    }

    public Page(DataTablesParam dataTablesParam){
        this(dataTablesParam.getStart() / dataTablesParam.getLength() + 1, dataTablesParam.getLength());
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        if(pageNumber != null){
            this.pageNumber = pageNumber;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null){
            this.limit = limit;
        }
    }

    public Integer getFirstResult(){
        return (this.pageNumber- 1 ) * limit;
    }

    public Integer getMaxResult(){
        return this.getLimit();
    }

    public Long getRowCount() {
        return rowCount;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
        if(rowCount != null){
            if(rowCount % limit == 0){
                this.totalPage = rowCount / limit;
            }else{
                this.totalPage = rowCount / limit + 1;
            }
        }
    }

    public Iterable<T> getData() {
        return data;
    }

    public void setData(Iterable<T> data) {
        this.data = data;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 获得分页起始行数用于limit x,y 中的x.
     * @return
     */
    public long startRowNumber(){
        if(limit != null && pageNumber != null){
            return limit*(pageNumber-1);
        }
        return 0;
    }

    @Transient
    public long getStartRowNumber(){
        return startRowNumber();
    }
}
