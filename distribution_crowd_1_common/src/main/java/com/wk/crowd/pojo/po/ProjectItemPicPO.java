package com.wk.crowd.pojo.po;

import java.io.Serializable;

public class ProjectItemPicPO implements Serializable {
    private Integer id;

    private Integer projectid;

    private String itemPicPath;

    private static final long serialVersionUID = 1L;

    public ProjectItemPicPO(Integer id, Integer projectid, String itemPicPath) {
        this.id = id;
        this.projectid = projectid;
        this.itemPicPath = itemPicPath;
    }

    public ProjectItemPicPO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getItemPicPath() {
        return itemPicPath;
    }

    public void setItemPicPath(String itemPicPath) {
        this.itemPicPath = itemPicPath == null ? null : itemPicPath.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectid=").append(projectid);
        sb.append(", itemPicPath=").append(itemPicPath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}