/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

CREATE TABLE `action`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int NULL COMMENT '1:rest_api,2:java_method',
  `url` varchar(255) NULL COMMENT 'type=1时表示HTTP请求地址',
  `method` varchar(255) NULL COMMENT 'type=1时表示HTTP请求方式(POST\\GET\\DELETE\\PUT)\r\ntype=2时表示java函数名称',
  `class_name` varchar(255) NULL COMMENT 'type=2时表示需要执行的类全路径',
  PRIMARY KEY (`id`)
);

CREATE TABLE `extract`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `el_type` varchar(255) NULL COMMENT '取值类型',
  `el` varchar(255) NULL COMMENT '取值表达式',
  PRIMARY KEY (`id`)
);

CREATE TABLE `field`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NULL,
  `field_name` varchar(255) NULL COMMENT '字段名称',
  `type` varchar(255) NULL COMMENT '字段类型',
  `ex_id` int NULL COMMENT '取值器id',
  `step` varchar(255) NULL COMMENT '执行步骤号',
  PRIMARY KEY (`id`)
);

CREATE TABLE `flow`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL COMMENT '工作流名称',
  PRIMARY KEY (`id`)
);

CREATE TABLE `flow_watcher`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `watcher_id` int NULL,
  `type` varchar(255) NULL COMMENT '是否通过watcher表达式，通过填写then，不通过填写cat',
  `work_id` int NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `flow_work_watcher`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `flow_works_id` int NULL,
  `watcher_id` int NULL,
  `type` varchar(255) NULL COMMENT '是否通过watcher表达式，通过填写then，不通过填写cat',
  `work_id` int NULL,
  `pid` int NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `flow_works`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `work_id` int NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `flow_works_param`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NULL COMMENT '来自那个表flow_works,flow_work_watcher',
  `ex_id` int NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `java_method_param`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `action_id` int NULL,
  `index` varchar(255) NULL COMMENT '参数索引，从0开始计数',
  `type` varchar(255) NULL COMMENT '参数类型，类全路径',
  `name` varchar(255) NULL COMMENT '参数名称',
  `value` varchar(255) NULL COMMENT '默认值',
  PRIMARY KEY (`id`)
);

CREATE TABLE `rest_api_param`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `action_id` int NULL,
  `in` varchar(255) NULL COMMENT '参数所在位置',
  `name` varchar(255) NULL COMMENT '参数名称',
  `require` tinyint NULL COMMENT '是否必填',
  `value` varchar(255) NULL COMMENT '默认值',
  PRIMARY KEY (`id`)
);

CREATE TABLE `result`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NULL,
  `field_name` varchar(255) NULL COMMENT '字段名称',
  `type` varchar(255) NULL COMMENT '字段类型',
  `ex_id` int NULL COMMENT '取值器id',
  `step` varchar(255) NULL COMMENT '执行步骤号',
  PRIMARY KEY (`id`)
);

CREATE TABLE `watcher`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `condition` varchar(255) NULL COMMENT '条件表达式',
  `el_type` varchar(255) NULL COMMENT '条件表达式中的取值表达式类型',
  PRIMARY KEY (`id`)
);

CREATE TABLE `work`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `step` varchar(255) NULL COMMENT '步骤号',
  `action_id` int NULL COMMENT 'action表id',
  PRIMARY KEY (`id`)
);

