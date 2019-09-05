package com.wk.crowd.pojo.po;

import java.io.Serializable;

public class TagPO implements Serializable {
    private Integer id;

    private Integer pid;

    private String name;

    private static final long serialVersionUID = 1L;

    public TagPO(Integer id, Integer pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    public TagPO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}