package com.hy.cat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public class MyCatCafe implements CatCafe {

    String name;
    double balance;
    int profitOne = 0;
    ArrayList<Cat> myCat = new ArrayList<>();
    ArrayList<Customer> myCustomer = new ArrayList<>();

    @Override
    public String toString() {
        return "MyCatCafe{" +
                "balance=" + balance +
                ", myCat=" + myCat +
                ", myCustomer=" + myCustomer +
                '}';
    }

    @Override
    public void buyCat(Cat cat) throws InsufficientBalanceException {
        if (balance < cat.price) {
            throw new InsufficientBalanceException("余额不足 无法购买");
        }
        balance -= cat.price;
        myCat.add(cat);
    }

    @Override
    public void treatCustomer(Customer customer) {
        Random random = new Random();
        try {
            if (myCat.isEmpty()) {
                throw new CatNotFoundException("无猫可RUA");
            }
            System.out.println("被 " + customer.name + " RUA猫咪的信息：");
            for (int i = 0; i < customer.ruaNum; i++) {
                int j = random.nextInt(myCat.size());
                System.out.println((myCat.get(j)));
            }
            balance += 15 * customer.ruaNum;
            myCustomer.add(customer);
        } catch (CatNotFoundException e) {
            e.printStackTrace();
            System.out.println("---无猫可RUA---");
        }

    }

    @Override
    public void close(LocalDate localDate) {
        System.out.println(localDate + " 一天顾客信息：");
        for (Customer aCustomer : myCustomer) {
            if (aCustomer.timeGet.isEqual(localDate)) {
                System.out.println(aCustomer);
                profitOne += aCustomer.ruaNum * 15;
            }
        }
        System.out.println(localDate + " 一天的利息为：" + profitOne);
    }
}
