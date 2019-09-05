package com.wk.crowd.pojo.po;

import java.io.Serializable;

public class ProjectPO implements Serializable {
    private Integer id;

    private String projectName;

    private String projectDescription;

    private Integer money;

    private Integer day;

    private Byte status;

    private String deploydate;

    private Long supportmoney;

    private Integer supporter;

    private Integer completion;

    private Integer memberid;

    private String createdate;

    private Integer follower;

    private String headerPicturePath;

    private static final long serialVersionUID = 1L;

    public ProjectPO(Integer id, String projectName, String projectDescription, Integer money, Integer day, Byte status, String deploydate, Long supportmoney, Integer supporter, Integer completion, Integer memberid, String createdate, Integer follower, String headerPicturePath) {
        this.id = id;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.money = money;
        this.day = day;
        this.status = status;
        this.deploydate = deploydate;
        this.supportmoney = supportmoney;
        this.supporter = supporter;
        this.completion = completion;
        this.memberid = memberid;
        this.createdate = createdate;
        this.follower = follower;
        this.headerPicturePath = headerPicturePath;
    }

    public ProjectPO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription == null ? null : projectDescription.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDeploydate() {
        return deploydate;
    }

    public void setDeploydate(String deploydate) {
        this.deploydate = deploydate == null ? null : deploydate.trim();
    }

    public Long getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Long supportmoney) {
        this.supportmoney = supportmoney;
    }

    public Integer getSupporter() {
        return supporter;
    }

    public void setSupporter(Integer supporter) {
        this.supporter = supporter;
    }

    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public String getHeaderPicturePath() {
        return headerPicturePath;
    }

    public void setHeaderPicturePath(String headerPicturePath) {
        this.headerPicturePath = headerPicturePath == null ? null : headerPicturePath.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectName=").append(projectName);
        sb.append(", projectDescription=").append(projectDescription);
        sb.append(", money=").append(money);
        sb.append(", day=").append(day);
        sb.append(", status=").append(status);
        sb.append(", deploydate=").append(deploydate);
        sb.append(", supportmoney=").append(supportmoney);
        sb.append(", supporter=").append(supporter);
        sb.append(", completion=").append(completion);
        sb.append(", memberid=").append(memberid);
        sb.append(", createdate=").append(createdate);
        sb.append(", follower=").append(follower);
        sb.append(", headerPicturePath=").append(headerPicturePath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}