/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : friday

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 09/06/2019 20:59:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '父权限id',
  `ptype` tinyint(1) NOT NULL COMMENT '1-菜单，2-按钮，3-接口',
  `pcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限代码',
  `pname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限名',
  `pdesc` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限描述',
  `url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '0', 1, NULL, '系统管理', NULL, NULL, 0, 'sys', '2019-06-06 22:18:45', NULL, '2019-06-06 22:18:56');
INSERT INTO `sys_permission` VALUES ('10', '2', 3, 'sys:user:delete', '编辑用户', '接口权限', NULL, 0, 'sys', '2019-06-06 22:23:11', NULL, '2019-06-06 22:26:02');
INSERT INTO `sys_permission` VALUES ('2', '1', 1, NULL, '用户管理', NULL, NULL, 0, 'sys', '2019-06-06 22:19:24', NULL, '2019-06-06 22:19:26');
INSERT INTO `sys_permission` VALUES ('3', '1', 1, NULL, '角色管理', NULL, NULL, 0, 'sys', '2019-06-06 22:20:00', NULL, '2019-06-06 22:20:01');
INSERT INTO `sys_permission` VALUES ('4', '2', 3, 'sys:user:list', '用户列表', NULL, NULL, 0, 'sys', '2019-06-06 22:20:46', NULL, '2019-06-06 22:20:48');
INSERT INTO `sys_permission` VALUES ('5', '2', 2, 'sys:user:add', '添加用户', '按钮权限', NULL, 0, 'sys', '2019-06-06 22:23:11', NULL, '2019-06-06 22:24:42');
INSERT INTO `sys_permission` VALUES ('6', '2', 3, 'sys:user:add', '添加用户', '接口权限', NULL, 0, 'sys', '2019-06-06 22:23:11', NULL, '2019-06-06 22:24:43');
INSERT INTO `sys_permission` VALUES ('7', '2', 2, 'sys:user:update', '编辑用户', '按钮权限', NULL, 0, 'sys', '2019-06-06 22:23:11', NULL, '2019-06-06 22:25:09');
INSERT INTO `sys_permission` VALUES ('8', '2', 3, 'sys:user:add', '编辑用户', '接口权限', NULL, 0, 'sys', '2019-06-06 22:23:11', NULL, '2019-06-06 22:25:22');
INSERT INTO `sys_permission` VALUES ('9', '2', 2, 'sys:user:delete', '编辑用户', '按钮权限', NULL, 0, 'sys', '2019-06-06 22:23:11', NULL, '2019-06-06 22:25:45');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_chs_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'super', '超管', NULL, 0, 'sys', '2019-06-06 22:16:24', NULL, '2019-06-06 22:16:25');

-- ----------------------------
-- Table structure for sys_role_permisson
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permisson`;
CREATE TABLE `sys_role_permisson`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `permission_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permisson
-- ----------------------------
INSERT INTO `sys_role_permisson` VALUES ('7b48bc42-8867-11e9-9930-00ac66de77d8', '1', '1', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c7b8c-8867-11e9-9930-00ac66de77d8', '1', '10', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c7d50-8867-11e9-9930-00ac66de77d8', '1', '2', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c7ddb-8867-11e9-9930-00ac66de77d8', '1', '3', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c7e4e-8867-11e9-9930-00ac66de77d8', '1', '4', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c7ebb-8867-11e9-9930-00ac66de77d8', '1', '5', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c7f58-8867-11e9-9930-00ac66de77d8', '1', '6', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c7fbe-8867-11e9-9930-00ac66de77d8', '1', '7', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c8049-8867-11e9-9930-00ac66de77d8', '1', '8', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');
INSERT INTO `sys_role_permisson` VALUES ('7b4c80b0-8867-11e9-9930-00ac66de77d8', '1', '9', 0, 'sys', '2019-06-06 22:29:24', NULL, '2019-06-06 22:29:24');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `real_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_type` int(1) NULL DEFAULT NULL COMMENT '用户类型（保留字段）',
  `is_ban` tinyint(1) NULL DEFAULT NULL COMMENT '封号状态(0-有效，1-封号)',
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'super', '123456', NULL, NULL, 0, 0, 'sys', '2019-06-06 22:15:15', 'sys', '2019-06-06 22:16:15');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', 0, 'sys', '2019-06-06 22:17:06', NULL, '2019-06-06 22:17:08');

SET FOREIGN_KEY_CHECKS = 1;
