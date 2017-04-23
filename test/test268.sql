-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: test268
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS test268;
CREATE DATABASE test268;
USE test268;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `dept_id` bigint(20) NOT NULL,
  `dept_name` varchar(20) NOT NULL,
  `dept_population` int(10) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `build_date` date NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,1,'IT部门',9,'负责软件研发工作,多个小组负责不同项目','2017-01-01','2017-04-21 14:00:22'),(2,2,'营销部门',1,'负责市场调研,获取关注热点,同时对公司的产品进行广告策划','2017-02-18','2017-04-21 14:13:29'),(3,3,'财务部门',3,'主要负责公司人员工资的统计发放,项目资金的申请和协调','2017-04-21','2017-04-21 14:14:53'),(4,5,'清洁部门',2,'主要负责公司楼道清洁.','2017-01-07','2017-04-21 14:15:44'),(5,6,'安保部门',2,'负责公司的安保工作, 对外来人员进行检查. 定期开展安全行动.','2017-01-21','2017-04-21 14:16:35'),(6,7,'人力资源管理部门',1,'负责公司人事调动','2017-01-02','2017-04-21 14:17:23');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_pwd` varchar(50) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  `salary` double(20,2) NOT NULL,
  `employee_date` date NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,101,'zkyyo','abc',1,35000.00,'2017-01-02','13454345654','zkyyo@outlook.com','2017-04-21 14:00:22'),(2,423497143,'赵东野','dongye',1,30000.00,'2017-04-21','13056545687','dongye09@gmail.com','2017-04-21 14:02:43'),(3,974709249,'李吕','aiyige',1,25000.00,'2017-04-01','+8613016054566','lv65412@163.com','2017-04-21 14:03:45'),(4,230805598,'李嘉颖','4765',6,35000.00,'2017-04-21','18646678011','jiayin8011@outlook.com','2017-04-21 14:04:46'),(5,688777589,'胡宇','70917335',1,23000.00,'2017-04-21','13059012311','709173335@qq.com','2017-04-21 14:05:48'),(6,724200363,'张汀','tingting009',1,23000.00,'2017-03-21','15612367888','tingtingin@gmail.com','2017-04-21 14:06:56'),(7,758795118,'Bod John','givelove999',1,34000.00,'2017-02-14','13016070122','john70122@gmail.com','2017-04-21 14:08:04'),(8,110617970,'乔宝','76589',1,21000.00,'2017-04-20','13056790988','badyqiao666@163.com','2017-04-21 14:08:50'),(9,407568017,'谢海丽','seesea',1,32000.00,'2017-02-04','13278643288','seabeauty@163.com','2017-04-21 14:09:41'),(10,89239094,'麦军','maijun123',1,34000.00,'2017-04-21','18612307688','85345321@qq.com','2017-04-21 14:10:13'),(11,637593984,'李芬','flower',2,36000.00,'2017-03-16','015674587900','fenfan@163.com','2017-04-21 14:18:23'),(12,579734422,'夏木','xiamuer123',3,27000.00,'2017-04-21','13016545699','summertree@outlook.com','2017-04-21 14:19:11'),(13,223232195,'夏叶清','1234',3,25000.00,'2017-04-21','15613567811','huhuou@gmail.com','2017-04-21 14:19:57'),(14,619114381,'林鑫','keke',3,29000.00,'2017-03-12','13012345789','keke456kk@163.com','2017-04-21 14:20:44'),(15,572429,'赵二春','098712',5,19000.00,'2017-04-21','13056012344','987893471@qq.com','2017-04-21 14:21:40'),(16,344976910,'张虎','xiamuaini',5,17000.00,'2017-04-21','13456434590','84545323@qq.com','2017-04-21 14:23:17'),(17,877254804,'朱迪','4987',7,61000.00,'2017-04-21','18613045610','dikauy@gmail.com','2017-04-21 14:24:25'),(18,866573051,'陈天利','tiantian',6,16000.00,'2017-04-21','13607987611','skyshu@163.com','2017-04-21 14:25:59');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `be_evaluated_id` bigint(20) NOT NULL,
  `evaluator_id` bigint(20) NOT NULL,
  `star_level` int(10) NOT NULL,
  `comment` text NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
INSERT INTO `evaluation` VALUES (1,101,101,10,'哈哈,自己先给自己一个评价,当然是满分啦.虽然还是有bug的,但是我还是会慢慢改进的.','2017-04-21 14:28:20'),(2,579734422,101,9,'夏木经常帮我们收拾桌子, 非常感谢啦','2017-04-21 14:32:18'),(3,110617970,101,8,'虽然我们是同个小组的,但我还是想提醒你去减肥','2017-04-21 14:32:45'),(4,866573051,101,7,'有时候挺凶的, 但是需要帮忙时也会权力相助','2017-04-21 14:33:29'),(5,866573051,101,7,'不就有几次忘记待卡嘛, 为什么就不让进了','2017-04-21 14:34:20'),(6,101,89239094,3,'说实话你这系统太烂了,我现在等进去就是管理员,我分分钟都可以把大家都删了','2017-04-21 14:35:40'),(8,974709249,89239094,5,'工作挺认真的','2017-04-21 14:36:00'),(9,101,89239094,4,'这系统太烂了,我还能把你们都删了','2017-04-21 14:37:41'),(10,230805598,89239094,9,'大姐姐','2017-04-21 14:38:17'),(11,101,89239094,3,'评价系统太不友好了','2017-04-21 14:40:08'),(12,423497143,89239094,9,'大神带我飞','2017-04-21 14:41:33'),(13,101,110617970,1,'你还说我胖, 你不信删几个员工看看会不会出bug, 哼','2017-04-21 14:43:01'),(14,758795118,110617970,9,'super man, big god, take me fly!!!!','2017-04-21 14:43:33'),(15,101,230805598,8,'我觉得再改进一下搜索系统应该会挺不错的,虽然已经挺好的,但是每次搜索完都清空信息真的很让人沮丧.....','2017-04-21 14:45:05'),(16,407568017,230805598,9,'哈哈,工作很热情,我要向你学习!!!','2017-04-21 14:45:40'),(17,866573051,230805598,8,'非常辛苦,经常帮我们科普一些安全知识','2017-04-21 14:46:04'),(18,101,619114381,5,'虽然我不是搞技术的,但是我还是发现,我的密码非常不安全,你有没有加密之类的啊?','2017-04-21 14:47:15'),(19,101,724200363,1,'竟然不能再评论区看到我的大名,只看到我的员工号是什么鬼?!excuse me?!','2017-04-21 14:48:33'),(20,758795118,724200363,9,'真大神','2017-04-21 14:48:44'),(21,110617970,724200363,7,'胖乔,工作很努力也得多运动啊','2017-04-21 14:49:12'),(22,101,866573051,8,'没办法, 上级要求的任务,真的很抱歉','2017-04-21 14:49:54'),(23,101,423497143,7,'挺简单的一个系统, 不过据说在Ubuntu的火狐浏览器下会很好看,你能做出来吗','2017-04-21 14:51:30'),(24,230805598,423497143,8,'嘉颖大大,多亏你我才找出一个超大的bug','2017-04-21 14:52:03'),(25,101,579734422,6,'为什么我看着看着,突然浏览器上多了那么多网页,这跳转不要太友好','2017-04-21 14:53:09'),(26,637593984,579734422,9,'芬芬经常在外跑,很辛苦的,为我们公司付出了很多精力','2017-04-21 14:54:17'),(27,101,758795118,10,'just do it! call me if any help needed :) john70122@gmail.com','2017-04-21 14:56:39');
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-21 23:39:52
