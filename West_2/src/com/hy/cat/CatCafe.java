package com.hy.cat;

import java.time.LocalDate;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public interface CatCafe {
    void buyCat(Cat cat);

    void treatCustomer(Customer customer);

    void close(LocalDate localDate);

}
