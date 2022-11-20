package com.github.CCweixiao.hbase.sdk.common.reflect.model;

/**
 * @author leojie 2022/11/20 11:30
 */
public class Asia {
    private boolean isLike;
    private boolean visit;
    protected long area;

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }
}
