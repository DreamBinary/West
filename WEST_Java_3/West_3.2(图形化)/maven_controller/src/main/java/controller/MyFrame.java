package controller;

import org.example.dao.ConCloMySql;
import org.example.service.Add;
import org.example.service.Change;
import org.example.service.Delete;
import org.example.service.Read;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author CXQ
 * @date 2021/12/31
 */
public class MyFrame extends JFrame {

    public static void main(String[] args) {
        ConCloMySql conCloMySql = new ConCloMySql();
        conCloMySql.connect();
        MyFrame myFrame = new MyFrame();
        myFrame.init();
        myFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

                JOptionPane.showMessageDialog(null,"欢迎使用！！！");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                conCloMySql.close();
                JOptionPane.showMessageDialog(null,"再见！！！");

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        myFrame.init();
    }


    public void init() {

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JRadioButton jRadioButton1 = new JRadioButton("1.查询");
        JRadioButton jRadioButton2 = new JRadioButton("2.添加");
        JRadioButton jRadioButton3 = new JRadioButton("3.删除");
        JRadioButton jRadioButton4 = new JRadioButton("4.更改");
        jRadioButton1.setActionCommand(jRadioButton1.getActionCommand());
        jRadioButton2.setActionCommand(jRadioButton2.getActionCommand());
        jRadioButton3.setActionCommand(jRadioButton3.getActionCommand());
        jRadioButton4.setActionCommand(jRadioButton4.getActionCommand());

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        buttonGroup.add(jRadioButton3);
        buttonGroup.add(jRadioButton4);

        container.add(jRadioButton1);
        container.add(jRadioButton2);
        container.add(jRadioButton3);
        container.add(jRadioButton4);

        JButton jButton = new JButton(new AbstractAction("确定") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = buttonGroup.getSelection().getActionCommand();
                switch (result) {
                    case "1.查询":
                        Read.init();break;
                    case "2.添加":
                        Add.add();break;
                    case "3.删除":
                        Delete.delete();break;
                    case "4.更改":
                        Change.change();break;
                    default:break;
                }
            }
        });
        container.add(jButton);
        setVisible(true);
        setBounds(870,250,120,180);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
