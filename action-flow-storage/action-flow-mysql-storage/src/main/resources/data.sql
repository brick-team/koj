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

 Date: 30/03/2022 14:43:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for af_action
-- ----------------------------
DROP TABLE IF EXISTS `af_action`;
CREATE TABLE `af_action` (
  `id` varchar(255) NOT NULL,
  `clazz_str` varchar(255) NOT NULL,
  `method_str` varchar(255) NOT NULL,
  `async` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_action
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_action_param
-- ----------------------------
DROP TABLE IF EXISTS `af_action_param`;
CREATE TABLE `af_action_param` (
  `id` varchar(255) NOT NULL,
  `action_id` varchar(255) NOT NULL COMMENT 'action表id',
  `arg_name` varchar(255) DEFAULT NULL COMMENT '方法参数名称',
  `index` int DEFAULT NULL COMMENT '参数索引',
  `type` varchar(255) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_action_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_action_param_ex
-- ----------------------------
DROP TABLE IF EXISTS `af_action_param_ex`;
CREATE TABLE `af_action_param_ex` (
  `id` varchar(255) NOT NULL,
  `action_param_id` varchar(255) NOT NULL COMMENT 'action_param参数表id',
  `param_group_id` varchar(255) DEFAULT NULL COMMENT '参数组id',
  `ex` varchar(255) DEFAULT NULL COMMENT '参数组取值表达式',
  `ex_id` varchar(255) DEFAULT NULL COMMENT '值提取器ID , 对应 ac_extract 表id',
  `format_id` varchar(255) DEFAULT NULL COMMENT '转换器id',
  `value` varchar(255) DEFAULT NULL COMMENT '静态值',
  `flow_id` varchar(255) NOT NULL COMMENT '流程id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_action_param_ex
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_api
-- ----------------------------
DROP TABLE IF EXISTS `af_api`;
CREATE TABLE `af_api` (
  `id` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '接口地址',
  `method` varchar(255) DEFAULT NULL COMMENT '请求方式',
  `desca` varchar(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_api
-- ----------------------------
BEGIN;
INSERT INTO `af_api` (`id`, `url`, `method`, `desca`) VALUES ('ff8080817fd8aa0d017fd8aa12140000', '/pet', 'post', 'Add a new pet to the store');
INSERT INTO `af_api` (`id`, `url`, `method`, `desca`) VALUES ('ff8080817fd8d461017fd8d466bb0000', 'http://localhost:8080/login', 'post', NULL);
INSERT INTO `af_api` (`id`, `url`, `method`, `desca`) VALUES ('ff8080817fd8d461017fd8d4671a0001', 'http://localhost:8080/login', 'post', NULL);
INSERT INTO `af_api` (`id`, `url`, `method`, `desca`) VALUES ('ff8080817fd8d4fa017fd8d4ff870000', 'http://localhost:8080/login', 'post', NULL);
INSERT INTO `af_api` (`id`, `url`, `method`, `desca`) VALUES ('ff8080817fd8d4fa017fd8d4ffe30001', 'http://localhost:8080/user_info', 'get', NULL);
COMMIT;

-- ----------------------------
-- Table structure for af_api_param
-- ----------------------------
DROP TABLE IF EXISTS `af_api_param`;
CREATE TABLE `af_api_param` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `api_id` varchar(255) DEFAULT NULL,
  `in` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  `require` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_api_param
-- ----------------------------
BEGIN;
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (1, 'ff8080817fd8aa0d017fd8aa12140000', 'body', 'body', NULL, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (2, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'id', 1, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (3, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'category', 1, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (4, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'id', 3, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (5, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'name', 3, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (6, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'name', 1, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (7, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'photoUrls', 1, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (8, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'tags', 1, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (9, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'tags', 8, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (10, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'id', 9, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (11, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'name', 9, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (12, 'ff8080817fd8aa0d017fd8aa12140000', NULL, 'status', 1, 0);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (13, 'ff8080817fd8d461017fd8d466bb0000', 'formData', 'username', NULL, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (14, 'ff8080817fd8d461017fd8d466bb0000', 'formData', 'password', NULL, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (15, 'ff8080817fd8d461017fd8d4671a0001', 'formData', 'username', NULL, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (16, 'ff8080817fd8d461017fd8d4671a0001', 'formData', 'password', NULL, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (17, 'ff8080817fd8d4fa017fd8d4ff870000', 'formData', 'username', NULL, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (18, 'ff8080817fd8d4fa017fd8d4ff870000', 'formData', 'password', NULL, 1);
INSERT INTO `af_api_param` (`id`, `api_id`, `in`, `name`, `pid`, `require`) VALUES (19, 'ff8080817fd8d4fa017fd8d4ffe30001', 'header', 'token', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for af_api_param_ex
-- ----------------------------
DROP TABLE IF EXISTS `af_api_param_ex`;
CREATE TABLE `af_api_param_ex` (
  `id` varchar(255) NOT NULL,
  `param_group` varchar(255) DEFAULT NULL COMMENT '参数组',
  `ex` varchar(255) DEFAULT NULL COMMENT '参数组对应提取式',
  `ex_id` varchar(255) DEFAULT NULL COMMENT '提取器id',
  `el` varchar(255) DEFAULT NULL,
  `flow_id` varchar(255) NOT NULL COMMENT '流程id',
  `api_param_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_api_param_ex
-- ----------------------------
BEGIN;
INSERT INTO `af_api_param_ex` (`id`, `param_group`, `ex`, `ex_id`, `el`, `flow_id`, `api_param_id`) VALUES ('ff8080817fd8e2e5017fd8e2ea540001', NULL, NULL, 'ff8080817fd8d20f017fd8d214b30000', NULL, 'ff8080817fd8e2e5017fd8e2ea170000', 19);
INSERT INTO `af_api_param_ex` (`id`, `param_group`, `ex`, `ex_id`, `el`, `flow_id`, `api_param_id`) VALUES ('ff8080817fd8e2e5017fd8e2ea6a0002', 'a', 'username', NULL, NULL, 'ff8080817fd8e2e5017fd8e2ea170000', 17);
INSERT INTO `af_api_param_ex` (`id`, `param_group`, `ex`, `ex_id`, `el`, `flow_id`, `api_param_id`) VALUES ('ff8080817fd8e2e5017fd8e2ea7d0003', 'a', 'password', NULL, NULL, 'ff8080817fd8e2e5017fd8e2ea170000', 18);
COMMIT;

-- ----------------------------
-- Table structure for af_extract
-- ----------------------------
DROP TABLE IF EXISTS `af_extract`;
CREATE TABLE `af_extract` (
  `id` varchar(255) NOT NULL,
  `from_action` varchar(255) DEFAULT NULL COMMENT '来自那个action执行结果',
  `from_api` varchar(255) DEFAULT NULL COMMENT '来自那个api执行结果',
  `el` varchar(255) NOT NULL COMMENT '提取表达式',
  `el_type` varchar(255) DEFAULT 'JSON_PATH' COMMENT '提取方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_extract
-- ----------------------------
BEGIN;
INSERT INTO `af_extract` (`id`, `from_action`, `from_api`, `el`, `el_type`) VALUES ('ff8080817fd8d20f017fd8d214b30000', '', 'ff8080817fd8d4fa017fd8d4ff870000', '$.token', 'JSON_PATH');
COMMIT;

-- ----------------------------
-- Table structure for af_flow
-- ----------------------------
DROP TABLE IF EXISTS `af_flow`;
CREATE TABLE `af_flow` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '流程名称',
  `works` varchar(255) NOT NULL COMMENT '需要执行的顶层工作id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_flow
-- ----------------------------
BEGIN;
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES ('2c9094b87fd8e161017fd8e1669d0000', '获取用户信息', 'ff8080817fd8d4fa017fd8d4ffe30001');
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES ('ff8080817fd8d73f017fd8d744dd0000', '获取用户信息', 'ff8080817fd8d4fa017fd8d4ffe30001');
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES ('ff8080817fd8e26a017fd8e271250000', '获取用户信息', 'ff8080817fd8d4fa017fd8d4ffe30001');
INSERT INTO `af_flow` (`id`, `name`, `works`) VALUES ('ff8080817fd8e2e5017fd8e2ea170000', '获取用户信息', 'ff8080817fd8c469017fd8c46efe0000');
COMMIT;

-- ----------------------------
-- Table structure for af_format
-- ----------------------------
DROP TABLE IF EXISTS `af_format`;
CREATE TABLE `af_format` (
  `id` varchar(255) NOT NULL,
  `class_str` varchar(255) NOT NULL COMMENT '转换器类全路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_format
-- ----------------------------
BEGIN;
INSERT INTO `af_format` (`id`, `class_str`) VALUES ('2c9094b87fd871c6017fd871cb740000', 'com.github.brick.action.flow.method.format.StringToClass');
COMMIT;

-- ----------------------------
-- Table structure for af_param
-- ----------------------------
DROP TABLE IF EXISTS `af_param`;
CREATE TABLE `af_param` (
  `id` varchar(255) NOT NULL,
  `group` varchar(255) NOT NULL COMMENT '参数组',
  `key` varchar(255) NOT NULL COMMENT '参数键',
  `value` varchar(255) DEFAULT NULL COMMENT '参数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_result
-- ----------------------------
DROP TABLE IF EXISTS `af_result`;
CREATE TABLE `af_result` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '结果键',
  `ex_id` varchar(255) NOT NULL COMMENT '结果取值表达式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `id` varchar(255) NOT NULL,
  `ex_id` varchar(255) NOT NULL COMMENT '取值器id',
  `condition` varchar(255) NOT NULL COMMENT '表达式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_watcher
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_watcher_rs
-- ----------------------------
DROP TABLE IF EXISTS `af_watcher_rs`;
CREATE TABLE `af_watcher_rs` (
  `id` varchar(255) NOT NULL,
  `watcher_id` varchar(255) DEFAULT NULL,
  `then_action_ids` varchar(255) DEFAULT NULL,
  `then_api_ids` varchar(255) DEFAULT NULL,
  `catch_action_ids` varchar(255) DEFAULT NULL,
  `catch_api_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_watcher_rs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for af_work
-- ----------------------------
DROP TABLE IF EXISTS `af_work`;
CREATE TABLE `af_work` (
  `id` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL COMMENT '执行类型包含：监控、函数、api',
  `ref_id` varchar(255) NOT NULL COMMENT '对应的id',
  `thens` varchar(255) DEFAULT NULL COMMENT '执行成功需要继续执行的workid',
  `catchs` varchar(255) DEFAULT NULL COMMENT '执行失败需要继续执行的workid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of af_work
-- ----------------------------
BEGIN;
INSERT INTO `af_work` (`id`, `type`, `ref_id`, `thens`, `catchs`) VALUES ('ff8080817fd8c469017fd8c46efe0000', 'api', 'ff8080817fd8d4fa017fd8d4ffe30001', '', '');
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
