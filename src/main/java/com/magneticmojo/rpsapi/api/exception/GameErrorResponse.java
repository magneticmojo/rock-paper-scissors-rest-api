package com.magneticmojo.rpsapi.api.exception;

public class GameErrorResponse {

    // TODO -> KANSKE TA BORT DENNA
    private String errorCode;
    private String errorMessage;

    public GameErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
