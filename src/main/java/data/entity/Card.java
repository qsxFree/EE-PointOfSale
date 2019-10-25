package main.java.data.entity;

import java.util.Date;

public class Card {
    private int cardID;
    private Customer customer;
    private double credits;
    private boolean isActive;
    private Date activationDate;
    private Date lastRenewalDate;
    private Date expiryDate;
    private String pin;

    public Card(int cardID) {
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Date getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(Date lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
