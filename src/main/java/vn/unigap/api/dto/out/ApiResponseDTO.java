package vn.unigap.api.dto.out;

public class ApiResponseDTO {
    private int errorCode;
    private int statusCode;
    private String message;
    private Object object;

    public ApiResponseDTO(int errorCode, int statusCode, String message, Object object) {
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.message = message;
        this.object = object;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
