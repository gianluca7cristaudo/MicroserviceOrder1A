package Productmanager.jsonModel;

import java.util.List;
import java.util.Map;

public class OrderValidation {
    private String timestamp;
    private Integer status;
    private Integer orderId;
    private List<Map<Integer,Integer>> extraArgs;

    public String getTimestamp() {
        return timestamp;
    }

    public OrderValidation setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public OrderValidation setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public OrderValidation setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public List<Map<Integer, Integer>> getExtraArgs() {
        return extraArgs;
    }

    public OrderValidation setExtraArgs(List<Map<Integer, Integer>> extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }
}
