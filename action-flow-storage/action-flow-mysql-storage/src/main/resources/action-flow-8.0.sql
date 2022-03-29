CREATE TABLE `af_action`  (
  `id` varchar(255) NOT NULL,
  `clazz_str` varchar(255) NOT NULL,
  `method_str` varchar(255) NOT NULL,
  `async` bit NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_action_param`  (
  `id` varchar(255) NOT NULL,
  `action_id` varchar(255) NOT NULL COMMENT 'action表id',
  `arg_name` varchar(255) NULL COMMENT '方法参数名称',
  `index` integer(10) NULL COMMENT '参数索引',
  `type` varchar(255) NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_action_param_ex`  (
  `id` varchar(255) NOT NULL,
  `action_param_id` varchar(255) NOT NULL COMMENT 'action_param参数表id',
  `param_group_id` varchar(255) NULL COMMENT '参数组id',
  `ex` varchar(255) NULL COMMENT '参数组取值表达式',
  `ex_id` varchar(255) NULL COMMENT '值提取器ID , 对应 ac_extract 表id',
  `format_id` varchar(255) NULL COMMENT '转换器id',
  `value` varchar(255) NULL COMMENT '静态值',
  `flow_id` varchar(255) NOT NULL COMMENT '流程id',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_api`  (
  `id` varchar(255) NOT NULL,
  `url` varchar(255) NULL COMMENT '接口地址',
  `method` varchar(255) NULL COMMENT '请求方式',
  `desca` varchar(255) NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_api_param`  (
  `id` int NOT NULL,
  `api_id` varchar(255) NOT NULL COMMENT '接口id',
  `pid` int NOT NULL COMMENT '父参数id',
  `in` varchar(255) NOT NULL COMMENT '参数位置（表单、请求头、请求地址、请求体）',
  `name` varchar(255) NOT NULL COMMENT '参数名称',
  `require` varchar(255) NOT NULL COMMENT '是否必填',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_api_param_ex`  (
  `id` varchar(255) NOT NULL,
  `param_group` varchar(255) NULL COMMENT '参数组',
  `ex` varchar(255) NULL COMMENT '参数组对应提取式',
  `ex_id` varchar(255) NULL COMMENT '提取器id',
  `el` varchar(255) NULL,
  `flow_id` varchar(255) NOT NULL COMMENT '流程id',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_extract`  (
  `id` varchar(255) NOT NULL,
  `from_action` varchar(255) NULL COMMENT '来自那个action执行结果',
  `from_api` varchar(255) NULL COMMENT '来自那个api执行结果',
  `el` varchar(255) NOT NULL COMMENT '提取表达式',
  `el_type` varchar(255) NULL DEFAULT 'JSON_PATH' COMMENT '提取方式',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_flow`  (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NULL COMMENT '流程名称',
  `works` varchar(255) NOT NULL COMMENT '需要执行的顶层工作id',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_format`  (
  `id` varchar(255) NOT NULL,
  `class_str` varchar(255) NOT NULL COMMENT '转换器类全路径',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_param`  (
  `id` varchar(255) NOT NULL,
  `group` varchar(255) NOT NULL COMMENT '参数组',
  `key` varchar(255) NOT NULL COMMENT '参数键',
  `value` varchar(255) NULL COMMENT '参数值',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_result`  (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '结果键',
  `ex_id` varchar(255) NOT NULL COMMENT '结果取值表达式',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_watcher`  (
  `id` varchar(255) NOT NULL,
  `ex_id` varchar(255) NOT NULL COMMENT '取值器id',
  `condition` varchar(255) NOT NULL COMMENT '表达式',
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_watcher_rs`  (
  `id` varchar(255) NOT NULL,
  `watcher_id` varchar(255) NULL,
  `then_action_ids` varchar(255) NULL,
  `then_api_ids` varchar(255) NULL,
  `catch_action_ids` varchar(255) NULL,
  `catch_api_ids` varchar(255) NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `af_work`  (
  `id` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL COMMENT '执行类型包含：监控、函数、api',
  `ref_id` varchar(255) NOT NULL COMMENT '对应的id',
  `thens` varchar(255) NULL COMMENT '执行成功需要继续执行的workid',
  `catchs` varchar(255) NULL COMMENT '执行失败需要继续执行的workid',
  PRIMARY KEY (`id`)
);

CREATE TABLE `table_1`  ();

CREATE TABLE `watcher`  ();

