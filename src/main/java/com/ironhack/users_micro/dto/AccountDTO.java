package com.ironhack.users_micro.dto;


public class AccountDTO {
    private Integer id;
    private Integer userId;
    private Double balance;
    private String accountType;

    public AccountDTO() {
    }

    public AccountDTO(Integer id, Integer userId, Double balance, String accountType) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.accountType = accountType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
