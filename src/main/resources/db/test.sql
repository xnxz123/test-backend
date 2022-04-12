/*
Navicat MySQL Data Transfer

Source Server         : localhost_root
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : backend_test

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2022-04-12 13:54:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT 'user name',
  `pwd` varchar(64) DEFAULT NULL COMMENT 'user password',
  `dob` datetime DEFAULT NULL COMMENT 'user date of birth',
  `address` varchar(255) DEFAULT NULL COMMENT 'user address',
  `description` varchar(255) DEFAULT NULL COMMENT 'user description',
  `longitude` decimal(9,6) DEFAULT NULL COMMENT 'user longitude',
  `latitude` decimal(8,6) DEFAULT NULL COMMENT 'user latitude',
  `created_at` datetime DEFAULT NULL COMMENT 'user created date',
  `update_at` datetime NOT NULL COMMENT 'update_at',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT 'version',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'status',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='user';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '202CB962AC59075B964B07152D234B70', '1997-05-13 00:00:00', 'xxx', 'xxxx', '116.427469', '39.902514', null, '2022-04-07 14:24:08', '1', '1');

