package com.example.save;

public class Drinks {
    double drink;
    double percentage;

    public Drinks() {
    }

    public double getDrink() {
        return drink;
    }

    public void setDrink(double drink) {
        this.drink = drink;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Drinks(double drink, double percentage) {
        this.drink = drink;
        this.percentage = percentage;
    }
}
