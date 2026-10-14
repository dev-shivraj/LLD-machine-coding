package parikinglot.model;

import parikinglot.model.enums.GateStatus;
import parikinglot.model.enums.GateType;

public class Gate extends BaseEntity{

    private String gateNo;

    private GateStatus gateStatus;

    private GateType gateType;

    private Operator operator;

    public String getGateNo() {
        return gateNo;
    }

    public void setGateNo(String gateNo) {
        this.gateNo = gateNo;
    }

    public GateStatus getGateStatus() {
        return gateStatus;
    }

    public void setGateStatus(GateStatus gateStatus) {
        this.gateStatus = gateStatus;
    }

    public GateType getGateType() {
        return gateType;
    }

    public void setGateType(GateType gateType) {
        this.gateType = gateType;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}