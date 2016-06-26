-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT '' COMMENT '权限类型：menu、button、url',
  `name` varchar(255) NOT NULL COMMENT '权限名称',
  `permission` varchar(255) NOT NULL COMMENT '权限字符串',
  `icon` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT '0',
  `url` varchar(255) DEFAULT '',
  `description` varchar(255) DEFAULT '' COMMENT '资源描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态值',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父ID',
  `create_by` bigint(20) DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_resource_type` (`type`,`permission`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `update_by` (`update_by`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='资源（权限）表';


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT '',
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `parent_id` bigint(20) DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_by` (`create_by`),
  KEY `update_by` (`update_by`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile_phone` varchar(255) NOT NULL DEFAULT '' COMMENT '手机号码',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT '' COMMENT '加密混淆字符',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别',
  `qq` bigint(20) DEFAULT NULL COMMENT 'QQ号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `avatar` varchar(500) DEFAULT '' COMMENT '头像图片路径',
  `province` varchar(50) DEFAULT '' COMMENT '省',
  `city` varchar(50) DEFAULT '' COMMENT '市',
  `reg_ip` varchar(50) DEFAULT NULL COMMENT '注册时IP地址',
  `score` int(10) DEFAULT '0' COMMENT '积分值',
  `status` int(10) DEFAULT '1' COMMENT '状态：0禁用 1正常',
  `create_by` bigint(20) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_roles` (`user_id`),
  KEY `fk_role_users` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

