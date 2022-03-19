package de.androidcrypto.androidjsongsonreader;

public class PriceList {
    private String date;
    private String dateUnix;
    private String closePrice;

    public PriceList(String date, String dateUnix, String closePrice) {
        this.date = date;
        this.dateUnix = dateUnix;
        this.closePrice = closePrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateUnix() {
        return dateUnix;
    }

    public void setDateUnix(String dateUnix) {
        this.dateUnix = dateUnix;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }



}
