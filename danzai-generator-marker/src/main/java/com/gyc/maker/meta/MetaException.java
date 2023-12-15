package com.gyc.maker.meta;

/**
 * ClassName: MetaException
 * Package: com.gyc.maker.meta
 * Description: meta异常类
 *
 * @Author gyc
 * @Create 2023/12/15 9:39
 * @Version 1.0
 */
public class MetaException extends RuntimeException{

    public MetaException(String message) {
        super(message);
    }

    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }

}
