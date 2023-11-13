package com.example.freeyourdebt;

public class RecycleViewModel {
    private String debtImg;
    private String debtName;
    private String debtID;
    private String debtType;
    private String debtAmount;
    private String debtPayment;

    public RecycleViewModel(String debtImg,String debtName,String debtID, String debtType, String debtAmount, String debtPayment ) {
        this.debtImg = debtImg;
        this.debtName = debtName;
        this.debtID = debtID;
        this.debtType = debtType;
        this.debtAmount = debtAmount;
        this.debtPayment = debtPayment;
    }

    public String getDebtImg() {
        return debtImg;
    }

    public String getDebtName() {
        return debtName;
    }

    public String getDebtID() {
        return debtID;
    }

    public String getDebtType() {
        return debtType;
    }

    public String getDebtAmount() {
        return debtAmount;
    }

    public String getDebtPayment() {
        return debtPayment;
    }
}

