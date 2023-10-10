/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.31 : Database - books
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`books` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `books`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `admin_name` varchar(50) NOT NULL COMMENT '管理员名称',
  `admin_password` varchar(100) NOT NULL COMMENT '管理员密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员表';

/*Data for the table `admin` */

insert  into `admin`(`admin_id`,`admin_name`,`admin_password`,`create_time`) values (1,'admin','123456','2023-10-01 00:00:00'),(2,'root','123456','2023-10-01 00:00:00');

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` int NOT NULL AUTO_INCREMENT COMMENT '图书ID',
  `title` varchar(100) NOT NULL COMMENT '书名',
  `author` varchar(100) NOT NULL COMMENT '作者',
  `publisher` varchar(100) NOT NULL COMMENT '出版社',
  `category_id` int NOT NULL COMMENT '类别ID',
  `release_date` date NOT NULL COMMENT '上线时间',
  `rating` decimal(2,1) NOT NULL DEFAULT '0.0' COMMENT '评分',
  `cover_image` varchar(100) DEFAULT NULL COMMENT '封面图',
  `description` text COMMENT '描述',
  PRIMARY KEY (`book_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='书籍表';

/*Data for the table `book` */

insert  into `book`(`book_id`,`title`,`author`,`publisher`,`category_id`,`release_date`,`rating`,`cover_image`,`description`) values (1,'深入浅出Node.js','朴灵','人民邮电出版社',1,'2013-02-01','9.0',NULL,'介绍Node.js的基础知识和应用实战'),(2,'明朝那些事儿','当年明月','湖南文艺出版社',2,'2006-07-01','9.5',NULL,'描述明朝历史上的一些趣闻轶事'),(3,'论语','孔子','商务印书馆',3,'2009-08-12','9.8',NULL,'记录孔子和其弟子言行的书籍');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `category_name` varchar(50) NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='类别表';

/*Data for the table `category` */

insert  into `category`(`category_id`,`category_name`) values (1,'计算机科学'),(2,'历史'),(3,'哲学'),(4,'计算机科学'),(5,'历史'),(6,'哲学');

/*Table structure for table `readinghistory` */

DROP TABLE IF EXISTS `readinghistory`;

CREATE TABLE `readinghistory` (
  `record_id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `book_id` int DEFAULT NULL COMMENT '图书ID',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`record_id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `readinghistory_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `readinghistory_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `readinghistory` */

insert  into `readinghistory`(`record_id`,`user_id`,`book_id`,`read_time`) values (4,1,1,'2023-10-01 09:30:00'),(5,1,2,'2023-10-02 14:15:00'),(6,2,1,'2023-10-03 17:45:00');

/*Table structure for table `recommendation` */

DROP TABLE IF EXISTS `recommendation`;

CREATE TABLE `recommendation` (
  `record_id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `book_id` int DEFAULT NULL COMMENT '图书ID',
  `recommend_time` datetime DEFAULT NULL COMMENT '推荐时间',
  `reason` varchar(100) DEFAULT NULL COMMENT '推荐原因',
  PRIMARY KEY (`record_id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `recommendation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `recommendation_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `recommendation` */

insert  into `recommendation`(`record_id`,`user_id`,`book_id`,`recommend_time`,`reason`) values (1,1,3,'2023-10-04 10:00:00','喜欢类似题材的书籍'),(2,2,2,'2023-10-05 16:20:00','与你之前阅读的书籍有关');

/*Table structure for table `systemlog` */

DROP TABLE IF EXISTS `systemlog`;

CREATE TABLE `systemlog` (
  `log_id` int NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `admin_id` int DEFAULT NULL COMMENT '管理员ID',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `operation_content` varchar(100) NOT NULL COMMENT '操作内容',
  `exception_info` text COMMENT '异常信息',
  PRIMARY KEY (`log_id`),
  KEY `user_id` (`user_id`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `systemlog_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `systemlog_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统日志表';

/*Data for the table `systemlog` */

insert  into `systemlog`(`log_id`,`user_id`,`admin_id`,`operation_time`,`operation_content`,`exception_info`) values (1,1,NULL,'2023-10-01 09:00:00','用户登录系统',NULL),(2,2,1,'2023-10-01 10:00:00','管理员修改了用户信息',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `photo` varchar(100) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`user_id`,`username`,`password`,`nickname`,`phone`,`email`,`photo`,`create_time`) values (1,'AJin','123456','曾祥金','18814255572','AJin2023@163.com',NULL,'2023-10-01 00:00:00'),(2,'user2','123456','小红',NULL,NULL,NULL,'2023-10-01 00:00:00'),(4,'xiaobai','123456','小白','10086','xiaobai@qq.com',NULL,'2023-10-10 15:25:23');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
