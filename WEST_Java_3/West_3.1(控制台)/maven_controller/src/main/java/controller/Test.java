package controller;

import org.example.dao.ConCloMySql;
import org.example.service.Add;
import org.example.service.Change;
import org.example.service.Delete;
import org.example.service.Read;

import java.util.Scanner;

/**
 * @author CXQ
 * @date 2021/12/24
 */

// @WebServlet("/user")
public class Test {
    static Scanner input = new Scanner(System.in);

    // @Override
    // protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    public static void main(String[] args) {
        ConCloMySql conCloMySql = new ConCloMySql();
        conCloMySql.connect();

        while (true) {
            System.out.println(".请输入要进行的功能序号(1.查询 2.添加 3.删除 4.更改) ：");
            int num = input.nextInt();
            switch (num) {
                case 1:
                    Read.read();
                    break;
                case 2:
                    Add.add();
                    break;
                case 3:
                    Delete.delete();
                    break;
                case 4:
                    Change.change();
                    break;
                default:
                    break;
            }
            System.out.println(".还有另外的操作吗？(true or false)");
            boolean isContinue = input.nextBoolean();
            if (!isContinue) {
                break;
            }
        }
        conCloMySql.close();
    }
}
