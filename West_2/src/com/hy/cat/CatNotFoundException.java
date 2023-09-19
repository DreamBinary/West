package com.hy.cat;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public class CatNotFoundException extends BaseException {
    public CatNotFoundException() {
        super();
    }

    public CatNotFoundException(String msg) {
        super(msg);
    }

}
