package parikinglot.model;

import java.util.Date;
import java.util.List;

public class Bill extends BaseEntity{

    private int amoumt;

    private Date exitTime;

    private Gate gate;

    private List<Payment> payments;

    private Token token;

    public int getAmoumt() {
        return amoumt;
    }

    public void setAmoumt(int amoumt) {
        this.amoumt = amoumt;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
