package org.spring.springboot.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * @descript 数据校验
 */
public class Assert {

    /**
     * 断言true , 不为true抛出异常
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言true , 不为true抛出异常
     * @param expression
     * @param msgCode
     * @param params
     */
    public static void isTrue(boolean expression, int msgCode, String... params) {
        if (!expression) {
            throw new BusinessException(msgCode, params);
        }
    }

    /**
     * 断言true , 不为true抛出异常
     * @param expression
     * @param msgCode
     * @param message
     */
    public static void isTrue(boolean expression, String message, int msgCode) {
        if (!expression) {
            throw new BusinessException(message,msgCode);
        }
    }

    /**
     * 断言true , 不为true抛出异常, 默认抛出炒作失败
     * @param expression
     */
    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new BusinessException("操作失败，请刷新后重试");
        }
    }


    /**
     * 断言非空字符串 , 为空抛出异常
     * @param str
     * @param message
     */
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言非空字符串 , 为空抛出异常
     * @param str
     * @param msgCode
     * @param params
     */
    public static void notBlank(String str, int msgCode, String... params) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(msgCode, params);
        }
    }

    /**
     * 断言空对象 , 不为空抛出异常
     * @param object
     * @param message
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言空对象 , 不为空抛出异常
     * @param object
     * @param msgCode
     * @param params
     */
    public static void isNull(Object object, int msgCode, String... params) {
        if (object != null) {
            throw new BusinessException(msgCode, params);
        }
    }

    /**
     * 断言空对象 , 不为空抛出异常
     * @param object
     * @param message
     * @param code
     */
    public static void isNull(Object object, String message, int code) {
        if (object != null) {
            throw new BusinessException(message, code);
        }
    }

    /**
     * 断言非空对象 , 为空抛出异常
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }
    /**
     * 断言非空对象 , 为空抛出异常
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message,int msgCode) {
        if (object == null) {
            throw new BusinessException(message,msgCode);
        }
    }

    /**
     * 断言非空对象 , 为空抛出异常
     * @param object
     * @param msgCode
     * @param params
     */
    public static void notNull(Object object, int msgCode, String... params) {
        if (object == null) {
            throw new BusinessException(msgCode, params);
        }
    }
    /**
     * 断言非空对象 , 为空抛出异常
     * @param object
     * @param msgCode
     * @param message
     */
    public static void notNull(Object object, int msgCode, String message) {
        if (object == null) {
            throw new BusinessException(message,msgCode);
        }
    }

    /**
     * 断言非空集合 , 为空抛出异常
     * @param collect
     * @param message
     * @param <T>
     */
    public static <T> void notEmpty(Collection<T> collect, String message) {
        if (collect == null || collect.size() <= 0) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言非空集合 , 为空抛出异常
     * @param collect
     * @param msgCode
     * @param params
     * @param <T>
     */
    public static <T> void notEmpty(Collection<T> collect, int msgCode, String... params) {
        if (collect == null || collect.size() <= 0) {
            throw new BusinessException(msgCode, params);
        }
    }


    /**
     * 断言空集合 , 非空抛出异常
     * @param collect
     * @param message
     * @param <T>
     */
    public static <T> void isEmpty(Collection<T> collect, String message) {
        if (collect != null && collect.size() > 0) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言空集合 , 非空抛出异常
     * @param collect
     * @param msgCode
     * @param params
     * @param <T>
     */
    public static <T> void isEmpty(Collection<T> collect, int msgCode, String... params) {
        if (collect != null && collect.size() > 0) {
            throw new BusinessException(msgCode, params);
        }
    }

    /**
     * 断言空字符串 , 为空抛出异常
     * @param str
     * @param msgCode
     * @param params
     * @param <T>
     */
    public static <T> void notEmpty(String str, int msgCode, String... params) {
        if (str == null || str.trim().length() <= 0) {
            throw new BusinessException(msgCode, params);
        }
    }

    /**
     * 断言空字符串 , 为空抛出异常
     * @param str
     * @param params
     * @param <T>
     */
    public static <T> void notEmpty(String str,  String params) {
        if (str == null || str.trim().length() <= 0) {
            throw new BusinessException(params);
        }
    }


    /**
     * 断言空字符串 , 非空抛出异常
     * @param str
     * @param message
     * @param <T>
     */
    public static <T> void isEmpty(String str, String message) {
        if (str != null && str.trim().length() > 0) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言空字符串 , 非空抛出异常
     * @param str
     * @param msgCode
     * @param params
     * @param <T>
     */
    public static <T> void isEmpty(String str, int msgCode, String... params) {
        if (str != null && str.trim().length() > 0) {
            throw new BusinessException(msgCode, params);
        }
    }
}
