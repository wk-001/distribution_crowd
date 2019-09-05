package com.wk.crowd.pojo.po;

import java.io.Serializable;

public class MemberLaunchInfoPO implements Serializable {
    private Integer id;

    private Integer memberid;

    private String descriptionSimple;

    private String descriptionDetail;

    private String phoneNum;

    private String serviceNum;

    private static final long serialVersionUID = 1L;

    public MemberLaunchInfoPO(Integer id, Integer memberid, String descriptionSimple, String descriptionDetail, String phoneNum, String serviceNum) {
        this.id = id;
        this.memberid = memberid;
        this.descriptionSimple = descriptionSimple;
        this.descriptionDetail = descriptionDetail;
        this.phoneNum = phoneNum;
        this.serviceNum = serviceNum;
    }

    public MemberLaunchInfoPO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getDescriptionSimple() {
        return descriptionSimple;
    }

    public void setDescriptionSimple(String descriptionSimple) {
        this.descriptionSimple = descriptionSimple == null ? null : descriptionSimple.trim();
    }

    public String getDescriptionDetail() {
        return descriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        this.descriptionDetail = descriptionDetail == null ? null : descriptionDetail.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum == null ? null : serviceNum.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberid=").append(memberid);
        sb.append(", descriptionSimple=").append(descriptionSimple);
        sb.append(", descriptionDetail=").append(descriptionDetail);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", serviceNum=").append(serviceNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}