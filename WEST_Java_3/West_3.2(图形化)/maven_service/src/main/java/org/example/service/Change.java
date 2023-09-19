package org.example.service;

import org.example.dao.ChangeMySql;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Change {

    public static void change() {
        JFrame jFrame = new JFrame();
        jFrame.setBounds(760, 570, 400, 230);

        JComboBox typeBox = new JComboBox();
        typeBox.addItem("goods");
        typeBox.addItem("order");
        JLabel typeLabel = new JLabel("类型 ： ");
        JPanel typePanel = new JPanel();
        typePanel.add(typeLabel);
        typePanel.add(typeBox);

        JComboBox fieldBox = new JComboBox();
        JLabel fieldLabel = new JLabel("属性 ： ");
        JPanel fieldPanel = new JPanel();
        fieldPanel.add(fieldLabel);
        fieldPanel.add(fieldBox);

        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = typeBox.getSelectedItem().toString();
                fieldBox.removeAllItems();
                if ("order".equals(type)) {
                    // fieldBox.addItem("订单编号");
                    //编号应该不可改
                    fieldBox.addItem("商品编号");
                    fieldBox.addItem("下单时间");
                } else if ("goods".equals(type)) {
                    // fieldBox.addItem("商品编号");
                    fieldBox.addItem("商品名");
                    fieldBox.addItem("商品价格");
                }
            }
        });

        JLabel num1 = new JLabel("编号 ： ");
        JTextField num2 = new JTextField(20);

        JLabel data1 = new JLabel("修改成的数据 ： ");
        JTextField data2 = new JTextField(20);

        JButton jButton = new JButton("确认");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = typeBox.getSelectedItem().toString();
                String number = num2.getText();
                String changeField = fieldBox.getSelectedItem().toString();
                String changeData = data2.getText();
                new ChangeMySql().change(type,number,changeField,changeData);
            }
        });

        Box box = Box.createVerticalBox();
        box.add(typePanel);
        box.add(fieldPanel);
        box.add(num1);
        box.add(num2);
        box.add(data1);
        box.add(data2);
        box.add(jButton);

        jFrame.add(box);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    // static Scanner input = new Scanner(System.in);
    //
    // public static void change() {
    //     System.out.println(".请输入修改的类型（order or goods）、编号、要修改的属性、修改成的数据：");
    //     String type = input.next();
    //     String number = input.next();
    //     String changeItem = input.next();
    //     String changeData = input.next();
    //     new ChangeMySql().change(type,number,changeItem,changeData);
    // }
}
