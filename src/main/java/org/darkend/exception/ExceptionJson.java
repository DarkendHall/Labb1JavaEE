package org.darkend.exception;

import java.time.LocalDateTime;

public class ExceptionJson {

    private LocalDateTime localDateTime;
    private int errorCode;
    private String errorMsg;

    public ExceptionJson setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ExceptionJson(int errorCode, String errorMsg) {
        this.localDateTime = LocalDateTime.now();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public ExceptionJson setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ExceptionJson setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
