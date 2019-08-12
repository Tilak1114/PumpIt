package com.example.tilak.pumpit;

public class Transaction {
    String title, amount, inorout, timedate, category;

    public Transaction() {
    }

    public Transaction(String title, String amount, String inorout, String timedate, String category) {
        this.title = title;
        this.amount = amount;
        this.inorout = inorout;
        this.timedate = timedate;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInorout() {
        return inorout;
    }

    public void setInorout(String inorout) {
        this.inorout = inorout;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }
}
