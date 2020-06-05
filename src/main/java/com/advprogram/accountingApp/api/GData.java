package main.java.com.advprogram.accountingApp.api;

import java.sql.Date;

public class GData {
    int baseSalary;
    int bonMaskan;
    int bonNagdi;
    int hagOlad;
    int payeSanavat;
    int sabetHogug;
    Date date;

    public int getSabetHogug() {
        return sabetHogug;
    }

    public void setSabetHogug(int sabetHogug) {
        this.sabetHogug = sabetHogug;
    }

    public int getPayeSanavat() {
        return payeSanavat;
    }

    public void setPayeSanavat(int payeSanavat) {
        this.payeSanavat = payeSanavat;
    }

    public int getHagOlad() {
        return hagOlad;
    }

    public void setHagOlad(int hagOlad) {
        this.hagOlad = hagOlad;
    }

    public int getBonNagdi() {
        return bonNagdi;
    }

    public void setBonNagdi(int bonNagdi) {
        this.bonNagdi = bonNagdi;
    }

    public int getBonMaskan() {
        return bonMaskan;
    }

    public void setBonMaskan(int bonMaskan) {
        this.bonMaskan = bonMaskan;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
