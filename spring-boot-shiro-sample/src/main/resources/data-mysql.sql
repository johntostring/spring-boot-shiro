-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '18966668888', 'super', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '', null, null, null, null, null, '1', null, '2015-09-28 17:47:18', null, '2015-09-30 17:36:16');
INSERT INTO `sys_user` VALUES ('2', '13988886666', 'admin', '系统管理员A', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, '1234567', 'super@millinch.com', null, null, null, null, null, '1', null, '2015-09-29 17:47:22', null, '2015-09-30 17:32:07');

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'superManager', '拥有所有权限', '1', null, '0', '2015-09-01 14:36:16', null, '2016-01-03 22:29:58');
INSERT INTO `sys_role` VALUES ('2', '系统管理员', 'systemManager', '拥有部分权限', '1', null, '0', '2015-08-30 18:03:47', null, '2015-08-30 18:03:47');
INSERT INTO `sys_role` VALUES ('3', '角色1', 'role1', 'nothing 34', '1', null, null, '2015-10-05 18:20:35', null, '2015-10-05 18:35:57');

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '菜单', '系统管理', 'system:*', 'fa fa-dashboard', '1', '', '', '1', '0', '0', '2015-07-01 19:33:21', null, '2015-10-09 10:34:05');
INSERT INTO `sys_resource` VALUES ('2', '菜单', '角色管理', 'system:role:*', 'fa fa-male', '12', '/role/config', '', '1', '1', '0', '2015-07-01 19:38:38', null, '2015-07-01 19:38:38');
INSERT INTO `sys_resource` VALUES ('3', '菜单', '密码修改', 'system:password', null, '14', '/user/password/edition', '', '1', '1', '0', '2015-07-01 19:38:51', null, '2015-07-01 19:39:51');
INSERT INTO `sys_resource` VALUES ('4', '菜单', '操作日志', 'system:log:*', null, '15', '/handle/operation/log', '', '1', '1', '0', '2015-07-01 19:40:37', null, '2015-07-01 19:40:37');
INSERT INTO `sys_resource` VALUES ('5', 'URL', '新增角色', 'system:role:create', null, '13', '/role/addition', '', '1', '1', '0', '2015-07-01 19:41:21', null, '2015-10-08 16:45:43');
INSERT INTO `sys_resource` VALUES ('6', '菜单', '用户管理', 'system:admin:*', 'fa fa-users', '11', '/user/config', '', '1', '1', '0', '2015-07-01 19:34:38', null, '2015-07-01 19:34:38');
INSERT INTO `sys_resource` VALUES ('7', 'URL', '新增用户', 'system:admin:create', '', null, '/user/addition', 'bbb', '1', '0', '0', '2015-08-30 18:29:56', null, '2015-10-09 11:33:03');

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1', '1');
INSERT INTO `sys_role_resource` VALUES ('2', '1', '2');
INSERT INTO `sys_role_resource` VALUES ('3', '1', '3');
INSERT INTO `sys_role_resource` VALUES ('4', '1', '4');
INSERT INTO `sys_role_resource` VALUES ('5', '1', '5');
INSERT INTO `sys_role_resource` VALUES ('6', '1', '6');
INSERT INTO `sys_role_resource` VALUES ('7', '1', '7');
INSERT INTO `sys_role_resource` VALUES ('8', '2', '2');

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2');