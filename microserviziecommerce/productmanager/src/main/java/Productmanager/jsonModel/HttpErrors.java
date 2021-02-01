package Productmanager.jsonModel;

public class HttpErrors {
    private String timestamp;
    private String sourceIp;
    private String service;
    private String request;
    private Integer error;

    public String getTimestamp() {
        return timestamp;
    }

    public HttpErrors setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public HttpErrors setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
        return this;
    }

    public String getService() {
        return service;
    }

    public HttpErrors setService(String products) {
        this.service = products;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public HttpErrors setRequest(String request) {
        this.request = request;
        return this;
    }

    public Integer getError() {
        return error;
    }

    public HttpErrors setError(Integer error) {
        this.error = error;
        return this;
    }
}
