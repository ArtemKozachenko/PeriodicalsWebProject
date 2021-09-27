package com.periodicals.bean;

import com.periodicals.constant.type.Role;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User extends AbstractBean<Integer> {
    private static final long serialVersionUID = -8731283059997732282L;

    private String login;
    private String password;
    private String salt;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String status;
    private Role role;
    private LocalDate creationDate;
    private EWallet wallet;
    private List<Subscription> subscriptions;

    public User() {
    }

    public User(Integer id, String login, String password, String salt, String email,
                String firstName, String lastName, String gender, String status, Role role, EWallet wallet) {
        super(id);
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.status = status;
        this.role = role;
        this.wallet = wallet;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getCreationDateAsString() {
        return creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public EWallet getWallet() {
        return wallet;
    }

    public void setWallet(EWallet eWallet) {
        this.wallet = eWallet;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", roleName='" + role + '\'' +
                ", creationDate=" + creationDate +
                ", wallet=" + wallet +
                ", subscriptions=" + subscriptions +
                ", id=" + getId() +
                '}';
    }

}
