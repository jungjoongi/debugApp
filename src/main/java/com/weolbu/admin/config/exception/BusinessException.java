package com.weolbu.admin.config.exception;

public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 3938577448246616516L;

    /** 에러코드*/
    private String errorCode;

    /** redirect URL */
    private String redirectUrl;
    /** 에러 메시지 */
    private String errorMsg;


    public BusinessException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BusinessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(String errorCode, String errorMsg, String redirectUrl) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.redirectUrl = redirectUrl;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorArgsMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


}
