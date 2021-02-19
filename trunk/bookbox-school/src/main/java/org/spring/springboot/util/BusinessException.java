package org.spring.springboot.util;

/*
 *@Descrition
 */
public class BusinessException extends BaseException {

    private int msgCode;

    private String[] params;

    public BusinessException(String message) {
        super(message, CommonConstants.EX_SERVICE_CODE);
    }

    public BusinessException(String message, int code) {
        super(message, code);
    }

    public BusinessException(int msgCode, String... params) {
        super(String.valueOf(msgCode), CommonConstants.EX_SERVICE_CODE);
        this.msgCode = msgCode;
        this.params = params;
    }


    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
