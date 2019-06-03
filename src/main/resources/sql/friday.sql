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

 Date: 03/06/2019 22:08:49
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
  `pcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限代码',
  `pname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限名',
  `pdesc` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限描述',
  `url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

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
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

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
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `real_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_type` tinyint(1) NULL DEFAULT NULL COMMENT '用户类型（保留字段）',
  `is_ban` tinyint(1) NULL DEFAULT NULL COMMENT '封号状态(0-有效，1-封号)',
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

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
  `modify_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
