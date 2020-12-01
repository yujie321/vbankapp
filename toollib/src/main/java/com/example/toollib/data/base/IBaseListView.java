package com.example.toollib.data.base;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public interface IBaseListView extends IBaseView {

    /**
     * 停止刷新
     */
    void finishRefresh();

    /**
     * 加载失败
     */
    void loadError();


    /**
     * 加载结束
     */
    void loadMoreEnd();

    /**
     * 加载完成  没有更多数据
     */
    void loadMoreComplete();

    /**
     * 页码
     * @return int
     */
    int getPage();

    /**
     * 页码
     */
    void setPage(int page);
}
