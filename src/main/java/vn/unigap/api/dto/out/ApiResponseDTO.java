package vn.unigap.api.dto.out;

public class ApiResponseDTO {
    private int statusCode;
    private String errorCode;
    private String message;
    private Object object;

    public ApiResponseDTO(int statusCode, String errorCode, String message, Object object) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.object = object;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
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
