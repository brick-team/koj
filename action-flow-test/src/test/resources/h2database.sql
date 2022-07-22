DROP TABLE IF EXISTS `action`;
CREATE TABLE `action` (
                          `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                          `type` tinyint NOT NULL COMMENT '1:rest_api,2:java_method',
                          `url` varchar(255) NULL DEFAULT NULL COMMENT 'type=1时表示HTTP请求地址',
                          `method` varchar(64) NULL DEFAULT NULL COMMENT 'type=1时表示HTTP请求方式(POST\\\\GET\\\\DELETE\\\\PUT) type=2时表示java函数名称',
                          `class_name` varchar(255) NULL DEFAULT NULL COMMENT 'type=2时表示需要执行的类全路径',
                          `file_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名称'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `extract`;
CREATE TABLE `extract` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                           `flow_id` int NOT NULL COMMENT 'flow表主键',
                           `flow_work_id` int NOT NULL COMMENT 'flow_work表主键',
                           `ref_id` int NOT NULL COMMENT 'type=1:关联rest_api_param, type=2:关联ava_method_param表,type=3:关联result表',
                           `type` tinyint NOT NULL COMMENT 'type=1:关联rest_api_param, type=2:关联ava_method_param表, type=3:关联result表',
                           `step` varchar(255) NULL DEFAULT NULL COMMENT '步骤标记',
                           `el_type` varchar(255) NOT NULL COMMENT 'el取值表达式类型',
                           `el` varchar(255) NOT NULL COMMENT '取值表达式'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `flow`;
CREATE TABLE `flow` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                        `name` varchar(255) NULL DEFAULT NULL COMMENT '工作流名称',
                        `qualifier` varchar(255) NULL DEFAULT NULL COMMENT '与spring 进行整合使用',
                        `file_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名称'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `flow_watcher`;
CREATE TABLE `flow_watcher` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                                `flow_work_id` int NOT NULL COMMENT 'flow_work表关联id',
                                `then_or_cat` tinyint NOT NULL COMMENT '1:then节点   2:cat节点',
                                `next_flow_work_id` int NOT NULL COMMENT '指向flow_work表的id',
                                `watcher_id` int NOT NULL COMMENT '指向watcher表的id',
                                `sort` int NOT NULL COMMENT '排序字段'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `flow_work`;
CREATE TABLE `flow_work` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                             `flow_id` int NOT NULL COMMENT 'flow表关联id',
                             `work_id` int NOT NULL COMMENT '关联work表id',
                             `step` varchar(255) NOT NULL COMMENT '步骤值',
                             `type` tinyint NOT NULL COMMENT '是否是顶层',
                             `async` tinyint NOT NULL COMMENT '是否异步(异步：后续所有流程都与之脱离)',
                             `sort` int NOT NULL COMMENT '排序字段（只有顶层具备）'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `java_method_param`;
CREATE TABLE `java_method_param` (
                                     `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                                     `action_id` int NOT NULL COMMENT 'action表关联id',
                                     `pid` int NULL DEFAULT NULL COMMENT '父级id',
                                     `index` int NULL DEFAULT NULL COMMENT '参数索引，从0开始计数',
                                     `type` varchar(255) NOT NULL COMMENT '参数类型，类全路径',
                                     `name` varchar(255) NULL DEFAULT NULL COMMENT '参数名称',
                                     `value` varchar(255) NULL DEFAULT NULL COMMENT '默认值'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `rest_api_param`;
CREATE TABLE `rest_api_param` (
                                  `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                                  `action_id` int NOT NULL COMMENT 'action表关联id',
                                  `pid` int NULL DEFAULT NULL COMMENT '父级id',
                                  `in` varchar(64) NULL DEFAULT NULL COMMENT '参数所在位置',
                                  `name` varchar(255) NULL DEFAULT NULL COMMENT '参数名称',
                                  `require` tinyint NULL DEFAULT NULL COMMENT '是否必填',
                                  `value` varchar(255) NULL DEFAULT NULL COMMENT '默认值'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `rest_api_result`;
CREATE TABLE `rest_api_result` (
                                   `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                                   `action_id` int NOT NULL COMMENT 'action表关联id',
                                   `pid` int NULL DEFAULT NULL COMMENT '父级id',
                                   `field_name` varchar(255) NOT NULL COMMENT '字段名',
                                   `type` varchar(255) NOT NULL COMMENT '值类型',
                                   `value` varchar(255) NULL DEFAULT NULL COMMENT '字段值'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `result`;
CREATE TABLE `result` (
                          `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                          `flow_id` int NOT NULL COMMENT 'flow表主键',
                          `pid` int NULL DEFAULT NULL COMMENT '父级id',
                          `field_name` varchar(255) NOT NULL COMMENT '字段名',
                          `type` varchar(255) NOT NULL COMMENT '值类型',
                          `value` varchar(255) NULL DEFAULT NULL COMMENT '字段值',
                          `file_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名称'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `watcher`;
CREATE TABLE `watcher` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                           `condition` varchar(255) NOT NULL COMMENT '条件表达式',
                           `el_type` varchar(255) NOT NULL COMMENT '取值表达式类型'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '表主键',
                        `action_id` int NOT NULL COMMENT 'action表关联id'
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;
