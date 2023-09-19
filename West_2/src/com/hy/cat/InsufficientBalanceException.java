package com.hy.cat;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public class InsufficientBalanceException extends BaseException {
    public InsufficientBalanceException() {
        super();
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }

}
