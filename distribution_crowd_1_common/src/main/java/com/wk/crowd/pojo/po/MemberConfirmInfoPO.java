package com.wk.crowd.pojo.po;

import java.io.Serializable;

public class MemberConfirmInfoPO implements Serializable {
    private Integer id;

    private Integer memberid;

    private String paynum;

    private String cardnum;

    private static final long serialVersionUID = 1L;

    public MemberConfirmInfoPO(Integer id, Integer memberid, String paynum, String cardnum) {
        this.id = id;
        this.memberid = memberid;
        this.paynum = paynum;
        this.cardnum = cardnum;
    }

    public MemberConfirmInfoPO() {
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

    public String getPaynum() {
        return paynum;
    }

    public void setPaynum(String paynum) {
        this.paynum = paynum == null ? null : paynum.trim();
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum == null ? null : cardnum.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberid=").append(memberid);
        sb.append(", paynum=").append(paynum);
        sb.append(", cardnum=").append(cardnum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}