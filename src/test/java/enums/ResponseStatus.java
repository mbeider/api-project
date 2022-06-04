package enums;

public enum ResponseStatus {
    BAD_REQUEST("BAD_REQUEST"),
    SQL_ERROR("SQL_ERROR");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
}
