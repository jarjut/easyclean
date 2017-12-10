package com.easyclean.easyclean;

import java.io.Serializable;

/**
 * Created by Fajrul on 10/12/2017.
 */

public class Transaction implements Serializable{
    public String transId;
    public String uid;
    public String type;
    public String laundryBag = null;
    public String bedSheet = null;
    public String bedCover = null;
    public String total = null;
    public String location = null;
    public String pickUpDate = null;
    public String pickUpTime = null;
    public String deliveryDate = null;
    public String deliveryTime = null;
    public String status = "order";

    public Transaction(String transId, String uid, String type){
        this.transId = transId;
        this.uid = uid;
        this.type = type;
    }

    public void setLaundryBag(String laundryBag){
        this.laundryBag = laundryBag;
    }

    public void setBedSheet(String bedSheet){
        this.bedSheet = bedSheet;
    }

    public void setBedCover(String bedCover){
        this.bedCover = bedCover;
    }

}
