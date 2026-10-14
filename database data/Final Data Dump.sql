CREATE DATABASE  IF NOT EXISTS `final_cafe_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `final_cafe_db`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: final_cafe_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audience_types`
--

DROP TABLE IF EXISTS `audience_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audience_types` (
  `audience_type_id` varchar(100) NOT NULL,
  `type_name` varchar(45) NOT NULL,
  PRIMARY KEY (`audience_type_id`),
  UNIQUE KEY `uq_audience_types_type_name` (`type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_types`
--

LOCK TABLES `audience_types` WRITE;
/*!40000 ALTER TABLE `audience_types` DISABLE KEYS */;
INSERT INTO `audience_types` VALUES ('9a785d20-ab9c-47c5-89ba-3ea70376c689','All Customers'),('e1f8cfdd-14dc-48b1-b6cc-9c6e062921cc','Children Under 10'),('eb77d1ce-683f-4cf1-a748-3c86fa91f339','Customers Over 60'),('deleted','deleted'),('43631520-00a5-4d3f-8f65-e7ef52606469','Monks and Nuns'),('383aa2fd-b3dc-41f0-8bb8-6d65e24b4078','Students');
/*!40000 ALTER TABLE `audience_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch_statuses`
--

DROP TABLE IF EXISTS `branch_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch_statuses` (
  `branch_status_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`branch_status_id`),
  UNIQUE KEY `uq_branch_statuses_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_statuses`
--

LOCK TABLES `branch_statuses` WRITE;
/*!40000 ALTER TABLE `branch_statuses` DISABLE KEYS */;
INSERT INTO `branch_statuses` VALUES ('3','BANKRUPT'),('1','CLOSED'),('2','OPENED');
/*!40000 ALTER TABLE `branch_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branches`
--

DROP TABLE IF EXISTS `branches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branches` (
  `branch_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  `branch_finance` decimal(25,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `branch_status_id` varchar(100) NOT NULL,
  `opening_time` time NOT NULL,
  `closing_time` time NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`branch_id`),
  UNIQUE KEY `uq_branches_name` (`name`),
  UNIQUE KEY `uq_branches_location` (`location`),
  KEY `fk_branches_branch_statuses1` (`branch_status_id`),
  CONSTRAINT `fk_branches_branch_statuses1` FOREIGN KEY (`branch_status_id`) REFERENCES `branch_statuses` (`branch_status_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branches`
--

LOCK TABLES `branches` WRITE;
/*!40000 ALTER TABLE `branches` DISABLE KEYS */;
INSERT INTO `branches` VALUES ('1','Café Luft - Original','Yagon - Kamayut - Hledan',25000001.00,'The Original Café Luft','2','09:00:00','19:00:00','2005-06-09 11:00:00'),('2','Café Luft - Than Lan','Yangon - Hlaing',20000000.00,'The Second ever Branch of Café Luft','2','15:53:00','15:54:00','2011-06-09 11:00:00'),('a14d0fd5-dfad-4732-b801-7f612a530030','Café Luft - Thamine','Yangon Thamine',1000000.00,'A new Location near Parami Sein Gay Har','2','08:30:00','19:30:00','2026-10-14 05:04:20'),('deleted','deleted','deleted',0.00,'deleted','3','00:00:00','00:00:00','2005-06-09 11:00:00');
/*!40000 ALTER TABLE `branches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branches_stats`
--

DROP TABLE IF EXISTS `branches_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branches_stats` (
  `branch_stat_id` varchar(100) NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `salecount` int NOT NULL,
  `saleamount` decimal(10,2) NOT NULL,
  `month` varchar(45) NOT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `employee_costs` decimal(10,2) NOT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`branch_stat_id`),
  KEY `fk_branch_stats_branches1` (`branch_id`),
  KEY `fk_branch_stats_employees1` (`employee_id`),
  CONSTRAINT `fk_branch_stats_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_branch_stats_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branches_stats`
--

LOCK TABLES `branches_stats` WRITE;
/*!40000 ALTER TABLE `branches_stats` DISABLE KEYS */;
INSERT INTO `branches_stats` VALUES ('40663218-5bb5-4c13-a7e2-2a67d28b0162','1',1,100.00,'October',0,0,'2026-10-13 03:40:57',1000.00,'manager-1'),('47673377-6492-4930-adbe-61f6028d501f','1',1,1000.00,'October',0,0,'2026-10-10 05:13:46',1000.00,'manager-1'),('922116f9-2888-4891-8768-423ddc6fd233','1',1,0.40,'October',0,0,'2026-10-10 04:58:40',1000.00,'manager-1');
/*!40000 ALTER TABLE `branches_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `employee_id` varchar(100) DEFAULT NULL,
  `branches_branch_id` varchar(100) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uq_categories_name` (`name`),
  KEY `fk_categories_employees1` (`employee_id`),
  KEY `fk_categories_branches1_idx` (`branches_branch_id`),
  CONSTRAINT `fk_categories_branches1` FOREIGN KEY (`branches_branch_id`) REFERENCES `branches` (`branch_id`),
  CONSTRAINT `fk_categories_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES ('7d1dde7f-e259-47dc-810d-4308b8a60769','Hot Drinks','Hot, Super High Temperature Drinks',1,0,0,'2026-09-27 17:38:52','manager-1','1'),('deleted','deleted','Place Holder Delete Data',0,0,0,'2026-09-27 17:38:08','manager-1','1'),('e8a57c6f-e77d-4b4d-8918-26854c3eb677','Cold Drinks','Ice Cold Drinks',1,0,0,'2026-10-12 12:40:41','manager-1','1'),('eed8283f-5288-4604-ae3a-a948dcac0604','Cakes And Other Bakables','For Baked Goods',1,0,0,'2026-10-14 05:53:58','4b07f10c-6663-4530-9216-39b8a80c1152','1');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_registers`
--

DROP TABLE IF EXISTS `daily_registers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_registers` (
  `register_id` varchar(100) NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `employee_id` varchar(100) NOT NULL,
  `register_status_id` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `opened_at` time NOT NULL,
  `closed_at` time DEFAULT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`register_id`),
  KEY `fk_daily_register_branches1` (`branch_id`),
  KEY `fk_daily_register_employees1` (`employee_id`),
  KEY `fk_daily_registers_register_statuses1` (`register_status_id`),
  CONSTRAINT `fk_daily_register_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_daily_register_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_daily_registers_register_statuses1` FOREIGN KEY (`register_status_id`) REFERENCES `register_statuses` (`register_status_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_registers`
--

LOCK TABLES `daily_registers` WRITE;
/*!40000 ALTER TABLE `daily_registers` DISABLE KEYS */;
INSERT INTO `daily_registers` VALUES ('0f426b37-bbae-4668-8149-6c788d4653a5','1','staff-1','2','2026-10-01','13:19:11','23:59:00',1,0),('17c0732c-e701-4b34-9bc4-661868b1f4f5','1','staff-1','2','2026-09-30','15:43:55','23:59:00',1,0),('29f3c517-5d7a-4842-b575-2a4aa0adeac7','1','manager-1','2','2026-10-07','11:52:04','23:59:00',1,0),('2c0dd4f2-3d28-42cc-b767-045936d3af91','1','manager-1','2','2026-10-11','02:55:31','23:59:00',1,0),('36f0123b-59fc-4870-8e49-d174e33fdc33','1','manager-1','2','2026-10-13','03:12:22','23:59:00',1,0),('37fad078-f57f-4afd-8e8d-fea260b1c151','1','staff-2','1','2026-10-14','02:58:31',NULL,0,0),('393eb9dc-1448-49bb-bc23-191722e7e7b5','1','1d7a271f-377c-409d-bc1a-c0d5755221f0','1','2026-10-14','12:16:56',NULL,0,0),('5550fc67-0d21-4b0b-aecd-2e5b046257a0','1','manager-1','2','2026-10-06','01:28:05','23:59:00',1,0),('5f1f4e62-6116-44dd-8190-439bb14bd409','1','manager-1','1','2026-10-14','02:31:32',NULL,0,0),('6a86925f-0b33-4fb4-8fc6-18589cdd4874','1','staff-2','2','2026-10-13','23:00:23','23:59:00',1,0),('7ee6b54f-3506-498a-a95c-6c5ba88e4ddc','1','staff-1','2','2026-09-29','11:02:35','11:07:30',1,0),('831c03d6-4c37-414f-a6c7-85243b729e15','1','manager-1','2','2026-10-03','02:03:10','23:59:00',1,0),('8c10fa06-93e1-4a8d-a061-561a7ba66b88','1','manager-1','1','2026-09-27','00:25:31',NULL,0,1),('913b25a9-0856-4fe4-8692-ba83c2ad792a','1','manager-1','2','2026-10-08','01:59:37','23:59:00',1,0),('967cc1cc-05bc-4271-990b-dc3296451288','1','manager-1','2','2026-09-28','00:05:01','00:05:39',1,1),('9fc212f4-2257-40cb-bdc5-6ac463d798af','2','staff-1','1','2026-10-13','13:48:20',NULL,0,0),('9fdbb7b6-0edf-42ce-881f-d0dd92e51b77','1','manager-1','2','2026-10-12','02:48:17','23:59:00',1,0),('a2c84fa6-0353-4c71-9cdb-f887943b0d6f','1','manager-1','1','2026-10-02','02:37:02',NULL,0,1),('a6ff4097-34cc-4bc2-9bc6-79308a32ea65','1','manager-1','2','2026-10-09','01:30:17','23:59:00',1,0),('aab3c76b-736c-4c7a-b133-0a9a03de6064','2','staff-2','2','2026-09-28','01:48:28','23:59:00',1,0),('b140c710-71ec-404c-9a6a-83a66ac6bbb1','1','manager-1','2','2026-09-29','01:58:21','23:59:00',1,0),('b4b06761-926c-4959-9f08-838725834d9e','2','72c7477a-1921-42ad-b694-41458ce8af75','1','2026-10-13','03:48:06',NULL,0,0),('c1a5347e-1a97-445d-807e-7e0dfcaa40ff','2','staff-1','2','2026-09-28','01:48:01','23:59:00',1,0),('d2bba002-7d23-473f-b34b-85cd9a9bbdcf','2','staff-1','1','2026-10-13','13:48:20',NULL,0,0),('d50f5b60-2d51-4c17-8693-9114e7d0239a','1','manager-1','2','2026-10-10','03:53:48','23:59:00',1,0),('deleted','deleted','deleted','2','2026-09-25','00:00:00','00:00:00',0,0),('e204661e-38c0-41b0-a9ba-01e1b7e65252','1','manager-1','2','2026-09-28','01:13:14','23:59:00',1,0),('ede37da9-cae3-42d4-8e34-a1e5d69014f8','1','staff-1','2','2026-10-12','03:54:26','23:59:00',1,0),('f2d34d3a-b1b9-42cb-8353-3559ff94c98c','1','4b07f10c-6663-4530-9216-39b8a80c1152','1','2026-10-14','05:41:07',NULL,0,0),('f99d3314-4d18-4183-9ac9-b0416e783582','1','manager-1','2','2026-10-01','03:43:08','23:59:00',1,0),('fb9288df-e482-42ba-9d0b-1eb8c24776bb','1','manager-1','2','2026-09-30','01:59:57','23:59:00',1,0);
/*!40000 ALTER TABLE `daily_registers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_report_stock_imports`
--

DROP TABLE IF EXISTS `daily_report_stock_imports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_report_stock_imports` (
  `report_id` varchar(100) NOT NULL,
  `import_id` varchar(100) NOT NULL,
  PRIMARY KEY (`report_id`,`import_id`),
  KEY `fk_daily_report_has_import_stocks_import_stocks1` (`import_id`),
  CONSTRAINT `fk_daily_report_has_import_stocks_daily_report1` FOREIGN KEY (`report_id`) REFERENCES `daily_reports` (`report_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_daily_report_has_import_stocks_import_stocks1` FOREIGN KEY (`import_id`) REFERENCES `stock_imports` (`import_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_report_stock_imports`
--

LOCK TABLES `daily_report_stock_imports` WRITE;
/*!40000 ALTER TABLE `daily_report_stock_imports` DISABLE KEYS */;
/*!40000 ALTER TABLE `daily_report_stock_imports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_reports`
--

DROP TABLE IF EXISTS `daily_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_reports` (
  `report_id` varchar(100) NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `employee_id` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `initial_float_cash` decimal(10,2) NOT NULL,
  `closing_cash` decimal(10,2) NOT NULL,
  `total_sales` decimal(10,2) NOT NULL,
  `total_import_costs` decimal(10,2) NOT NULL,
  `net_profit_loss` decimal(10,2) NOT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `register_id` varchar(100) NOT NULL,
  PRIMARY KEY (`report_id`),
  KEY `fk_daily_report_branches1` (`branch_id`),
  KEY `fk_daily_reports_employees1` (`employee_id`),
  KEY `fk_daily_reports_daily_registers1` (`register_id`),
  CONSTRAINT `fk_daily_report_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_daily_reports_daily_registers1` FOREIGN KEY (`register_id`) REFERENCES `daily_registers` (`register_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_daily_reports_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_reports`
--

LOCK TABLES `daily_reports` WRITE;
/*!40000 ALTER TABLE `daily_reports` DISABLE KEYS */;
INSERT INTO `daily_reports` VALUES ('1','1','manager-1','2026-09-27',1000.00,1000.00,1000.00,100.00,1000.00,0,0,'deleted');
/*!40000 ALTER TABLE `daily_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delete_record`
--

DROP TABLE IF EXISTS `delete_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delete_record` (
  `parent_id` varchar(100) NOT NULL,
  `child_id` varchar(100) NOT NULL,
  `parent_table_name` varchar(100) NOT NULL,
  `child_table_name` varchar(100) NOT NULL,
  PRIMARY KEY (`parent_id`,`child_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delete_record`
--

LOCK TABLES `delete_record` WRITE;
/*!40000 ALTER TABLE `delete_record` DISABLE KEYS */;
INSERT INTO `delete_record` VALUES ('2fc5bd80-0f34-4dad-813e-e9ec49a0c017','P-1','pay_methods','paymethods'),('5c5d1045-d7e7-4003-b7bf-7cafd6c54379','P-02','sizes','products'),('5c5d1045-d7e7-4003-b7bf-7cafd6c54379','P-05','sizes','products'),('eddc0519-f861-4cf3-9345-6707dca5ef23','P-03','sizes','products'),('eddc0519-f861-4cf3-9345-6707dca5ef23','P-06','sizes','products'),('IT-3','0f0235a8-1425-4d75-8a40-7fc1cf2010b6','ingredient_types','products_ingredients'),('IT-3','BATCH-14c5324f','ingredient_types','ingredient_batches'),('IT-3','BATCH-5f4138bf','ingredient_types','ingredient_batches'),('IT-3','BATCH-ff210765','ingredient_types','ingredient_batches');
/*!40000 ALTER TABLE `delete_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discounts` (
  `discount_id` varchar(100) NOT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `startdate` date NOT NULL,
  `enddate` date NOT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `is_active` tinyint NOT NULL DEFAULT '1',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `promo_type_id` varchar(100) NOT NULL,
  `audience_type_id` varchar(100) NOT NULL,
  `branches_branch_id` varchar(100) NOT NULL,
  PRIMARY KEY (`discount_id`),
  KEY `fk_discounts_employees1` (`employee_id`),
  KEY `fk_discounts_promo_types1` (`promo_type_id`),
  KEY `fk_discounts_audience_types1` (`audience_type_id`),
  KEY `fk_discounts_branches1_idx` (`branches_branch_id`),
  CONSTRAINT `fk_discounts_audience_types1` FOREIGN KEY (`audience_type_id`) REFERENCES `audience_types` (`audience_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_discounts_branches1` FOREIGN KEY (`branches_branch_id`) REFERENCES `branches` (`branch_id`),
  CONSTRAINT `fk_discounts_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_discounts_promo_types1` FOREIGN KEY (`promo_type_id`) REFERENCES `promo_types` (`promo_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
INSERT INTO `discounts` VALUES ('0e9b1d33-02fb-495a-95b3-10995c38d12e',NULL,'Thadinkyut Special','Seasonal Thadinkyut Special',10.00,'2026-10-10','2026-10-31',0,1,0,'2026-09-28 12:43:41','d7847d54-7a69-4066-82b4-b5d7176c0083','9a785d20-ab9c-47c5-89ba-3ea70376c689','1'),('1afc4b45-3dab-48fb-a26b-b693e1a0b925','manager-1','New Year Special','Happy New Year!',25.00,'2026-12-25','2027-01-05',0,0,0,'2026-10-11 15:50:45','d7847d54-7a69-4066-82b4-b5d7176c0083','9a785d20-ab9c-47c5-89ba-3ea70376c689','1'),('6101c3ef-2b14-493b-a0b7-4d3376f6db12','manager-1','Halloween Discount','Halloween Discount',13.00,'2026-10-30','2026-10-31',0,0,0,'2026-09-30 04:38:12','d7847d54-7a69-4066-82b4-b5d7176c0083','9a785d20-ab9c-47c5-89ba-3ea70376c689','1'),('7d5b492d-ffb0-49a1-aac7-2cfea0e67259','manager-1','Near Expiry Sep 30','Near Expiry Sep 29',50.00,'2026-10-01','2026-10-02',1,0,0,'2026-09-29 04:59:54','bdff88b1-1da0-4cb8-b2c1-0099b1662ca0','9a785d20-ab9c-47c5-89ba-3ea70376c689','1'),('deleted',NULL,'deleted','Place Holder Delete Data',0.00,'2026-09-30','2026-09-30',0,0,0,'2026-09-29 04:59:54','deleted','deleted','deleted'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','4b07f10c-6663-4530-9216-39b8a80c1152','Kids Special','Only For Children Under 10',10.00,'2026-10-01','2027-10-01',0,1,0,'2026-10-14 11:50:02','550d4b91-2b36-4d94-8706-0d921ad8e262','e1f8cfdd-14dc-48b1-b6cc-9c6e062921cc','1');
/*!40000 ALTER TABLE `discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discounts_products`
--

DROP TABLE IF EXISTS `discounts_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discounts_products` (
  `discount_id` varchar(100) NOT NULL,
  `product_id` varchar(100) NOT NULL,
  PRIMARY KEY (`discount_id`,`product_id`),
  KEY `fk_discount_has_menu_items_sizes_menu_items_sizes1` (`product_id`),
  CONSTRAINT `fk_discount_has_menu_items_sizes_discount1` FOREIGN KEY (`discount_id`) REFERENCES `discounts` (`discount_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_discount_has_menu_items_sizes_menu_items_sizes1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts_products`
--

LOCK TABLES `discounts_products` WRITE;
/*!40000 ALTER TABLE `discounts_products` DISABLE KEYS */;
INSERT INTO `discounts_products` VALUES ('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','1cbfb426-b636-48e5-9a0e-ed2a583c2b55'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','1db0b384-654f-4162-9c45-f0c97e92bf08'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','2069a97f-6f53-4fae-a487-f8badd0a0059'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','3bef409b-b69c-4039-a4d9-5e5fc0ef5884'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','42792efb-2b7b-4637-a1fe-4f7cb28d94ec'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','48e71e79-f88a-4ce6-af9d-ea4b55c63542'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','493fb55d-c5ba-433f-b441-d6a59dcfe28f'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','4a450f90-225d-4d88-9ef4-e814b802c340'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','7f81bf59-eb4f-40aa-83c5-aab8dac5525c'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','88b9a373-e6c2-46d6-bbab-054dec0c8093'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','98d2581a-c9c0-4fbf-a393-ed56806d2be3'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','9a5f2f9c-6bb6-4136-b8c6-84db7dd6ceda'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','a902ea5e-0b9b-4cad-9a64-3b9632a64871'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','a917ef26-459b-4df7-ae96-50f300b0001d'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','c4e672cd-bef7-4eed-85fd-d640d8dc0ff9'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','c8767528-377c-4c71-99c3-3f2086c384a7'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','ceda2d7d-ee1c-4a08-a854-6f4c65fa4b30'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','d270eade-612a-4c8e-9f42-e0a83c397c6f'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','d7345435-f0dc-48fd-85ab-cc7c081a916c'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','deb610a3-7176-455d-b841-1421a3951b54'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','e74c143b-44e7-4573-8a11-151129c9f522'),('f5b501d0-33e3-4ce3-9d59-0468ca44aaa2','ec927278-01d2-4c8f-a0a1-382c1fee1d1f');
/*!40000 ALTER TABLE `discounts_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_roles`
--

DROP TABLE IF EXISTS `employee_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_roles` (
  `role_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uq_employee_roles_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_roles`
--

LOCK TABLES `employee_roles` WRITE;
/*!40000 ALTER TABLE `employee_roles` DISABLE KEYS */;
INSERT INTO `employee_roles` VALUES ('1','MANAGER','Manager',0,0,'2026-09-29 12:06:24'),('2','STAFF','Staff Member',0,0,'2026-09-29 12:06:24'),('3','ADMIN','Administrator',0,0,'2026-09-29 12:06:24');
/*!40000 ALTER TABLE `employee_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_statuses`
--

DROP TABLE IF EXISTS `employee_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_statuses` (
  `employee_status_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`employee_status_id`),
  UNIQUE KEY `uq_employee_statuses_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_statuses`
--

LOCK TABLES `employee_statuses` WRITE;
/*!40000 ALTER TABLE `employee_statuses` DISABLE KEYS */;
INSERT INTO `employee_statuses` VALUES ('1','FIRED'),('2','NORMAL'),('3','ONLEAVE');
/*!40000 ALTER TABLE `employee_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employee_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `photopath` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `employee_status_id` varchar(100) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `salary` decimal(10,2) NOT NULL,
  `address` varchar(100) NOT NULL,
  `dob` date NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') NOT NULL,
  `employee_role_id` varchar(100) NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `uq_employees_email` (`email`),
  KEY `fk_employees_employee_role1` (`employee_role_id`),
  KEY `fk_employees_branches1` (`branch_id`),
  KEY `fk_employees_employee_statuses1` (`employee_status_id`),
  CONSTRAINT `fk_employees_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_employees_employee_role1` FOREIGN KEY (`employee_role_id`) REFERENCES `employee_roles` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_employees_employee_statuses1` FOREIGN KEY (`employee_status_id`) REFERENCES `employee_statuses` (`employee_status_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES ('1d7a271f-377c-409d-bc1a-c0d5755221f0','Hein Min Htet','hein@mail.com','ZT3pWSAmKM - photo_2026-10-14_03-17-31.jpg','$2a$10$xiN5ZKOlCMS4ctEBMUa6qedDUGI68WaUG65dZ31cGt4NG4koGqcE2','2','09121212121',1000000.00,'Yangon, Shwe Pyi Tha','1933-10-08','MALE','1','1','2026-10-14 12:13:29'),('2926924c-d6b4-43c1-b030-8315c0f4d340','Alex Dugin','alex@kremlin.com',NULL,'$2a$10$K4xaidgUX6MrSo4Rrd5EI.50.gs0EVlH4QBjVJklOp1342KVuRJUW','2','09232124220',850.00,'Moscow, USA','1984-09-12','MALE','2','1','2026-10-13 04:50:26'),('4b07f10c-6663-4530-9216-39b8a80c1152','Kaung Satt Hein','sattheinkaung@gmail.com','gm47rUPRhi - photo_2026-10-14_03-17-27.jpg','$2a$10$deOEYdwmAxhhOOsGLTo0Ou9VFAUaEC3YaV6IpQYPgaEwrbVR02zj.','2','09262402005',1000000.00,'Sin Yay Twin, Kamayut, Yangon','2005-06-09','MALE','1','1','2026-10-14 05:37:23'),('72c7477a-1921-42ad-b694-41458ce8af75','Brad Pitt','brad@mail.com',NULL,'$2a$10$zsbYXAnwHqpYvmD.rKjlS.uiVGdYDz7WYqE6Zuo7FhoE/5jYbCeBa','3','09123452005',1000000.00,'Kamayut, Yangon','2000-12-12','OTHER','1','2','2026-10-06 07:52:20'),('admin','O5 - 13','admin@mail.com','vmWxt3IJTv - O5_Council.webp','$2a$10$12wRCGcGTVOxawdHH/Rh6e82CoE46pnbW0te19y6fUjS4aeMWWuzm','2','09131313051',0.00,'Ganzir','2005-06-09','MALE','3','1','2005-06-09 11:00:00'),('cbe892b2-129b-4f68-a249-1350f5407f26','Nay Zar Linn','nay@mail.com','riUKzdDo08 - photo_2026-10-14_03-17-34.jpg','$2a$10$neZcz0hastRA2Wfq6Zu88.1haWkqfNmE8SmodpCLI0TybhovlcMBm','2','09121212121',1000000.00,'Yangon, Hlaing','1994-12-12','FEMALE','3','1','2026-10-14 12:15:30'),('deleted','deleted','deleted','deleted','deleted','1','deleted',0.00,'deleted','2005-06-09','OTHER','1','deleted','2005-06-09 11:00:00'),('e9980cb4-86f9-456a-a8be-a36cac784734','STAFF3','staff3@mail.com',NULL,'$2a$10$.ODZ8mgI.sDzU1VkRz5cy.ifIq5Uh2cdHNu4Q5cJEOPtcEvGghTUe','1','091232005',1000.00,'123, Kamayut, Yangon','1996-02-10','FEMALE','2','1','2026-10-06 05:55:38'),('f1ba85f9-063f-4a2a-9f00-210c287deaf1','StaffTestForEdit','edittest@mail.com',NULL,'$2a$10$Ciz5fOYwP.JHExFCdcp6XumiADCpEEE52gxmskFWSNr4k.IhedSPC','1','09135792005',1000.00,'Kamayut, Yangon','1912-12-12','OTHER','2','1','2026-10-06 09:38:24'),('manager-1','Lukoshenko','manager@mail.com',NULL,'$2a$10$6lWD/7BvJ2Ja.VRqF7NTtuq7HwYDutMERJtO0sWdbazinK.Z6iwHu','2','09101012005',1000000.00,'Minsk, Belarus','1990-10-13','MALE','1','a14d0fd5-dfad-4732-b801-7f612a530030','2005-06-09 11:00:00'),('staff-1','STAFF','staff@mail.com',NULL,'$2a$10$zsbYXAnwHqpYvmD.rKjlS.uiVGdYDz7WYqE6Zuo7FhoE/5jYbCeBa','2','09123452005',800.00,'Pripyat, Ukraine','2009-12-12','OTHER','2','2','2005-06-09 11:00:00'),('staff-2','Yinmin Thiri Soe','staff2@mail.com','JwOgutXrN9 - photo_2026-10-14_03-17-39.jpg','$2a$10$vvtOgMkdQlQ4GGwSruFsCOLOgBFvXcX8WQKjDbquBbXNyOGH6uBqa','2','09123452005',1000.00,'Bakhmut','2000-12-12','FEMALE','2','1','2005-06-09 11:00:00');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense_categories`
--

DROP TABLE IF EXISTS `expense_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expense_categories` (
  `expense_category_id` varchar(100) NOT NULL,
  `category_name` varchar(45) NOT NULL,
  PRIMARY KEY (`expense_category_id`),
  UNIQUE KEY `uq_expense_categories_category_name` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_categories`
--

LOCK TABLES `expense_categories` WRITE;
/*!40000 ALTER TABLE `expense_categories` DISABLE KEYS */;
INSERT INTO `expense_categories` VALUES ('deleted','deleted'),('5e5e2dd8-ebd6-4dc0-b84b-ade86654216a','Delivery Fees'),('b5bdc4a7-79e4-4bce-bf61-242ddd1d24f9','Employee Salaries'),('469fb792-03d9-4fd8-9402-52883998f120','Fines'),('cb999a05-186c-4e63-8cea-6c8e6dfb7bb2','Stock Import');
/*!40000 ALTER TABLE `expense_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expenses` (
  `expense_id` varchar(100) NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `expense_category_id` varchar(100) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `expense_date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`expense_id`),
  KEY `fk_expenses_branches1` (`branch_id`),
  KEY `fk_expenses_expense_categories1` (`expense_category_id`),
  KEY `fk_expenses_employees1` (`employee_id`),
  CONSTRAINT `fk_expenses_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_expenses_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_expenses_expense_categories1` FOREIGN KEY (`expense_category_id`) REFERENCES `expense_categories` (`expense_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expenses`
--

LOCK TABLES `expenses` WRITE;
/*!40000 ALTER TABLE `expenses` DISABLE KEYS */;
INSERT INTO `expenses` VALUES ('557cb226-63f2-4b9a-9c30-98bc0f3dc176','1','cb999a05-186c-4e63-8cea-6c8e6dfb7bb2',575000.00,'2026-10-14 06:15:00','Baked Goods Import','4b07f10c-6663-4530-9216-39b8a80c1152','2026-10-14 11:21:50',0),('e20190f4-2a95-4768-aa6c-d1355a40e3d9','1','cb999a05-186c-4e63-8cea-6c8e6dfb7bb2',950000.00,'2026-10-14 09:35:00','Stock Imports - Coffee Beans, Vanilla','4b07f10c-6663-4530-9216-39b8a80c1152','2026-10-14 11:18:19',0),('fa6ba5e7-1313-4b41-859a-4cd70126cdb1','1','b5bdc4a7-79e4-4bce-bf61-242ddd1d24f9',100000.00,'2026-10-01 13:35:00','Give an Employee Their Salary',NULL,'2026-10-02 01:37:44',0),('ff1e0079-c13a-4457-8acb-39e8252c489d','1','cb999a05-186c-4e63-8cea-6c8e6dfb7bb2',2190000.00,'2026-10-07 07:11:00','General Goods Imports','4b07f10c-6663-4530-9216-39b8a80c1152','2026-10-14 11:22:28',0);
/*!40000 ALTER TABLE `expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient_batches`
--

DROP TABLE IF EXISTS `ingredient_batches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient_batches` (
  `batch_id` varchar(100) NOT NULL,
  `remaining_quantity` decimal(10,3) NOT NULL,
  `manufactured_date` date NOT NULL,
  `expire_date` date NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ingredient_type_id` varchar(100) NOT NULL,
  `unit_cost` decimal(10,2) NOT NULL DEFAULT '0.00',
  `quantity_ordered` double NOT NULL,
  `total_import_cost` double NOT NULL,
  `import_id` varchar(100) NOT NULL,
  PRIMARY KEY (`batch_id`),
  KEY `fk_ingredients_branches1` (`branch_id`),
  KEY `fk_ingredient_batches_ingredient_types1` (`ingredient_type_id`),
  KEY `fk_ingredient_batches_stock_imports1_idx` (`import_id`),
  CONSTRAINT `fk_ingredient_batches_ingredient_types1` FOREIGN KEY (`ingredient_type_id`) REFERENCES `ingredient_types` (`ingredient_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ingredient_batches_stock_imports1` FOREIGN KEY (`import_id`) REFERENCES `stock_imports` (`import_id`),
  CONSTRAINT `fk_ingredients_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient_batches`
--

LOCK TABLES `ingredient_batches` WRITE;
/*!40000 ALTER TABLE `ingredient_batches` DISABLE KEYS */;
INSERT INTO `ingredient_batches` VALUES ('Brown Tea Leaves-7750ac46',20.000,'2025-09-11','2027-09-11','1',0,'2026-10-14 06:13:55','831d9615-02e0-4c46-96b8-bb02030bbe60',10000.00,20,200000,'db4c094e-ae47-4e79-b528-02280955217f'),('Cheese Cake-90a7aa8b',30.000,'2026-10-14','2026-10-16','1',0,'2026-10-14 06:16:04','87b2d5e4-ccb2-49e1-87d5-a771d543eec5',5000.00,30,150000,'69d276fb-15ad-40b8-80f2-d0cf79dabd76'),('Chocolate Cake-d50c8b49',30.000,'2026-10-14','2026-10-16','1',0,'2026-10-14 06:16:04','2969bcb2-676e-4b59-adc7-5432da09fd37',5000.00,30,150000,'69d276fb-15ad-40b8-80f2-d0cf79dabd76'),('Coffee Beans-66863669',30.000,'2026-10-01','2026-10-31','1',0,'2026-10-14 06:18:09','IT-1',30000.00,30,900000,'1c93c8a1-4765-4a00-8727-8357c6415699'),('deleted',0.000,'2026-10-01','2026-10-01','deleted',0,'2026-10-02 02:00:09','deleted',0.00,0,0,'deleted'),('Donuts-2f0dfa04',50.000,'2026-10-14','2026-10-16','1',0,'2026-10-14 06:16:04','c0d92aa1-4ae7-4cfb-8620-d4fb5c0d1ce6',2500.00,50,125000,'69d276fb-15ad-40b8-80f2-d0cf79dabd76'),('Milk (Liter)-fe74ed46',20.000,'2026-10-01','2026-10-31','1',0,'2026-10-14 06:13:55','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',12000.00,20,240000,'db4c094e-ae47-4e79-b528-02280955217f'),('Pumpkin Spice-066c87f3',3000.000,'2026-10-06','2026-11-30','1',0,'2026-10-14 06:13:55','c8990c0d-3f08-4f8c-b28e-c23c645362f1',500.00,3000,1500000,'db4c094e-ae47-4e79-b528-02280955217f'),('Strawberry Cake-d6570d40',30.000,'2026-10-14','2026-10-16','1',0,'2026-10-14 06:16:04','aae81176-e9c1-49d3-92a2-2d0199d6b31c',5000.00,30,150000,'69d276fb-15ad-40b8-80f2-d0cf79dabd76'),('Sugar-e43613bd',50.000,'2024-10-13','2028-10-31','1',0,'2026-10-14 06:13:55','5d58be8d-c93e-411d-9076-10074f5d0eb0',5000.00,50,250000,'db4c094e-ae47-4e79-b528-02280955217f'),('Vanilla-321981f3',500.000,'2026-08-31','2030-12-31','1',0,'2026-10-14 06:18:09','fcd438ea-db78-4dbf-bc1b-133603349cbb',100.00,500,50000,'1c93c8a1-4765-4a00-8727-8357c6415699');
/*!40000 ALTER TABLE `ingredient_batches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient_types`
--

DROP TABLE IF EXISTS `ingredient_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient_types` (
  `ingredient_type_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `unit_id` varchar(100) NOT NULL,
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ingredient_type_id`),
  UNIQUE KEY `uq_ingredient_types_name` (`name`),
  KEY `fk_ingredient_types_units1` (`unit_id`),
  CONSTRAINT `fk_ingredient_types_units1` FOREIGN KEY (`unit_id`) REFERENCES `units` (`unit_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient_types`
--

LOCK TABLES `ingredient_types` WRITE;
/*!40000 ALTER TABLE `ingredient_types` DISABLE KEYS */;
INSERT INTO `ingredient_types` VALUES ('2969bcb2-676e-4b59-adc7-5432da09fd37','Chocolate Cake','From Bakery','e137d54a-e5c5-412f-b397-06a8a74654cb',0,'2026-10-14 05:21:58'),('30fcf888-cd6f-4943-85f0-bec99949d567','Ready Grounded Coffee','A Ready Grounded Coffee for Cheaper products','fd6ac73e-1dd9-491e-becd-f7b20dfb569f',0,'2026-10-14 05:31:50'),('5d58be8d-c93e-411d-9076-10074f5d0eb0','Sugar','Sugar','fd6ac73e-1dd9-491e-becd-f7b20dfb569f',0,'2026-10-14 05:30:17'),('702640c2-6624-4b4a-8895-0766049ba976','Green Tea Leaves','Green Tea Leaves','fd6ac73e-1dd9-491e-becd-f7b20dfb569f',0,'2026-10-14 06:08:42'),('831d9615-02e0-4c46-96b8-bb02030bbe60','Brown Tea Leaves','Healthy Brown Tea Leaves','fd6ac73e-1dd9-491e-becd-f7b20dfb569f',0,'2026-10-14 06:09:10'),('87b2d5e4-ccb2-49e1-87d5-a771d543eec5','Cheese Cake','From A Local Bakery','e137d54a-e5c5-412f-b397-06a8a74654cb',0,'2026-10-14 05:25:02'),('89a6d50c-df90-42af-9d5d-9ce845e1bbee','Milk','From the Store','0ae229a0-d3f3-4454-9347-e3410a3e0298',0,'2026-10-14 05:26:32'),('aae81176-e9c1-49d3-92a2-2d0199d6b31c','Strawberry Cake','A Delicious Strawberry Cake','e137d54a-e5c5-412f-b397-06a8a74654cb',0,'2026-10-14 05:29:55'),('c0d92aa1-4ae7-4cfb-8620-d4fb5c0d1ce6','Donuts','From J-Donuts','0ae229a0-d3f3-4454-9347-e3410a3e0298',0,'2026-10-14 05:27:33'),('c8990c0d-3f08-4f8c-b28e-c23c645362f1','Pumpkin Spice','Pumpkin Spice','349378ce-b353-48c1-8d76-c49d03df107d',0,'2026-10-14 05:31:04'),('deleted','deleted','deleted','deleted',0,'2026-09-27 23:30:10'),('fa45cb10-9f6d-4f76-8f6e-b2abeb90f157','Milk (Liter)','From a local store','39b57a66-c914-4a97-9d9c-6e9a79bec84f',0,'2026-10-14 05:27:07'),('fcd438ea-db78-4dbf-bc1b-133603349cbb','Vanilla','Vanilla Spice','349378ce-b353-48c1-8d76-c49d03df107d',0,'2026-10-14 05:30:42'),('IT-1','Coffee Beans','Coffee Beans','fd6ac73e-1dd9-491e-becd-f7b20dfb569f',0,'2026-09-27 23:30:10'),('IT-2','Rice','Rice','fd6ac73e-1dd9-491e-becd-f7b20dfb569f',0,'2026-09-27 23:30:10'),('IT-3','Flour','Flour','fd6ac73e-1dd9-491e-becd-f7b20dfb569f',1,'2026-09-27 23:30:10');
/*!40000 ALTER TABLE `ingredient_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_detail_id` varchar(100) NOT NULL,
  `product_id` varchar(100) NOT NULL,
  `order_id` varchar(100) NOT NULL,
  `quantity` int NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `fk_menu_items_sizes_has_order_menu_items_sizes1` (`product_id`),
  KEY `fk_menu_items_sizes_has_order_order1` (`order_id`),
  CONSTRAINT `fk_menu_items_sizes_has_order_menu_items_sizes1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_menu_items_sizes_has_order_order1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES ('80f1f643-b50a-4932-9f1d-a0db7a295e4b','3bef409b-b69c-4039-a4d9-5e5fc0ef5884','3d41415b-47a7-4efe-bd64-1719f715193d',2,'less sugar'),('82ffddb5-7d66-4eb9-9c96-7a2bfbb82e49','0f0235a8-1425-4d75-8a40-7fc1cf2010b6','2456f7fa-2052-449d-8698-928844595d17',2,''),('922c1b5d-5647-4195-80ad-9d7f39ad1b3e','0f0235a8-1425-4d75-8a40-7fc1cf2010b6','2f82cba2-1bcf-4bce-938e-ff4ad776a835',2,''),('9f1b6ef9-9a9b-43bd-a95e-394236829d73','1cbfb426-b636-48e5-9a0e-ed2a583c2b55','3d41415b-47a7-4efe-bd64-1719f715193d',1,''),('b2c76cc9-a951-4680-9f79-c8575e47ac15','0f0235a8-1425-4d75-8a40-7fc1cf2010b6','1e004ce0-0a4e-4cb9-b4e6-4f88582b9c11',1,''),('d8f2e120-962c-45f4-a475-455340e1c168','0f0235a8-1425-4d75-8a40-7fc1cf2010b6','8293bb3a-7f14-4523-b982-87023ce6e51e',1,''),('de95ad67-a3d2-462d-9f32-15727d990bbb','0f0235a8-1425-4d75-8a40-7fc1cf2010b6','b6a1ded2-4a65-40b6-a5b8-4bf52d095051',1,''),('edda8717-80c4-4631-82dc-e66d3223d7de','0f0235a8-1425-4d75-8a40-7fc1cf2010b6','1fa644cd-7c87-4fe2-bc0b-343caf4e12d7',1,'');
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` varchar(100) NOT NULL,
  `employee_id` varchar(100) NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `created_time` datetime NOT NULL,
  `received_time` datetime DEFAULT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `total_amount` decimal(10,2) NOT NULL,
  `token_number` int NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_order_employees1` (`employee_id`),
  KEY `fk_order_branches1` (`branch_id`),
  CONSTRAINT `fk_order_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_order_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('1e004ce0-0a4e-4cb9-b4e6-4f88582b9c11','staff-2','1','2026-10-14 04:54:12',NULL,0,1,6000.00,4),('1fa644cd-7c87-4fe2-bc0b-343caf4e12d7','staff-2','1','2026-10-14 03:06:48',NULL,0,1,6000.00,2),('2456f7fa-2052-449d-8698-928844595d17','manager-1','1','2026-10-13 13:52:50','2026-10-13 13:57:45',0,0,12000.00,1),('2f82cba2-1bcf-4bce-938e-ff4ad776a835','staff-2','1','2026-10-14 04:57:24',NULL,0,1,12000.00,5),('3d41415b-47a7-4efe-bd64-1719f715193d','staff-2','1','2026-10-14 11:56:56','2026-10-14 11:58:40',0,0,19800.00,2),('8293bb3a-7f14-4523-b982-87023ce6e51e','staff-2','1','2026-10-14 03:06:22',NULL,0,0,6000.00,1),('b6a1ded2-4a65-40b6-a5b8-4bf52d095051','staff-2','1','2026-10-14 04:39:47',NULL,0,1,6000.00,3),('Order-1','manager-1','1','2026-09-28 12:43:41','2026-09-28 12:43:41',0,0,100.00,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_methods`
--

DROP TABLE IF EXISTS `pay_methods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_methods` (
  `method_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `logopath` varchar(100) DEFAULT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `employee_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`method_id`),
  UNIQUE KEY `uq_pay_methods_name` (`name`),
  KEY `fk_pay_methods_employees1` (`employee_id`),
  CONSTRAINT `fk_pay_methods_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_methods`
--

LOCK TABLES `pay_methods` WRITE;
/*!40000 ALTER TABLE `pay_methods` DISABLE KEYS */;
INSERT INTO `pay_methods` VALUES ('057b6967-f5dc-4e77-aee6-fa8f8854d5db','Wave Pay','Wave Pay','yRfVnb6dNs - Wave Pay.jpg',1,1,0,'2026-10-02 14:52:46','admin'),('126c99c8-e7cc-4d41-9c63-ed0ce1bc0617','AYA Pay','AYA Pay','9UNBgKUCRv - AYA.jpg',1,1,0,'2026-10-02 14:55:41','admin'),('2fc5bd80-0f34-4dad-813e-e9ec49a0c017','Cash 3','Old Cash','wYHuhyAgKT - Cash.jpg',1,1,1,'2026-10-02 14:29:06','manager-1'),('49b957a5-526d-488d-b441-32474260f29a','Cash','Cash','5sxrnxdQ1h - Cash.jpg',1,1,0,'2026-10-02 14:20:27','admin'),('4f19f1e1-8bba-4997-8888-030248feae3f','VISA','VISA Card','M5OqNbWKNt - VISA.jpg',1,1,1,'2026-10-02 14:56:08','admin'),('c46e16cc-cf5c-481b-8659-4cdd59a76e7a','Cash 2','Foreign Cash','ESqdYHHT0w - Cash.jpg',1,1,1,'2026-10-02 14:23:28','manager-1'),('deleted','deleted','deleted',NULL,0,0,0,'2026-10-02 14:16:36',NULL),('f7e35e6f-d9f0-4dfd-be2e-675e18c23676','KBZ Pay','KBZ Pay','zmOrQJ5RJO - KBZ - Pay.jpg',1,1,0,'2026-10-02 14:16:36','admin');
/*!40000 ALTER TABLE `pay_methods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` varchar(100) NOT NULL,
  `order_id` varchar(100) NOT NULL,
  `paid_time` datetime NOT NULL,
  `method_id` varchar(100) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  `filepath` varchar(100) NOT NULL,
  `employee_id` varchar(100) NOT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `fk_payment_pay_method1` (`method_id`),
  KEY `fk_payment_order1` (`order_id`),
  KEY `fk_payments_employees1` (`employee_id`),
  CONSTRAINT `fk_payment_order1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_payment_pay_method1` FOREIGN KEY (`method_id`) REFERENCES `pay_methods` (`method_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_payments_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES ('160740c0-0b77-43aa-bc77-47af1361b543','3d41415b-47a7-4efe-bd64-1719f715193d','2026-10-14 11:57:58','49b957a5-526d-488d-b441-32474260f29a','Good',0,0,'2026-10-14','','staff-2'),('71582d85-acbe-4bcd-b5bb-ae955b419775','8293bb3a-7f14-4523-b982-87023ce6e51e','2026-10-14 04:40:04','126c99c8-e7cc-4d41-9c63-ed0ce1bc0617','',0,0,'2026-10-14','','staff-2'),('b917f6ee-78f1-4d6a-bc85-efdc69e8a928','2456f7fa-2052-449d-8698-928844595d17','2026-10-13 13:53:32','126c99c8-e7cc-4d41-9c63-ed0ce1bc0617','yuyyi',0,0,'2026-10-13','','manager-1'),('P-1','Order-1','2026-09-28 12:43:41','deleted','No Notes',0,0,'2026-09-28','lk;jsdf','manager-1');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_types`
--

DROP TABLE IF EXISTS `product_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_types` (
  `type_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `coverimgpath` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `category_id` varchar(100) NOT NULL,
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `isedited` tinyint NOT NULL DEFAULT '0',
  `employee_id` varchar(100) DEFAULT NULL,
  `branches_branch_id` varchar(100) NOT NULL,
  PRIMARY KEY (`type_id`),
  KEY `fk_items_item_types1` (`category_id`),
  KEY `fk_menu_types_employees1` (`employee_id`),
  KEY `fk_product_types_branches1_idx` (`branches_branch_id`),
  CONSTRAINT `fk_items_item_types1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_menu_types_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_product_types_branches1` FOREIGN KEY (`branches_branch_id`) REFERENCES `branches` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_types`
--

LOCK TABLES `product_types` WRITE;
/*!40000 ALTER TABLE `product_types` DISABLE KEYS */;
INSERT INTO `product_types` VALUES ('088a5229-43ce-4770-8d11-58ec92b942f6','Hot Chocolate','Make From Pure Coca Beans','Ba2XSRkRvd - favicon - Cafe Project Backup.ico',20000.00,'2026-10-12 12:19:43','7d1dde7f-e259-47dc-810d-4308b8a60769',1,0,'manager-1','1'),('1','Coffee - Cappucino','A Hot Cup of Cappucino','',10000.00,'2026-09-27 23:52:18','7d1dde7f-e259-47dc-810d-4308b8a60769',0,1,'manager-1','1'),('15517e8c-b1e9-4ac6-93ad-a765403c068f','Green Tea','A Healthy Green Tea','',7000.00,'2026-10-13 05:22:05','7d1dde7f-e259-47dc-810d-4308b8a60769',0,1,'manager-1','1'),('2','Coffee - Espresso','A Hot Cup of Espresso','',10000.00,'2026-09-27 23:52:18','7d1dde7f-e259-47dc-810d-4308b8a60769',0,1,'manager-1','1'),('2f191df0-ee5c-433d-8649-71cd1cb6a160','Chocolate Cake','A Chocolate Cake','',6000.00,'2026-10-14 11:30:53','eed8283f-5288-4604-ae3a-a948dcac0604',0,1,'4b07f10c-6663-4530-9216-39b8a80c1152','1'),('836bc489-bd81-4e4c-a74c-21073ef46283','Strawberry Cake','Strawberry Cake',NULL,6000.00,'2026-10-14 11:33:04','eed8283f-5288-4604-ae3a-a948dcac0604',0,0,'4b07f10c-6663-4530-9216-39b8a80c1152','1'),('95ddf68e-d7df-4774-83ea-09c54d8b69f7','Coffee Bean Soup','A Hot Soup of Healthy Beans',NULL,6000.00,'2026-10-12 14:30:59','7d1dde7f-e259-47dc-810d-4308b8a60769',1,0,'manager-1','1'),('9860946c-a29d-4047-b8d0-1a78ff8a4f4d','Pumpkin Spice Latte','A pumpkin spice latte',NULL,15000.00,'2026-10-14 11:30:11','7d1dde7f-e259-47dc-810d-4308b8a60769',0,0,'4b07f10c-6663-4530-9216-39b8a80c1152','1'),('9a2bb4a0-3063-450f-9d6c-44498991aab8','Iced Coffee','A Cold Cup of Coffee','',10000.00,'2026-10-12 13:14:24','e8a57c6f-e77d-4b4d-8918-26854c3eb677',0,1,'manager-1','1'),('aa21cc73-731f-47ba-a465-707c8e5b7dc4','Donut','A J Donut',NULL,3000.00,'2026-10-14 11:33:43','eed8283f-5288-4604-ae3a-a948dcac0604',0,0,'4b07f10c-6663-4530-9216-39b8a80c1152','1'),('e5521bb0-e68f-45d9-89e0-cf027681cc72','Iced Tea','Iced Tea','',5000.00,'2026-10-12 13:13:18','e8a57c6f-e77d-4b4d-8918-26854c3eb677',0,1,'manager-1','1'),('f4934cee-0250-4fdc-8225-cd796f32e6ed','Cheese Cake','Cheese Cake','',6000.00,'2026-10-14 11:31:23','eed8283f-5288-4604-ae3a-a948dcac0604',0,1,'4b07f10c-6663-4530-9216-39b8a80c1152','1'),('f7434b6c-1f54-4f95-94f2-01afad192dc9','Iced Chocolate','A Cold Cup of Chocolate',NULL,10000.00,'2026-10-12 13:08:05','e8a57c6f-e77d-4b4d-8918-26854c3eb677',1,0,'manager-1','1');
/*!40000 ALTER TABLE `product_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` varchar(100) NOT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  `item_id` varchar(100) NOT NULL,
  `size_id` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `is_active` tinyint NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  KEY `fk_menu_items_has_size_menu_items1` (`item_id`),
  KEY `fk_menu_items_has_size_size1` (`size_id`),
  KEY `fk_menu_items_employees1` (`employee_id`),
  CONSTRAINT `fk_menu_items_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_menu_items_has_size_menu_items1` FOREIGN KEY (`item_id`) REFERENCES `product_types` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_menu_items_has_size_size1` FOREIGN KEY (`size_id`) REFERENCES `sizes` (`size_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('0f0235a8-1425-4d75-8a40-7fc1cf2010b6','manager-1','95ddf68e-d7df-4774-83ea-09c54d8b69f7','28694f3e-d646-425b-ad42-09d2bd1ef2bd',6000.00,0,1,1,'2026-10-12 14:30:59'),('1cbfb426-b636-48e5-9a0e-ed2a583c2b55',NULL,'1','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',10000.00,1,0,1,'2026-10-14 11:10:01'),('1db0b384-654f-4162-9c45-f0c97e92bf08','4b07f10c-6663-4530-9216-39b8a80c1152','aa21cc73-731f-47ba-a465-707c8e5b7dc4','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',3000.00,0,0,1,'2026-10-14 11:33:43'),('2069a97f-6f53-4fae-a487-f8badd0a0059',NULL,'e5521bb0-e68f-45d9-89e0-cf027681cc72','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',5000.00,1,0,1,'2026-10-14 11:06:52'),('3bef409b-b69c-4039-a4d9-5e5fc0ef5884','4b07f10c-6663-4530-9216-39b8a80c1152','836bc489-bd81-4e4c-a74c-21073ef46283','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',6000.00,0,0,1,'2026-10-14 11:33:04'),('42792efb-2b7b-4637-a1fe-4f7cb28d94ec',NULL,'9a2bb4a0-3063-450f-9d6c-44498991aab8','eddc0519-f861-4cf3-9345-6707dca5ef23',8000.00,1,0,1,'2026-10-14 11:02:22'),('48e71e79-f88a-4ce6-af9d-ea4b55c63542','4b07f10c-6663-4530-9216-39b8a80c1152','9860946c-a29d-4047-b8d0-1a78ff8a4f4d','20bde9d9-1f72-4c03-a9c1-99c93b47dff1',25000.00,0,0,1,'2026-10-14 11:30:11'),('493fb55d-c5ba-433f-b441-d6a59dcfe28f',NULL,'9a2bb4a0-3063-450f-9d6c-44498991aab8','4d1bc4e5-c882-4d2f-82bd-6379c8350243',12000.00,1,0,1,'2026-10-14 11:02:22'),('4a450f90-225d-4d88-9ef4-e814b802c340',NULL,'2','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',10000.00,1,0,1,'2026-10-14 11:25:31'),('7f81bf59-eb4f-40aa-83c5-aab8dac5525c',NULL,'e5521bb0-e68f-45d9-89e0-cf027681cc72','4d1bc4e5-c882-4d2f-82bd-6379c8350243',6500.00,0,0,1,'2026-10-14 11:06:52'),('88b9a373-e6c2-46d6-bbab-054dec0c8093',NULL,'1','eddc0519-f861-4cf3-9345-6707dca5ef23',8000.00,1,0,1,'2026-10-14 11:10:01'),('98d2581a-c9c0-4fbf-a393-ed56806d2be3',NULL,'15517e8c-b1e9-4ac6-93ad-a765403c068f','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',7000.00,1,0,1,'2026-10-14 06:34:56'),('9a5f2f9c-6bb6-4136-b8c6-84db7dd6ceda',NULL,'9a2bb4a0-3063-450f-9d6c-44498991aab8','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',10000.00,1,0,1,'2026-10-14 11:02:22'),('a902ea5e-0b9b-4cad-9a64-3b9632a64871',NULL,'2f191df0-ee5c-433d-8649-71cd1cb6a160','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',6000.00,1,0,1,'2026-10-14 11:32:32'),('a917ef26-459b-4df7-ae96-50f300b0001d','4b07f10c-6663-4530-9216-39b8a80c1152','9860946c-a29d-4047-b8d0-1a78ff8a4f4d','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',150000.00,0,0,1,'2026-10-14 11:30:11'),('c4e672cd-bef7-4eed-85fd-d640d8dc0ff9',NULL,'e5521bb0-e68f-45d9-89e0-cf027681cc72','eddc0519-f861-4cf3-9345-6707dca5ef23',4000.00,0,0,1,'2026-10-14 11:06:52'),('c8767528-377c-4c71-99c3-3f2086c384a7',NULL,'f4934cee-0250-4fdc-8225-cd796f32e6ed','5c5d1045-d7e7-4003-b7bf-7cafd6c54379',6000.00,1,0,1,'2026-10-14 11:32:17'),('ceda2d7d-ee1c-4a08-a854-6f4c65fa4b30',NULL,'15517e8c-b1e9-4ac6-93ad-a765403c068f','20bde9d9-1f72-4c03-a9c1-99c93b47dff1',8000.00,1,0,1,'2026-10-14 06:34:56'),('d270eade-612a-4c8e-9f42-e0a83c397c6f',NULL,'15517e8c-b1e9-4ac6-93ad-a765403c068f','4d1bc4e5-c882-4d2f-82bd-6379c8350243',7500.00,1,0,1,'2026-10-14 06:34:56'),('d7345435-f0dc-48fd-85ab-cc7c081a916c',NULL,'1','4d1bc4e5-c882-4d2f-82bd-6379c8350243',12000.00,1,0,1,'2026-10-14 11:10:01'),('deb610a3-7176-455d-b841-1421a3951b54',NULL,'2','4d1bc4e5-c882-4d2f-82bd-6379c8350243',12000.00,1,0,1,'2026-10-14 11:25:31'),('e74c143b-44e7-4573-8a11-151129c9f522','4b07f10c-6663-4530-9216-39b8a80c1152','9860946c-a29d-4047-b8d0-1a78ff8a4f4d','4d1bc4e5-c882-4d2f-82bd-6379c8350243',20000.00,0,0,1,'2026-10-14 11:30:11'),('ec927278-01d2-4c8f-a0a1-382c1fee1d1f',NULL,'2','eddc0519-f861-4cf3-9345-6707dca5ef23',8000.00,1,0,1,'2026-10-14 11:25:31');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_ingredients`
--

DROP TABLE IF EXISTS `products_ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_ingredients` (
  `product_id` varchar(100) NOT NULL,
  `ingredient_type_id` varchar(100) NOT NULL,
  `quantity_required` decimal(10,3) NOT NULL,
  PRIMARY KEY (`product_id`,`ingredient_type_id`),
  KEY `fk_menu_items_sizes_has_ingredients_ingredients1` (`ingredient_type_id`),
  CONSTRAINT `fk_menu_items_sizes_has_ingredients_ingredients1` FOREIGN KEY (`ingredient_type_id`) REFERENCES `ingredient_types` (`ingredient_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_menu_items_sizes_has_ingredients_menu_items_sizes1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_ingredients`
--

LOCK TABLES `products_ingredients` WRITE;
/*!40000 ALTER TABLE `products_ingredients` DISABLE KEYS */;
INSERT INTO `products_ingredients` VALUES ('0f0235a8-1425-4d75-8a40-7fc1cf2010b6','deleted',1.000),('0f0235a8-1425-4d75-8a40-7fc1cf2010b6','IT-1',20.000),('0f0235a8-1425-4d75-8a40-7fc1cf2010b6','IT-2',10.000),('1cbfb426-b636-48e5-9a0e-ed2a583c2b55','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.200),('1cbfb426-b636-48e5-9a0e-ed2a583c2b55','IT-1',0.250),('1db0b384-654f-4162-9c45-f0c97e92bf08','c0d92aa1-4ae7-4cfb-8620-d4fb5c0d1ce6',1.000),('2069a97f-6f53-4fae-a487-f8badd0a0059','831d9615-02e0-4c46-96b8-bb02030bbe60',0.150),('2069a97f-6f53-4fae-a487-f8badd0a0059','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.150),('3bef409b-b69c-4039-a4d9-5e5fc0ef5884','aae81176-e9c1-49d3-92a2-2d0199d6b31c',1.000),('42792efb-2b7b-4637-a1fe-4f7cb28d94ec','5d58be8d-c93e-411d-9076-10074f5d0eb0',0.150),('42792efb-2b7b-4637-a1fe-4f7cb28d94ec','89a6d50c-df90-42af-9d5d-9ce845e1bbee',0.150),('42792efb-2b7b-4637-a1fe-4f7cb28d94ec','fcd438ea-db78-4dbf-bc1b-133603349cbb',0.230),('42792efb-2b7b-4637-a1fe-4f7cb28d94ec','IT-1',0.070),('48e71e79-f88a-4ce6-af9d-ea4b55c63542','c8990c0d-3f08-4f8c-b28e-c23c645362f1',0.300),('48e71e79-f88a-4ce6-af9d-ea4b55c63542','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.300),('48e71e79-f88a-4ce6-af9d-ea4b55c63542','fcd438ea-db78-4dbf-bc1b-133603349cbb',0.300),('48e71e79-f88a-4ce6-af9d-ea4b55c63542','IT-1',0.500),('493fb55d-c5ba-433f-b441-d6a59dcfe28f','5d58be8d-c93e-411d-9076-10074f5d0eb0',0.200),('493fb55d-c5ba-433f-b441-d6a59dcfe28f','89a6d50c-df90-42af-9d5d-9ce845e1bbee',0.300),('493fb55d-c5ba-433f-b441-d6a59dcfe28f','fcd438ea-db78-4dbf-bc1b-133603349cbb',0.500),('493fb55d-c5ba-433f-b441-d6a59dcfe28f','IT-1',0.150),('4a450f90-225d-4d88-9ef4-e814b802c340','5d58be8d-c93e-411d-9076-10074f5d0eb0',0.300),('4a450f90-225d-4d88-9ef4-e814b802c340','89a6d50c-df90-42af-9d5d-9ce845e1bbee',0.300),('4a450f90-225d-4d88-9ef4-e814b802c340','IT-1',0.300),('7f81bf59-eb4f-40aa-83c5-aab8dac5525c','831d9615-02e0-4c46-96b8-bb02030bbe60',0.200),('7f81bf59-eb4f-40aa-83c5-aab8dac5525c','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.200),('88b9a373-e6c2-46d6-bbab-054dec0c8093','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.100),('88b9a373-e6c2-46d6-bbab-054dec0c8093','IT-1',0.200),('98d2581a-c9c0-4fbf-a393-ed56806d2be3','702640c2-6624-4b4a-8895-0766049ba976',0.100),('9a5f2f9c-6bb6-4136-b8c6-84db7dd6ceda','5d58be8d-c93e-411d-9076-10074f5d0eb0',0.200),('9a5f2f9c-6bb6-4136-b8c6-84db7dd6ceda','89a6d50c-df90-42af-9d5d-9ce845e1bbee',0.200),('9a5f2f9c-6bb6-4136-b8c6-84db7dd6ceda','fcd438ea-db78-4dbf-bc1b-133603349cbb',0.300),('9a5f2f9c-6bb6-4136-b8c6-84db7dd6ceda','IT-1',0.100),('a902ea5e-0b9b-4cad-9a64-3b9632a64871','2969bcb2-676e-4b59-adc7-5432da09fd37',1.000),('a917ef26-459b-4df7-ae96-50f300b0001d','c8990c0d-3f08-4f8c-b28e-c23c645362f1',0.100),('a917ef26-459b-4df7-ae96-50f300b0001d','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.100),('a917ef26-459b-4df7-ae96-50f300b0001d','fcd438ea-db78-4dbf-bc1b-133603349cbb',0.100),('a917ef26-459b-4df7-ae96-50f300b0001d','IT-1',0.300),('c4e672cd-bef7-4eed-85fd-d640d8dc0ff9','831d9615-02e0-4c46-96b8-bb02030bbe60',0.100),('c4e672cd-bef7-4eed-85fd-d640d8dc0ff9','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.100),('c8767528-377c-4c71-99c3-3f2086c384a7','87b2d5e4-ccb2-49e1-87d5-a771d543eec5',1.000),('ceda2d7d-ee1c-4a08-a854-6f4c65fa4b30','702640c2-6624-4b4a-8895-0766049ba976',0.500),('d270eade-612a-4c8e-9f42-e0a83c397c6f','702640c2-6624-4b4a-8895-0766049ba976',0.300),('d7345435-f0dc-48fd-85ab-cc7c081a916c','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.250),('d7345435-f0dc-48fd-85ab-cc7c081a916c','IT-1',0.300),('deb610a3-7176-455d-b841-1421a3951b54','5d58be8d-c93e-411d-9076-10074f5d0eb0',0.400),('deb610a3-7176-455d-b841-1421a3951b54','89a6d50c-df90-42af-9d5d-9ce845e1bbee',0.400),('deb610a3-7176-455d-b841-1421a3951b54','IT-1',0.400),('e74c143b-44e7-4573-8a11-151129c9f522','c8990c0d-3f08-4f8c-b28e-c23c645362f1',0.200),('e74c143b-44e7-4573-8a11-151129c9f522','fa45cb10-9f6d-4f76-8f6e-b2abeb90f157',0.200),('e74c143b-44e7-4573-8a11-151129c9f522','fcd438ea-db78-4dbf-bc1b-133603349cbb',0.200),('e74c143b-44e7-4573-8a11-151129c9f522','IT-1',0.400),('ec927278-01d2-4c8f-a0a1-382c1fee1d1f','5d58be8d-c93e-411d-9076-10074f5d0eb0',0.200),('ec927278-01d2-4c8f-a0a1-382c1fee1d1f','89a6d50c-df90-42af-9d5d-9ce845e1bbee',0.200),('ec927278-01d2-4c8f-a0a1-382c1fee1d1f','IT-1',0.200);
/*!40000 ALTER TABLE `products_ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo_types`
--

DROP TABLE IF EXISTS `promo_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promo_types` (
  `promo_type_id` varchar(100) NOT NULL,
  `type_name` varchar(45) NOT NULL,
  PRIMARY KEY (`promo_type_id`),
  UNIQUE KEY `uq_promo_types_type_name` (`type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo_types`
--

LOCK TABLES `promo_types` WRITE;
/*!40000 ALTER TABLE `promo_types` DISABLE KEYS */;
INSERT INTO `promo_types` VALUES ('550d4b91-2b36-4d94-8706-0d921ad8e262','Age Related'),('deleted','deleted'),('d9b25a5e-1d64-49ca-a254-d1ac8cf97ad7','Depends on The Person'),('bdff88b1-1da0-4cb8-b2c1-0099b1662ca0','Near Expiry'),('d7847d54-7a69-4066-82b4-b5d7176c0083','Seasonal');
/*!40000 ALTER TABLE `promo_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register_statuses`
--

DROP TABLE IF EXISTS `register_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `register_statuses` (
  `register_status_id` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`register_status_id`),
  UNIQUE KEY `uq_register_statuses_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register_statuses`
--

LOCK TABLES `register_statuses` WRITE;
/*!40000 ALTER TABLE `register_statuses` DISABLE KEYS */;
INSERT INTO `register_statuses` VALUES ('2','CLOSED'),('1','OPENED');
/*!40000 ALTER TABLE `register_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sizes`
--

DROP TABLE IF EXISTS `sizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sizes` (
  `size_id` varchar(100) NOT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `size_code` varchar(10) NOT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`size_id`),
  KEY `fk_sizes_employees1` (`employee_id`),
  CONSTRAINT `fk_sizes_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sizes`
--

LOCK TABLES `sizes` WRITE;
/*!40000 ALTER TABLE `sizes` DISABLE KEYS */;
INSERT INTO `sizes` VALUES ('20bde9d9-1f72-4c03-a9c1-99c93b47dff1','admin','Extra Large','XL',1,1,0,'2026-10-02 04:26:49'),('28694f3e-d646-425b-ad42-09d2bd1ef2bd','admin','Extra Extra Large','XXL',0,1,0,'2026-10-02 04:28:02'),('4d1bc4e5-c882-4d2f-82bd-6379c8350243','admin','Large','L',1,1,0,'2026-09-27 17:44:15'),('5c5d1045-d7e7-4003-b7bf-7cafd6c54379','admin','Normal','N',1,1,0,'2026-09-27 17:42:36'),('deleted','deleted','deleted','d',0,0,0,'2026-09-27 17:45:35'),('eddc0519-f861-4cf3-9345-6707dca5ef23','admin','Small','S',1,1,0,'2026-09-27 17:45:35');
/*!40000 ALTER TABLE `sizes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_imports`
--

DROP TABLE IF EXISTS `stock_imports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_imports` (
  `import_id` varchar(100) NOT NULL,
  `imported_at` datetime NOT NULL,
  `employee_id` varchar(100) NOT NULL,
  `branch_id` varchar(100) NOT NULL,
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `supplier_id` varchar(100) NOT NULL,
  `total_cost` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`import_id`),
  KEY `fk_import_stocks_employees1` (`employee_id`),
  KEY `fk_import_stocks_branches1` (`branch_id`),
  KEY `fk_stock_imports_suppliers1` (`supplier_id`),
  CONSTRAINT `fk_import_stocks_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_import_stocks_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_stock_imports_suppliers1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_imports`
--

LOCK TABLES `stock_imports` WRITE;
/*!40000 ALTER TABLE `stock_imports` DISABLE KEYS */;
INSERT INTO `stock_imports` VALUES ('1c93c8a1-4765-4a00-8727-8357c6415699','2026-10-14 09:35:00','4b07f10c-6663-4530-9216-39b8a80c1152','1',0,0,'2026-10-14 06:18:09','d40e8aec-91de-412d-9935-3a289e265170',950000.00),('69d276fb-15ad-40b8-80f2-d0cf79dabd76','2026-10-14 06:15:00','4b07f10c-6663-4530-9216-39b8a80c1152','1',0,0,'2026-10-14 06:16:04','391d81de-fa93-4f43-9879-33967966b092',575000.00),('db4c094e-ae47-4e79-b528-02280955217f','2026-10-07 07:11:00','4b07f10c-6663-4530-9216-39b8a80c1152','1',0,0,'2026-10-14 06:13:54','0f772a8b-4704-462e-97d9-b827d5d9f0dc',2190000.00),('deleted','2026-10-02 02:00:09','manager-1','deleted',0,0,'2026-10-02 02:00:09','deleted',0.00);
/*!40000 ALTER TABLE `stock_imports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `supplier_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `contact_info` varchar(150) DEFAULT NULL,
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`supplier_id`),
  UNIQUE KEY `uq_suppliers_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES ('0f772a8b-4704-462e-97d9-b827d5d9f0dc','Local Market','Market Head / 09131213121',0,'2026-10-14 05:20:03'),('2f5ff459-0ec7-41bb-a28f-ae821ffd1b6a','Test Delete','CoffeeBeans / 09121212121',1,'2026-10-13 16:45:27'),('33eabed3-ec92-429d-ad84-faf468d7f67d','City Mart','Yangon / 09191919192',0,'2026-10-01 11:39:54'),('391d81de-fa93-4f43-9879-33967966b092','Food Panda','Food Panda / 09555554444',0,'2026-10-14 05:19:29'),('d40e8aec-91de-412d-9935-3a289e265170','One Stop Mart','One Stop Mart / 09121212121',0,'2026-10-14 05:18:04'),('deleted','deleted','deleted',0,'2026-10-01 11:39:54');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `units` (
  `unit_id` varchar(100) NOT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `abbreviation` varchar(10) NOT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `isedited` tinyint NOT NULL DEFAULT '0',
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`unit_id`),
  KEY `fk_units_employees1` (`employee_id`),
  CONSTRAINT `fk_units_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES ('0ae229a0-d3f3-4454-9347-e3410a3e0298','admin','Gallon','gl',1,1,0,'2026-09-27 23:07:00'),('349378ce-b353-48c1-8d76-c49d03df107d','admin','Gram','g',1,1,0,'2026-09-27 23:05:47'),('39b57a66-c914-4a97-9d9c-6e9a79bec84f','admin','Liter','l',1,1,0,'2026-09-27 23:06:12'),('3c1e36ca-06c0-408a-803f-9109e89c995c','manager-1','Gallon','GL',1,1,1,'2026-09-27 23:07:12'),('5abcd6e3-0306-45e2-8d17-355ff277fba1','admin','Test Unit','TU',1,1,1,'2026-09-30 23:30:40'),('5dd2950d-00fe-4d06-8827-9b37c04d1776','manager-1','Test 2 Unit','T2',0,0,1,'2026-10-01 03:43:51'),('93a84c10-72e2-4152-9a4e-92ddd931e770','admin','Pound','lb',1,0,0,'2026-10-14 05:15:46'),('bacaf0ae-e83b-4756-a458-7c88b5b04506','manager-1','Gram','G',0,1,1,'2026-09-27 23:06:01'),('d3150847-e73e-4d09-b3d5-2256f9246ae5','manager-1','Kilogram2','Kg',0,0,1,'2026-09-27 23:05:25'),('deleted','deleted','deleted','deleted',0,0,0,'2026-09-27 23:05:12'),('e137d54a-e5c5-412f-b397-06a8a74654cb','admin','Pieces','pcs',1,0,0,'2026-10-14 05:21:15'),('f4013d51-8b44-455c-ad52-1eaf29218c6a','manager-1','Gallon','GL',0,0,1,'2026-09-27 23:06:35'),('fd6ac73e-1dd9-491e-becd-f7b20dfb569f','admin','Kilogram','kg',1,1,0,'2026-09-27 23:05:12');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waste_logs`
--

DROP TABLE IF EXISTS `waste_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `waste_logs` (
  `waste_id` varchar(100) NOT NULL,
  `batch_id` varchar(100) NOT NULL,
  `waste_reason_id` varchar(100) NOT NULL,
  `quantity_lost` decimal(10,3) NOT NULL,
  `financial_loss` decimal(10,2) NOT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  `logged_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `notes` varchar(255) DEFAULT NULL,
  `isdeleted` tinyint NOT NULL DEFAULT '0',
  `branch_id` varchar(100) NOT NULL,
  PRIMARY KEY (`waste_id`),
  KEY `fk_waste_logs_ingredient_batches1` (`batch_id`),
  KEY `fk_waste_logs_waste_reasons1` (`waste_reason_id`),
  KEY `fk_waste_logs_employees1` (`employee_id`),
  KEY `fk_waste_logs_branches1_idx` (`branch_id`),
  CONSTRAINT `fk_waste_logs_branches1` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`),
  CONSTRAINT `fk_waste_logs_employees1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_waste_logs_ingredient_batches1` FOREIGN KEY (`batch_id`) REFERENCES `ingredient_batches` (`batch_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_waste_logs_waste_reasons1` FOREIGN KEY (`waste_reason_id`) REFERENCES `waste_reasons` (`waste_reason_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waste_logs`
--

LOCK TABLES `waste_logs` WRITE;
/*!40000 ALTER TABLE `waste_logs` DISABLE KEYS */;
INSERT INTO `waste_logs` VALUES ('1e982715-d655-4cab-aeb9-acc51695e411','deleted','9b328593-87c5-4279-aff6-8dc5e635f94c',1212.000,1468944.00,'manager-1','2026-10-13 05:14:27','',0,'1'),('35566ec8-290e-4489-a270-95aa66a5812d','deleted','9b328593-87c5-4279-aff6-8dc5e635f94c',20.000,2000.00,'manager-1','2026-09-03 05:21:10','',0,'1'),('d8d63fd2-afa4-49cf-97dc-efba5931162d','deleted','9b328593-87c5-4279-aff6-8dc5e635f94c',2.000,40.00,'manager-1','2026-10-09 05:21:10','',0,'1');
/*!40000 ALTER TABLE `waste_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waste_reasons`
--

DROP TABLE IF EXISTS `waste_reasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `waste_reasons` (
  `waste_reason_id` varchar(100) NOT NULL,
  `reason_name` varchar(45) NOT NULL,
  PRIMARY KEY (`waste_reason_id`),
  UNIQUE KEY `uq_waste_reasons_reason_name` (`reason_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waste_reasons`
--

LOCK TABLES `waste_reasons` WRITE;
/*!40000 ALTER TABLE `waste_reasons` DISABLE KEYS */;
INSERT INTO `waste_reasons` VALUES ('a5d93297-9e46-47d8-8c6a-6448a3402ca2','Accidents'),('9b328593-87c5-4279-aff6-8dc5e635f94c','Expired'),('343b9cca-1fa6-40c4-9fd2-6ac12978c3af','Low Quality Imports'),('a43e9873-f51b-4c4c-85ec-d7858e86f874','Pest Infestation'),('27125e04-5092-4357-b945-f179a2ff0151','Stolen');
/*!40000 ALTER TABLE `waste_reasons` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-14 12:23:14
