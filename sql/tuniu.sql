/*
 途牛旅游平台 - 数据库初始化脚本（微服务版）
 Source Schema: tuniu
 由原 TuNiu-Service/src/main/resources/sql/tuniu.sql 迁移并对齐新微服务域模型
 主要变更：order_detail.buy_nums(varchar) -> buy_num(int)
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 数据库
-- ----------------------------
DROP DATABASE IF EXISTS `tuniu`;
CREATE DATABASE `tuniu` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `tuniu`;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL DEFAULT NULL,
  `lng` varchar(255) NULL DEFAULT NULL,
  `lat` varchar(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `city` VALUES (1, '北京', '116.404', '39.915');
INSERT INTO `city` VALUES (2, '上海', '120.52', '30.40');
INSERT INTO `city` VALUES (3, '杭州', '118.21', '120.30');
INSERT INTO `city` VALUES (4, '广州', '23.06', '113.17');
INSERT INTO `city` VALUES (5, '南京', '31.14', '118.22');
INSERT INTO `city` VALUES (6, '武汉', '29.58', '113.41');
INSERT INTO `city` VALUES (7, '郑州', '112.42', '34.16');
INSERT INTO `city` VALUES (8, '长沙', '28.12', '112.59');
INSERT INTO `city` VALUES (9, '深圳', '113.46', '22.27');
INSERT INTO `city` VALUES (10, '成都', '104.06', '30.67');
INSERT INTO `city` VALUES (11, '西安', '107.40', '33.42');
INSERT INTO `city` VALUES (12, '合肥', '117.17', '31.52');
INSERT INTO `city` VALUES (13, '重庆', '105.11', '28.10');
INSERT INTO `city` VALUES (14, '汉口', '113.41', '29.58');
INSERT INTO `city` VALUES (15, '济南', '36.40', '117.00');
INSERT INTO `city` VALUES (16, '天津', '117.195', '39.085');
INSERT INTO `city` VALUES (17, '苏州', '119.55', '30.47');
INSERT INTO `city` VALUES (18, '沈阳', '123.25', '41.48');
INSERT INTO `city` VALUES (19, '青岛', '119.30', '35.35');
INSERT INTO `city` VALUES (20, '厦门', '118.04', '24.26');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NULL DEFAULT NULL,
  `password` varchar(255) NULL DEFAULT NULL,
  `name` varchar(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `user` VALUES (1, '1', 'a7cb7a28fa09e36863e08268a6408f455b5705e712b4bd5a', '王俊凯');
INSERT INTO `user` VALUES (2, '202409071', '095431967d31e6f510713284b9da3a25b35c149055468d8a', '何丹彤');
INSERT INTO `user` VALUES (3, '202409072', '49589dd2f15ef85b3c79fd6a21f314f5980026f79847f58e', '赵丽颖');
INSERT INTO `user` VALUES (9, '202409070', 'a22d18f4011af97316515120288887c4f418b5ba4d184830', '赛尔号');
INSERT INTO `user` VALUES (10, '202409090', '53916b52958b253202f89f69b39012d4ea14442a3ee49592', '无敌');
INSERT INTO `user` VALUES (11, '202509240', '796f0b615e4480df8b168b2c88079aa6720f20966f529504', '123');

-- ----------------------------
-- Table structure for passenger
-- ----------------------------
DROP TABLE IF EXISTS `passenger`;
CREATE TABLE `passenger`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL DEFAULT NULL,
  `type` varchar(255) NULL DEFAULT NULL,
  `id_card` varchar(255) NULL DEFAULT NULL,
  `phone` varchar(255) NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `card_type` varchar(255) NULL DEFAULT NULL,
  `country` varchar(255) NULL DEFAULT NULL,
  `gender` varchar(255) NULL DEFAULT NULL,
  `often` varchar(255) NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_passenger_id_card`(`id_card` ASC) USING BTREE,
  INDEX `idx_passenger_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `passenger` VALUES (1, '小鸭', '学生票', '1111111111', '15803275423', 1, '身份证', '中国', '男', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (2, '北邙', '成人票', '112233', '778899', 1, '身份证', '中国', '男', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (3, '二狗', '儿童', '554455', '243221', 1, '身份证', '中国', '男', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (4, '你好', '儿童', '17852439', '13521038796', 1, '身份证', '中国', '男', '有', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (5, '我的天啊', '儿童', '1122334455', '18833736206', 1, '身份证', '中国', '女', '有', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (6, '儿女情长', '成人票', '109641325452', '18633172918', 1, '身份证', '中国', '女', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (8, '哈哈', '成人票', '123', '546', 1, '身份证', '中国', '男', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (9, '我打打', '成人票', '23453', '2356', 1, '身份证', '中国', '男', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (10, '三个地方', '儿童', '5678', '564', 1, '身份证', '中国', '男', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (11, '合同改好', '儿童', '235', '74564', 1, '身份证', '中国', '女', '无', '2025-12-31 17:27:41', 0);
INSERT INTO `passenger` VALUES (12, '我去饿', '学生票', '8465', '23425', 1, '身份证', '中国', '女', '无', '2025-12-31 17:27:41', 0);

-- ----------------------------
-- Table structure for train_tickets
-- ----------------------------
DROP TABLE IF EXISTS `train_tickets`;
CREATE TABLE `train_tickets`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `train_number` varchar(255) NULL DEFAULT NULL COMMENT '列车号',
  `start_station` varchar(255) NULL DEFAULT NULL COMMENT '出发站',
  `end_station` varchar(255) NULL DEFAULT NULL COMMENT '终点站',
  `start_time` datetime NULL DEFAULT NULL COMMENT '出发时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '到达时间',
  `seat_type` varchar(255) NULL DEFAULT NULL COMMENT '座位等级',
  `seat_number` varchar(255) NULL DEFAULT NULL COMMENT '座位号',
  `price` int NULL DEFAULT NULL COMMENT '价格',
  `available_seats` int NULL DEFAULT NULL COMMENT '余票',
  `lasts` varchar(255) NULL DEFAULT NULL COMMENT '运行时长',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `train_tickets` VALUES (1, 'G123', '北京', '上海', '2025-12-25 20:33:36', '2026-01-01 22:33:43', '商务座', '01A', 500, 19, '02:00');
INSERT INTO `train_tickets` VALUES (2, 'T5678', '上海', '北京', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '经济座', '05C', 200, 20, '02:00');
INSERT INTO `train_tickets` VALUES (3, 'D9101', '杭州', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '02F', 100, 5, '02:00');
INSERT INTO `train_tickets` VALUES (4, 'D9235', '广州', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '07E', 800, 5, '02:00');
INSERT INTO `train_tickets` VALUES (5, 'D345', '南京', '上海', '2024-09-20 20:33:36', '2024-09-20 22:33:43', '一等座', '03A', 400, 2, '02:00');
INSERT INTO `train_tickets` VALUES (6, 'D4689', '武汉', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '01A', 333, 3, '02:00');
INSERT INTO `train_tickets` VALUES (7, 'T3456', '郑州', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '14F', 225, 4, '02:00');
INSERT INTO `train_tickets` VALUES (8, 'G6743', '长沙', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '03B', 153, 5, '02:00');
INSERT INTO `train_tickets` VALUES (9, 'D6578', '深圳', '上海', '2024-09-20 20:33:36', '2024-09-20 22:33:43', '一等座', '11C', 125, 6, '02:00');
INSERT INTO `train_tickets` VALUES (10, 'D546', '成都', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '21A', 436, 7, '02:00');
INSERT INTO `train_tickets` VALUES (11, 'T1235', '西安', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '20F', 746, 8, '02:00');
INSERT INTO `train_tickets` VALUES (12, 'G7549', '合肥', '上海', '2024-09-18 20:33:36', '2024-09-18 22:33:43', '一等座', '16B', 243, 9, '02:00');
INSERT INTO `train_tickets` VALUES (13, 'G2356', '北京', '上海', '2024-09-20 12:33:36', '2024-09-20 13:33:43', '商务座', '12C', 200, 7, '01:00');
INSERT INTO `train_tickets` VALUES (14, 'T7788', '北京', '上海', '2024-09-20 16:33:36', '2024-09-20 20:33:43', '商务座', '33B', 1000, 7, '04:00');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '哪个用户下的订单',
  `time` datetime NULL DEFAULT NULL COMMENT '下单时间',
  `status` varchar(255) NULL DEFAULT NULL COMMENT '订单状态',
  `total_price` int NULL DEFAULT NULL COMMENT '总价',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_orders_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `orders` VALUES (3, 1, '2024-09-18 17:15:40', '已支付', 2000, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (4, 1, '2024-09-18 17:23:12', '已支付', 3000, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (5, 1, '2024-09-18 17:25:19', '已支付', 1000, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (6, 1, '2024-09-18 20:55:55', '已支付', 400, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (7, 1, '2024-09-18 21:16:42', '已支付', 400, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (8, 1, '2024-09-18 21:30:27', '已支付', 3000, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (11, 1, '2025-09-29 22:00:20', '待支付', 500, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (12, 1, '2025-09-29 22:01:27', '待支付', 500, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (13, 1, '2025-09-29 22:05:52', '待支付', 500, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (14, 1, '2025-11-01 15:17:04', '待支付', 1000, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (16, 1, '2025-11-01 15:21:07', '待支付', 1000, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (19, 1, '2025-11-01 15:30:46', '待支付', 500, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (20, 1, '2025-12-11 16:18:59', '待支付', 500, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (21, 1, '2025-12-25 19:25:13', '待支付', 500, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (22, 1, '2025-12-25 19:28:05', '待支付', 500, '2025-12-31 17:27:41', 0);
INSERT INTO `orders` VALUES (23, 1, '2025-12-25 19:29:37', '待支付', 500, '2025-12-31 17:27:41', 0);

-- ----------------------------
-- Table structure for order_detail
-- 变更：原 buy_nums(varchar) -> buy_num(int)，对齐新域模型 OrderDetail.buyNum
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NULL DEFAULT NULL COMMENT '订单id，对应订单表',
  `ticket_type` varchar(255) NULL DEFAULT NULL COMMENT '订单类型，区分火车机票',
  `ticket_id` int NULL DEFAULT NULL COMMENT '订单id，对应对应的票',
  `passenger_id` int NULL DEFAULT NULL COMMENT '乘客id，对应乘客表',
  `buy_num` int NULL DEFAULT 1 COMMENT '购买数量',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_detail_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_order_detail_ticket_id`(`ticket_id` ASC) USING BTREE,
  INDEX `idx_order_detail_passenger_id`(`passenger_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `order_detail` VALUES (4, 3, '火车票', 14, 11, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (5, 3, '火车票', 14, 3, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (6, 4, '火车票', 14, 11, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (7, 4, '火车票', 14, 3, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (8, 4, '火车票', 14, 12, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (9, 5, '火车票', 13, 12, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (10, 5, '火车票', 13, 4, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (11, 5, '火车票', 13, 5, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (12, 5, '火车票', 13, 6, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (13, 5, '火车票', 13, 8, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (14, 6, '火车票', 13, 3, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (15, 6, '火车票', 13, 9, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (16, 7, '火车票', 13, 12, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (17, 7, '火车票', 13, 4, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (18, 8, '火车票', 14, 11, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (19, 8, '火车票', 14, 10, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (20, 8, '火车票', 14, 6, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (21, 11, 'train', 1, 5, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (22, 12, 'train', 1, 5, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (23, 13, 'train', 1, 5, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (24, 14, 'train', 1, 4, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (25, 14, 'train', 1, 12, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (28, 16, 'train', 1, 5, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (29, 16, 'train', 1, 12, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (33, 19, 'train', 1, 4, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (34, 20, 'train', 1, 5, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (35, 21, 'train', 1, 11, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (36, 22, 'train', 1, 12, 1, '2025-12-31 17:27:41');
INSERT INTO `order_detail` VALUES (37, 23, 'train', 1, 12, 1, '2025-12-31 17:27:41');

SET FOREIGN_KEY_CHECKS = 1;
