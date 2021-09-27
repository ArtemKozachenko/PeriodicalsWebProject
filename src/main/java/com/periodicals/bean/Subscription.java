package com.periodicals.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Subscription extends AbstractBean<Integer> {
    private static final long serialVersionUID = -8133095899889248941L;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private User user;
    private Magazine magazine;

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getStartDateAsString() {
        return startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getEndDateAsString() {
        return endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Subscription() {
    }

    public Subscription(LocalDate startDate, LocalDate endDate, User user, Magazine magazine) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.magazine = magazine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", magazine=" + magazine +
                ", id=" + getId() +
                '}';
    }
}
