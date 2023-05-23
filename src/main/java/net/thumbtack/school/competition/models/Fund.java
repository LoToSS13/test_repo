package net.thumbtack.school.competition.models;

import java.util.Objects;

public class Fund {
    private int amount;
    private double criticalMark;


    public Fund(int amount, double criticalMark) {
        this.amount = amount;
        this.criticalMark = criticalMark;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCriticalMark() {
        return criticalMark;
    }

    public void setCriticalMark(double criticalMark) {
        this.criticalMark = criticalMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fund fund = (Fund) o;
        return amount == fund.amount && Double.compare(fund.criticalMark, criticalMark) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, criticalMark);
    }
}
