/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 8.0.27 : Database - west_weather
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`west_weather` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `west_weather`;

/*Table structure for table `citys` */

DROP TABLE IF EXISTS `citys`;

CREATE TABLE `citys` (
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id` varchar(50) NOT NULL,
  `lat` varchar(50) DEFAULT NULL,
  `lon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`name`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `citys` */

insert  into `citys`(`name`,`id`,`lat`,`lon`) values ('上海','101020100','31.23170','121.47264'),('北京','101010100','39.90498','116.40528'),('厦门','101230201','24.49047','118.11022'),('广州','101280101','23.12517','113.28064'),('杭州','101210101','30.28745','120.15358'),('深圳','101280601','22.54700','114.08594'),('福州','101230101','26.07530','119.30623'),('西安','101110101','34.26316','108.94802');

/*Table structure for table `weather` */

DROP TABLE IF EXISTS `weather`;

CREATE TABLE `weather` (
  `name` varchar(20) NOT NULL,
  `fxDate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tempMax` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tempMin` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `textDay` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`name`,`fxDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `weather` */

insert  into `weather`(`name`,`fxDate`,`tempMax`,`tempMin`,`textDay`) values ('上海','2022-01-16','11','6','多云'),('上海','2022-01-17','11','5','阴'),('上海','2022-01-18','11','5','晴'),('北京','2022-01-16','1','-7','多云'),('北京','2022-01-17','4','-7','晴'),('北京','2022-01-18','3','-7','晴'),('福州','2022-01-16','15','11','小雨'),('福州','2022-01-17','13','11','阴'),('福州','2022-01-18','13','11','阴'),('西安','2022-01-16','10','0','晴'),('西安','2022-01-17','10','-1','晴'),('西安','2022-01-18','9','-1','晴');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
