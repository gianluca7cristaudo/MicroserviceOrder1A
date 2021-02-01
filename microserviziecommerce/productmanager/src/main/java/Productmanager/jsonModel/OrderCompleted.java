package Productmanager.jsonModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrderCompleted implements Serializable {
    private Integer orderId;
    private List<Map<Integer,Integer>> products;
    private Double total;
    private String shippingAddress;
    private String billingAddress;
    private Integer userId;
    private List<Map<Integer,Integer>> extraArgs;

    public Integer getOrderId() {
        return orderId;
    }

    public OrderCompleted setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public List<Map<Integer, Integer>> getProducts() {
        return products;
    }

    public OrderCompleted setProducts(List<Map<Integer, Integer>> products) {
        this.products = products;
        return this;
    }

    public Double getTotal() {
        return total;
    }

    public OrderCompleted setTotal(Double total) {
        this.total = total;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public OrderCompleted setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public OrderCompleted setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public OrderCompleted setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public List<Map<Integer, Integer>> getArgs() {
        return extraArgs;
    }

    public OrderCompleted setArgs(List<Map<Integer, Integer>> args) {
        this.extraArgs = args;
        return this;
    }

    @Override
    public String toString() {
        return "OrderCompleted{" +
                "orderId=" + orderId +
                ", products=" + products +
                ", total=" + total +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", userId=" + userId +
                ", args=" + extraArgs +
                '}';
    }

}
