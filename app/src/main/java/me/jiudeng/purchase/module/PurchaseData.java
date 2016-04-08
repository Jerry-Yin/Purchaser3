package me.jiudeng.purchase.module;

import java.math.BigDecimal;

/**
 * Created by Yin on 2016/3/29.
 * 所有清单信息
 */
public class PurchaseData {
    /**
     * TODO
     * Json 数据模型
     *
     InfoId       int       //采购员采购表id
     PurchaseId   int       //采购单id
     SellPrize    float   //线上售价              3
     BuyPrize     float   //采购价                4
     NeedNumber   float64   //定购量            6
     Operator     string    //采购员
     ItemId       int       //物品id
     ItemName     string    //物品名称              1
     Unit         string    //单位规格              2
     Remark       string    //备注
     TheoryTime   time.Time //仓库总采购单生成时间
     PurchaseTime time.Time //采购员采购单生成时间

     /** 提交的数据格式(4个)
     PurchaseTheory int       //采购单id                       <--->PurchasedId</--->   Id
     Price          int       //单价(单位厘 1/1000元) X1000    <--->自己填的</--->      Price
     Number         float64   //采购数量                       <--->自己填的</--->      Number
     Operator       string    //采购员                                                  Operator

     CreateTime     time.Time //创建时间
     */

    private int InfoId;
    private int PurchaseId;
    private float SellPrice;
    private float BuyPrice;
    private float NeedNumber;   //订购量
    private float MountPur; //采购量 ，返回的数据中没有的
    private float MoneyPur;

    private String Operator;
    private int ItemId;
    private String ItemName;
    private String Unit;
    private String Remark;

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        this.ItemName = itemName;
    }

    public int getInfoId() {
        return InfoId;
    }

    public void setInfoId(int infoId) {
        InfoId = infoId;
    }

    public int getPurchaseId() {
        return PurchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        PurchaseId = purchaseId;
    }

    public float getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(float sellPrice) {
        SellPrice = sellPrice;
    }

    public float getBuyPrice() {
        return BuyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        BuyPrice = buyPrice;
    }

    public float getNeedNumbre() {
        return NeedNumber;
    }

    public void setNeedNumbre(float needNumbre) {
        NeedNumber = needNumbre;
    }

    public float getMountPur() {
        return MountPur;
    }

    public void setMountPur(float mountPur) {
        MountPur = mountPur;
    }

    public float getMoneyPur() {
        return MoneyPur;
    }

    public void setMoneyPur(float moneyPur) {
        MoneyPur = moneyPur;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
