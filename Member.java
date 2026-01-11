package com.Online_BookStore.Satya;


import java.time.LocalDate;

public class Member {

    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate startingDate;
    private LocalDate endDate;

    public Member(){}

    public Member(String name, String email, String phoneNumber, LocalDate startingDate, LocalDate endDate) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.startingDate = startingDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", startingDate=" + startingDate +
                ", endDate=" + endDate +
                '}';
    }
}
