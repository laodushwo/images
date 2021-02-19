package org.spring.springboot.util;
/**
 * @author DS
 * @time 2018/04/02.
 */
public enum ApplicationType {

    JSON("application/json"), XML("application/xml"), TEXT("text/xml"), FORM("application/x-www-form-urlencoded");

    private String type;

    private ApplicationType(String type) {
        this.type = type;
    }

    public String val() {
        return type;
    }

}
