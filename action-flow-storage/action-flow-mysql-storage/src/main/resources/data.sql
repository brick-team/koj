/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1(root@root123@）
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : action-flow

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 31/03/2022 15:56:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for af_action
-- ----------------------------
DROP TABLE IF EXISTS `af_action`;
CREATE TABLE `af_action` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `async` bit(1) DEFAULT NULL,
  `clazz_str` varchar(255) DEFAULT NULL,
  `method_str` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_action
-- ----------------------------
BEGIN;
INSERT INTO `af_action` (`id`, `async`, `clazz_str`, `method_str`) VALUES (1, b'0', 'com.github.brick.action.flow.LoginAction', 'login');
INSERT INTO `af_action` (`id`, `async`, `clazz_str`, `method_str`) VALUES (2, b'0', 'com.github.brick.action.flow.SendPointAction', 'sendPoint');
COMMIT;

-- ----------------------------
-- Table structure for af_action_param
-- ----------------------------
DROP TABLE IF EXISTS `af_action_param`;
CREATE TABLE `af_action_param` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_id` bigint DEFAULT NULL,
  `arg_name` varchar(255) DEFAULT NULL,
  `index` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_action_param
-- ----------------------------
BEGIN;
INSERT INTO `af_action_param` (`id`, `action_id`, `arg_name`, `index`, `type`) VALUES (1, 1, 'username', 0, 'java.lang.String');
INSERT INTO `af_action_param` (`id`, `action_id`, `arg_name`, `index`, `type`) VALUES (2, 1, 'password', 1, 'java.lang.String');
INSERT INTO `af_action_param` (`id`, `action_id`, `arg_name`, `index`, `type`) VALUES (3, 2, 'uid', 0, 'java.lang.String');
INSERT INTO `af_action_param` (`id`, `action_id`, `arg_name`, `index`, `type`) VALUES (4, 2, 'point', 1, 'java.lang.Integer');
COMMIT;

-- ----------------------------
-- Table structure for af_action_param_ex
-- ----------------------------
DROP TABLE IF EXISTS `af_action_param_ex`;
CREATE TABLE `af_action_param_ex` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_param_id` varchar(255) DEFAULT NULL,
  `ex` varchar(255) DEFAULT NULL,
  `ex_id` varchar(255) DEFAULT NULL,
  `flow_id` varchar(255) DEFAULT NULL,
  `format_id` varchar(255) DEFAULT NULL,
  `param_group_id` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_action_param_ex
-- ----------------------------
BEGIN;
INSERT INTO `af_action_param_ex` (`id`, `action_param_id`, `ex`, `ex_id`, `flow_id`, `format_id`, `param_group_id`, `value`) VALUES (12, '4', NULL, NULL, '3', '1', NULL, '10');
INSERT INTO `af_action_param_ex` (`id`, `action_param_id`, `ex`, `ex_id`, `flow_id`, `format_id`, `param_group_id`, `value`) VALUES (11, '3', 'username', NULL, '3', NULL, 'a', NULL);
INSERT INTO `af_action_param_ex` (`id`, `action_param_id`, `ex`, `ex_id`, `flow_id`, `format_id`, `param_group_id`, `value`) VALUES (10, '2', 'password', NULL, '3', NULL, 'a', NULL);
INSERT INTO `af_action_param_ex` (`id`, `action_param_id`, `ex`, `ex_id`, `flow_id`, `format_id`, `param_group_id`, `value`) VALUES (9, '1', 'username', NULL, '3', NULL, 'a', NULL);
COMMIT;

-- ----------------------------
-- Table structure for af_api
-- ----------------------------
DROP TABLE IF EXISTS `af_api`;
CREATE TABLE `af_api` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `desca` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_api
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_api_param
-- ----------------------------
DROP TABLE IF EXISTS `af_api_param`;
CREATE TABLE `af_api_param` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `api_id` bigint DEFAULT NULL,
  `in` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  `require` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_api_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_api_param_ex
-- ----------------------------
DROP TABLE IF EXISTS `af_api_param_ex`;
CREATE TABLE `af_api_param_ex` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `api_param_id` bigint DEFAULT NULL,
  `el` varchar(255) DEFAULT NULL,
  `ex` varchar(255) DEFAULT NULL,
  `ex_id` varchar(255) DEFAULT NULL,
  `flow_id` bigint DEFAULT NULL,
  `param_group` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_api_param_ex
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_extract
-- ----------------------------
DROP TABLE IF EXISTS `af_extract`;
CREATE TABLE `af_extract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `el` varchar(255) DEFAULT NULL,
  `el_type` varchar(255) DEFAULT NULL,
  `from_action` bigint DEFAULT NULL,
  `from_api` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_extract
-- ----------------------------
BEGIN;
INSERT INTO `af_extract` (`id`, `el`, `el_type`, `from_action`, `from_api`) VALUES (1, '$.username', 'JSON_PATH', 1, NULL);
INSERT INTO `af_extract` (`id`, `el`, `el_type`, `from_action`, `from_api`) VALUES (2, '$.login_time', 'JSON_PATH', 1, NULL);
INSERT INTO `af_extract` (`id`, `el`, `el_type`, `from_action`, `from_api`) VALUES (3, '$.age', 'JSON_PATH', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for af_flow
-- ----------------------------
DROP TABLE IF EXISTS `af_flow`;
CREATE TABLE `af_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `works` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_flow
-- ----------------------------
BEGIN;
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES (1, 'action1', NULL);
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES (2, 'action1', NULL);
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES (3, 'tttt', NULL);
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES (4, 'tttt', NULL);
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES (5, 'tttt', NULL);
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES (6, 'tttt', NULL);
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES (7, 'tttt', NULL);
COMMIT;

-- ----------------------------
-- Table structure for af_format
-- ----------------------------
DROP TABLE IF EXISTS `af_format`;
CREATE TABLE `af_format` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_str` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_format
-- ----------------------------
BEGIN;
INSERT INTO `af_format` (`id`, `class_str`) VALUES (1, 'com.github.brick.action.flow.method.format.num.StringToIntegerFormat');
COMMIT;

-- ----------------------------
-- Table structure for af_param
-- ----------------------------
DROP TABLE IF EXISTS `af_param`;
CREATE TABLE `af_param` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group` varchar(255) NOT NULL COMMENT '参数组',
  `key` varchar(255) NOT NULL COMMENT '参数键',
  `value` varchar(255) DEFAULT NULL COMMENT '参数值',
  `flow_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_param
-- ----------------------------
BEGIN;
INSERT INTO `af_param` (`id`, `group`, `key`, `value`, `flow_id`) VALUES (1, 'a', 'password', 'password', 3);
INSERT INTO `af_param` (`id`, `group`, `key`, `value`, `flow_id`) VALUES (2, 'a', 'username', 'username', 3);
INSERT INTO `af_param` (`id`, `group`, `key`, `value`, `flow_id`) VALUES (3, 'a', 'password', 'password', 3);
COMMIT;

-- ----------------------------
-- Table structure for af_result
-- ----------------------------
DROP TABLE IF EXISTS `af_result`;
CREATE TABLE `af_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ex_id` varchar(255) DEFAULT NULL,
  `flow_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_result
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_watcher
-- ----------------------------
DROP TABLE IF EXISTS `af_watcher`;
CREATE TABLE `af_watcher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `condition` varchar(255) NOT NULL,
  `ex_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_watcher
-- ----------------------------
BEGIN;
INSERT INTO `af_watcher` (`id`, `condition`, `ex_id`) VALUES (1, '>10', 3);
COMMIT;

-- ----------------------------
-- Table structure for af_watcher_rs
-- ----------------------------
DROP TABLE IF EXISTS `af_watcher_rs`;
CREATE TABLE `af_watcher_rs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ref_id` bigint NOT NULL,
  `ref_type` varchar(255) NOT NULL,
  `type` int DEFAULT NULL,
  `watcher_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_watcher_rs
-- ----------------------------
BEGIN;
INSERT INTO `af_watcher_rs` (`id`, `ref_id`, `ref_type`, `type`, `watcher_id`) VALUES (1, 2, 'action', 1, 1);
INSERT INTO `af_watcher_rs` (`id`, `ref_id`, `ref_type`, `type`, `watcher_id`) VALUES (2, 2, 'action', 1, 1);
INSERT INTO `af_watcher_rs` (`id`, `ref_id`, `ref_type`, `type`, `watcher_id`) VALUES (3, 2, 'action', 2, 1);
COMMIT;

-- ----------------------------
-- Table structure for af_work
-- ----------------------------
DROP TABLE IF EXISTS `af_work`;
CREATE TABLE `af_work` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ref_id` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_work
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_work_cat
-- ----------------------------
DROP TABLE IF EXISTS `af_work_cat`;
CREATE TABLE `af_work_cat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint DEFAULT NULL,
  `ref_id` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `work_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_work_cat
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_work_cz
-- ----------------------------
DROP TABLE IF EXISTS `af_work_cz`;
CREATE TABLE `af_work_cz` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `flow_id` bigint DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  `ref_id` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `work_type` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_work_cz
-- ----------------------------
BEGIN;
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (59, 6, 58, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (66, 7, 65, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (65, 7, 64, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (64, 7, 63, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (63, 7, 61, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (62, 7, 61, 1, 'watcher', 3);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (61, 7, NULL, 1, 'action', 1);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (60, 6, 59, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (58, 6, 57, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (57, 6, 55, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (56, 6, 55, 1, 'watcher', 3);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (55, 6, NULL, 1, 'action', 1);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (54, 5, 53, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (53, 5, 52, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (52, 5, 51, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (51, 5, 49, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (50, 5, 49, 1, 'watcher', 3);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (49, 5, NULL, 1, 'action', 1);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (48, 4, 47, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (47, 4, 46, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (46, 4, 45, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (45, 4, 43, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (44, 4, 43, 1, 'watcher', 3);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (43, 4, NULL, 1, 'action', 1);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (42, 3, 41, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (41, 3, 40, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (40, 3, 39, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (39, 3, 37, 1, 'watcher', 2);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (38, 3, 37, 1, 'watcher', 3);
INSERT INTO `af_work_cz` (`id`, `flow_id`, `pid`, `ref_id`, `type`, `work_type`) VALUES (37, 3, NULL, 1, 'action', 1);
COMMIT;

-- ----------------------------
-- Table structure for af_work_then
-- ----------------------------
DROP TABLE IF EXISTS `af_work_then`;
CREATE TABLE `af_work_then` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint DEFAULT NULL,
  `ref_id` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `work_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_work_then
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES (1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
