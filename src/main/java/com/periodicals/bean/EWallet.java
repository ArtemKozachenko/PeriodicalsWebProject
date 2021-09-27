package com.periodicals.bean;

import java.math.BigDecimal;

public class EWallet extends AbstractBean<Integer> {
    private static final long serialVersionUID = -8156283465450583882L;

    private BigDecimal amountOfMoney;

    public EWallet() {
    }

    public EWallet(Integer id, BigDecimal amountOfMoney) {
        super(id);
        this.amountOfMoney = amountOfMoney;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(BigDecimal amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public String toString() {
        return "EWallet{" +
                "amountOfMoney=" + amountOfMoney +
                ", id='" + getId() +
                '}';
    }
}
