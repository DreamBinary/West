package org.example.service;


import org.example.dao.AddMySql;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Add {

    static JFrame jFrame = new JFrame("添加");
    static JPanel info = new JPanel();

    public static void add() {
        jFrame.setBounds(760, 570, 400, 300);

        JComboBox typeBox = new JComboBox();
        typeBox.addItem("good");
        typeBox.addItem("order");
        JLabel typeLabel = new JLabel("类型 ： ");
        JPanel typePanel = new JPanel();
        typePanel.add(typeLabel);
        typePanel.add(typeBox);
        jFrame.add(typePanel, BorderLayout.NORTH);

        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info.removeAll();
                //输入报错 再次输入时候停留在上一次错误
                if ("order".equals(typeBox.getSelectedItem().toString())) {
                    addOrder();
                } else if ("good".equals(typeBox.getSelectedItem().toString())) {
                    addGood();
                }
            }
        });

        jFrame.add(info);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

     static void addOrder() {
         info.updateUI();
         info.setBorder(new TitledBorder(new EtchedBorder(),"订单信息"));

         JLabel orderNum1 = new JLabel("订单编号 ： ");
         JTextField orderNum2 = new JTextField(20);

         JLabel goodNum1 = new JLabel("商品编号 ： ");
         JTextField goodNum2 = new JTextField(20);

         JXDatePicker orderTime = new JXDatePicker();
         orderTime.setDate(new Date());
         orderTime.setFormats(new SimpleDateFormat("yyyy-MM-dd"));

         Box box = Box.createVerticalBox();
         box.add(orderNum1);
         box.add(orderNum2);
         box.add(goodNum1);
         box.add(goodNum2);
         box.add(orderTime);

         info.add(box);

         JButton jButton = new JButton("确定");
         jButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String orderNum = orderNum2.getText();
                 String goodNum = goodNum2.getText();
                 LocalDate orderTime0 = orderTime.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                 String orderTime1 = orderTime0.toString();
                 new AddMySql("order",orderNum,goodNum,orderTime1).add();
             }
         });
         jFrame.add(jButton,BorderLayout.SOUTH);
    }

    static void addGood() {
        info.updateUI();
        info.setBorder(new TitledBorder(new EtchedBorder(),"商品信息"));

        JLabel goodNum1 = new JLabel("商品编号 ： ");
        JTextField goodNum2 = new JTextField(20);

        JLabel goodName1 = new JLabel("商品名 ： ");
        JTextField goodName2 = new JTextField(20);

        JLabel goodPrice1 = new JLabel("商品价格 ： ");
        JTextField goodPrice2 = new JTextField(20);

        Box box = Box.createVerticalBox();
        box.add(goodNum1);
        box.add(goodNum2);
        box.add(goodName1);
        box.add(goodName2);
        box.add(goodPrice1);
        box.add(goodPrice2);

        info.add(box);

        JButton jButton = new JButton("确定");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String goodNum = goodNum2.getText();
                String goodName = goodName2.getText();
                String goodPrice = goodPrice2.getText();
                new AddMySql("goods",goodName,goodNum,goodPrice).add();
            }
        });
        jFrame.add(jButton,BorderLayout.SOUTH);

    }
}
