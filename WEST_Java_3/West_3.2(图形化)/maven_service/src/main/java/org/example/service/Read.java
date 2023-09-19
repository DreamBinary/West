package org.example.service;

import org.example.dao.ReadFyMysql;
import org.example.dao.ReadMySql;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Read {

    static JFrame jFrame = new JFrame("查询");
    static String number;
    static String type;
    static String fun;

    public static void init() {
        jFrame.setBounds(760, 570, 400, 300);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        choose();
    }

    static void choose() {
        int funNum = JOptionPane.showOptionDialog(jFrame, "单项 OR 分页", "选择查询",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"单项", "分页"}, "单项");
        int typeNum = JOptionPane.showOptionDialog(jFrame, "goods OR order", "选择查询",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"goods", "order"}, "goods");
        switch (funNum) {
            case 0:
                fun = "单项";
                addEnter();
                switch (typeNum) {
                    case 0:
                        jFrame.setTitle("单项查询(goods)");
                        type = "goods";
                        break;
                    case 1:
                        jFrame.setTitle("单项查询(order)");
                        type = "order";
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                jFrame.getContentPane().removeAll();
                jFrame.repaint();
                switch (typeNum) {
                    case 0:
                        jFrame.setTitle("分页查询(goods)");
                        type = "goods";
                        break;
                    case 1:
                        jFrame.setTitle("分页查询(order)");
                        type = "order";
                        break;
                    default:
                        break;
                }
                fyRead();
                break;
            default:
                break;
        }
    }

    static void fyRead() {
        String onePageNum0 = JOptionPane.showInputDialog(jFrame, "请输入每页数据个数", "每页个数", JOptionPane.INFORMATION_MESSAGE);
        int onePageNum = Integer.parseInt(onePageNum0);

        ReadFyMysql readFyMysql = new ReadFyMysql(type, onePageNum);
        int pageNum = readFyMysql.getPageNum();
        String message = "一共 " + pageNum + " 页, 你要查询哪一页？";
        String pageWant0 = JOptionPane.showInputDialog(jFrame, message, "查询页数", JOptionPane.INFORMATION_MESSAGE);
        int pageWant = Integer.parseInt(pageWant0);
        ArrayList<String> texts = readFyMysql.getPage(pageWant);
        JOptionPane.showMessageDialog(jFrame, texts, "分页查询结果", JOptionPane.INFORMATION_MESSAGE);
    }

     static void addEnter() {
        JPanel jPanelNum = new JPanel();
        JTextField jTextNum = new JTextField(5);
        jTextNum.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if ("单项".equals(fun)) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        number = jTextNum.getText();
                        String text = new ReadMySql(type, number).read();
                        if (text.isEmpty()) {
                            JOptionPane.showMessageDialog(null,"没有此条数据哦！","查询警告",JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(jFrame, text, "单项查询结果", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "分页查询不用输入哦！", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        jPanelNum.add(jTextNum);
        jPanelNum.setBorder(new TitledBorder(new EtchedBorder(), "编号(单项查询输入)"));
        jFrame.add(jPanelNum, BorderLayout.NORTH);
        jFrame.validate();
    }
}
