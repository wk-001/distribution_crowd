package com.wk.crowd.pojo.po;

import java.io.Serializable;

public class ReturnPO implements Serializable {
    private Integer id;

    private Integer projectid;

    private Byte type;

    private Integer supportmoney;

    private String content;

    private Integer count;

    private Integer signalpurchase;

    private Integer purchase;

    private Integer freight;

    private Byte invoice;

    private Integer returndate;

    private String describPicPath;

    private static final long serialVersionUID = 1L;

    public ReturnPO(Integer id, Integer projectid, Byte type, Integer supportmoney, String content, Integer count, Integer signalpurchase, Integer purchase, Integer freight, Byte invoice, Integer returndate, String describPicPath) {
        this.id = id;
        this.projectid = projectid;
        this.type = type;
        this.supportmoney = supportmoney;
        this.content = content;
        this.count = count;
        this.signalpurchase = signalpurchase;
        this.purchase = purchase;
        this.freight = freight;
        this.invoice = invoice;
        this.returndate = returndate;
        this.describPicPath = describPicPath;
    }

    public ReturnPO() {
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Integer supportmoney) {
        this.supportmoney = supportmoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSignalpurchase() {
        return signalpurchase;
    }

    public void setSignalpurchase(Integer signalpurchase) {
        this.signalpurchase = signalpurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Byte getInvoice() {
        return invoice;
    }

    public void setInvoice(Byte invoice) {
        this.invoice = invoice;
    }

    public Integer getReturndate() {
        return returndate;
    }

    public void setReturndate(Integer returndate) {
        this.returndate = returndate;
    }

    public String getDescribPicPath() {
        return describPicPath;
    }

    public void setDescribPicPath(String describPicPath) {
        this.describPicPath = describPicPath == null ? null : describPicPath.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectid=").append(projectid);
        sb.append(", type=").append(type);
        sb.append(", supportmoney=").append(supportmoney);
        sb.append(", content=").append(content);
        sb.append(", count=").append(count);
        sb.append(", signalpurchase=").append(signalpurchase);
        sb.append(", purchase=").append(purchase);
        sb.append(", freight=").append(freight);
        sb.append(", invoice=").append(invoice);
        sb.append(", returndate=").append(returndate);
        sb.append(", describPicPath=").append(describPicPath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}