/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 8.0.27 : Database - west
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`west` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `west`;

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `商品编号` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `商品名` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `商品价格` decimal(10,0) NOT NULL,
  PRIMARY KEY (`商品名`,`商品价格`,`商品编号`),
  UNIQUE KEY `商品唯一` (`商品编号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `goods` */

insert  into `goods`(`商品编号`,`商品名`,`商品价格`) values ('10001','iPad','4780'),('10002','MateBookX','9000'),('10003','nova9','2699'),('10004','iPhone13','6588'),('10005','联想小新Pro16','5999'),('10006','联想YOGA14S','7099'),('10007','惠普光影精灵7Victus','7199'),('10008','DELLG15','6799'),('10009','小米11Pro','3999'),('10010','OPPOFindX3','3699'),('10011','MatePad11','2499'),('10012','小米平板5Pro','2499'),('10013','OPPOReno7','3699'),('10014','雷神911MT','5999'),('10015','神舟战神','4799'),('10016','戴睿Pro','1618'),('10017','ALIENWARE外星人全新x17','27969'),('10018','神舟战神Z8','6399'),('10019','惠普暗影精灵7','5599'),('10020','雷神PRO16','6000'),('10021','机械革命S5','4699'),('10022','外星人1代','12469'),('10023','ThinkPadS2','7099'),('10024','华硕asus','4178'),('10025','magicbook','4999');

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `订单编号` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `商品信息` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `下单时间` datetime NOT NULL,
  PRIMARY KEY (`订单编号`,`商品信息`),
  UNIQUE KEY `订单唯一` (`订单编号`),
  KEY `商品信息` (`商品信息`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`商品信息`) REFERENCES `goods` (`商品编号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order` */

insert  into `order`(`订单编号`,`商品信息`,`下单时间`) values ('','10001','2021-08-24 22:12:27'),('ord00002','10002','2021-10-05 22:13:27'),('ord00003','10003','2021-12-09 22:43:13'),('ord00004','10004','2021-12-20 22:43:13'),('ord00005','10005','2021-12-17 22:43:13'),('ord00006','10006','2021-12-06 22:43:13'),('ord00007','10007','2021-12-20 22:43:13'),('ord00008','10008','2021-12-24 22:43:13'),('ord00009','10009','2021-12-20 22:43:13'),('ord00010','10010','2021-12-20 22:43:13'),('ord00011','10011','2021-12-21 14:42:52'),('ord00012','10012','2021-12-21 00:00:00'),('ord00013','10013','2021-12-03 22:07:01'),('ord00014','10014','2021-12-02 22:07:06'),('ord00015','10015','2021-12-21 22:07:45'),('ord00016','10016','2021-10-05 22:07:53'),('ord00017','10017','2022-01-01 22:09:14'),('ord00018','10018','2021-10-05 22:09:18'),('ord00019','10019','2021-10-05 22:09:40'),('ord00020','10020','2021-10-12 22:09:59'),('ord00021','10021','2021-10-12 22:10:02'),('ord00022','10022','2021-09-14 22:11:40'),('ord00023','10023','2021-12-14 02:11:44'),('ord00024','10024','2021-12-31 22:12:08'),('ord00025','10025','2022-03-11 22:12:19');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
