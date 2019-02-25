/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : demo
Target Host     : localhost:3306
Target Database : demo
Date: 2019-01-02 22:13:54
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for cmt_info
-- ----------------------------
DROP TABLE IF EXISTS `cmt_info`;
CREATE TABLE `cmt_info` (
  `articleId` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `cmtContent` varchar(255) NOT NULL,
  `cmtTime` varchar(255) NOT NULL,
  `cmtId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cmt_info
-- ----------------------------
INSERT INTO `cmt_info` VALUES ('16', '8th-Cicada', 'haha', '2018年12月20日 14:40:20', '1');
INSERT INTO `cmt_info` VALUES ('16', 'gslBrendae', 'what???', '2018年12月20日 15:40:20', '2');
INSERT INTO `cmt_info` VALUES ('17', 'lmmiang', 'nishishui', '2018年12月21日 08:23:31', '3');
INSERT INTO `cmt_info` VALUES ('13', 'lmmiang', 'hhhh', '2018年12月21日 08:23:39', '4');
INSERT INTO `cmt_info` VALUES ('17', 'lmmiang', 'lllllll', '2018年12月21日 08:23:50', '5');
INSERT INTO `cmt_info` VALUES ('6', 'lmmiang', 'asdasd', '2018年12月21日 08:24:03', '6');
INSERT INTO `cmt_info` VALUES ('6', 'lmmiang', 'nsflandf ', '2018年12月21日 08:25:28', '7');
INSERT INTO `cmt_info` VALUES ('19', 'lmmiang', 'nnnnn', '2018年12月21日 09:35:28', '8');
INSERT INTO `cmt_info` VALUES ('2', 'lmmiang', 'hhhh', '2018年12月21日 17:52:46', '9');
INSERT INTO `cmt_info` VALUES ('21', 'lmmiang', 'no problem???', '2018年12月21日 17:54:44', '10');
INSERT INTO `cmt_info` VALUES ('21', 'lmmiang', '11111', '2018年12月22日 01:43:50', '11');
INSERT INTO `cmt_info` VALUES ('22', 'lmmiang', '555', '2018年12月22日 01:45:28', '12');
INSERT INTO `cmt_info` VALUES ('2', 'lmmiang', '156', '2018年12月22日 01:45:49', '13');
INSERT INTO `cmt_info` VALUES ('36', 'lmmiang', '我评论了', '2018年12月26日 16:56:57', '14');
INSERT INTO `cmt_info` VALUES ('50', 'gslBrendae', '吵死了', '2019年01月02日 22:04:52', '15');
INSERT INTO `cmt_info` VALUES ('48', 'gslBrendae', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2019年01月02日 22:05:26', '16');
INSERT INTO `cmt_info` VALUES ('46', 'lmmiang', '你猜', '2019年01月02日 22:11:31', '17');

-- ----------------------------
-- Table structure for follow_info
-- ----------------------------
DROP TABLE IF EXISTS `follow_info`;
CREATE TABLE `follow_info` (
  `userId` varchar(255) NOT NULL,
  `followId` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`,`followId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow_info
-- ----------------------------
INSERT INTO `follow_info` VALUES ('8th-Cicada', '8th-Cicada');
INSERT INTO `follow_info` VALUES ('8th-Cicada', 'gslBrendae');
INSERT INTO `follow_info` VALUES ('8th-Cicada', 'iKilaw');
INSERT INTO `follow_info` VALUES ('8th-Cicada', 'lmmiang');
INSERT INTO `follow_info` VALUES ('BJT', 'BJT');
INSERT INTO `follow_info` VALUES ('cccoskun', 'cccoskun');
INSERT INTO `follow_info` VALUES ('chunlankun', 'chunlankun');
INSERT INTO `follow_info` VALUES ('Dbt', 'Dbt');
INSERT INTO `follow_info` VALUES ('Fujunjun', 'Fujunjun');
INSERT INTO `follow_info` VALUES ('gslBrendae', '8th-Cicada');
INSERT INTO `follow_info` VALUES ('gslBrendae', 'gslBrendae');
INSERT INTO `follow_info` VALUES ('gslBrendae', 'lmmiang');
INSERT INTO `follow_info` VALUES ('gslBrendae', 'Patronum_Sx');
INSERT INTO `follow_info` VALUES ('gslBrendae', 'xiaopohai_hun');
INSERT INTO `follow_info` VALUES ('Huangtt1', 'Huangtt1');
INSERT INTO `follow_info` VALUES ('iKilaw', 'gslBrendae');
INSERT INTO `follow_info` VALUES ('iKilaw', 'iKilaw');
INSERT INTO `follow_info` VALUES ('Kyrie_x', 'Kyrie_x');
INSERT INTO `follow_info` VALUES ('lmmiang', '8th-Cicada');
INSERT INTO `follow_info` VALUES ('lmmiang', 'chunlankun');
INSERT INTO `follow_info` VALUES ('lmmiang', 'gslBrendae');
INSERT INTO `follow_info` VALUES ('lmmiang', 'iKilaw');
INSERT INTO `follow_info` VALUES ('lmmiang', 'lmmiang');
INSERT INTO `follow_info` VALUES ('lmmiang', 'Patronum_Sx');
INSERT INTO `follow_info` VALUES ('lmmiang', 'xiaopohai_hun');
INSERT INTO `follow_info` VALUES ('Patronum_Sx', 'Patronum_Sx');
INSERT INTO `follow_info` VALUES ('qunqun', 'qunqun');
INSERT INTO `follow_info` VALUES ('qunqun1', 'lmmiang');
INSERT INTO `follow_info` VALUES ('qunqun1', 'qunqun1');
INSERT INTO `follow_info` VALUES ('wuhdjkfa', 'wuhdjkfa');
INSERT INTO `follow_info` VALUES ('Xgz', 'Xgz');
INSERT INTO `follow_info` VALUES ('xiaopohai_hun', 'lmmiang');
INSERT INTO `follow_info` VALUES ('xiaopohai_hun', 'xiaopohai_hun');

-- ----------------------------
-- Table structure for history_info
-- ----------------------------
DROP TABLE IF EXISTS `history_info`;
CREATE TABLE `history_info` (
  `userId` varchar(255) NOT NULL,
  `num` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`,`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history_info
-- ----------------------------
INSERT INTO `history_info` VALUES ('8th-Cicada', '1', '苍井翔太');
INSERT INTO `history_info` VALUES ('8th-Cicada', '9', 'm');
INSERT INTO `history_info` VALUES ('8th-Cicada', '10', 'm');
INSERT INTO `history_info` VALUES ('8th-Cicada', '11', 'm');
INSERT INTO `history_info` VALUES ('8th-Cicada', '12', 'KK');
INSERT INTO `history_info` VALUES ('8th-Cicada', '13', 'k');
INSERT INTO `history_info` VALUES ('cccccccoskun', '2', '阴阳师');
INSERT INTO `history_info` VALUES ('gslBrendae', '3', 'RNG');
INSERT INTO `history_info` VALUES ('Kyrie_x', '5', 'AJ');
INSERT INTO `history_info` VALUES ('lmmiang', '4', '张靓颖');
INSERT INTO `history_info` VALUES ('lmmiang', '21', 'hhh');
INSERT INTO `history_info` VALUES ('lmmiang', '22', '张靓颖');
INSERT INTO `history_info` VALUES ('lmmiang', '23', '易烊千玺');
INSERT INTO `history_info` VALUES ('lmmiang', '24', 'm');
INSERT INTO `history_info` VALUES ('lmmiang', '25', 'm');
INSERT INTO `history_info` VALUES ('lmmiang', '26', 'm');
INSERT INTO `history_info` VALUES ('lmmiang', '27', '张靓颖');
INSERT INTO `history_info` VALUES ('lmmiang', '28', '易烊千玺');
INSERT INTO `history_info` VALUES ('lmmiang', '29', '易烊千玺');
INSERT INTO `history_info` VALUES ('lmmiang', '30', '张靓颖');
INSERT INTO `history_info` VALUES ('lmmiang', '31', '张靓颖');
INSERT INTO `history_info` VALUES ('lmmiang', '32', '易烊千玺');
INSERT INTO `history_info` VALUES ('lmmiang', '33', 'm');
INSERT INTO `history_info` VALUES ('lmmiang', '34', 'm');

-- ----------------------------
-- Table structure for like_info
-- ----------------------------
DROP TABLE IF EXISTS `like_info`;
CREATE TABLE `like_info` (
  `userId` varchar(255) NOT NULL,
  `articleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of like_info
-- ----------------------------
INSERT INTO `like_info` VALUES ('lmmiang', '20');
INSERT INTO `like_info` VALUES ('lmmiang', '2');
INSERT INTO `like_info` VALUES ('lmmiang', '22');
INSERT INTO `like_info` VALUES ('lmmiang', '23');
INSERT INTO `like_info` VALUES ('lmmiang', '24');
INSERT INTO `like_info` VALUES ('lmmiang', '33');
INSERT INTO `like_info` VALUES ('lmmiang', '36');
INSERT INTO `like_info` VALUES ('gslBrendae', '50');
INSERT INTO `like_info` VALUES ('gslBrendae', '46');
INSERT INTO `like_info` VALUES ('gslBrendae', '49');
INSERT INTO `like_info` VALUES ('gslBrendae', '48');
INSERT INTO `like_info` VALUES ('lmmiang', '54');
INSERT INTO `like_info` VALUES ('lmmiang', '53');

-- ----------------------------
-- Table structure for major_info
-- ----------------------------
DROP TABLE IF EXISTS `major_info`;
CREATE TABLE `major_info` (
  `articleId` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `time` varchar(255) NOT NULL,
  `cmt` varchar(255) NOT NULL,
  `repost` varchar(255) NOT NULL,
  `kudo` varchar(255) NOT NULL,
  `origin` int(11) NOT NULL,
  `pic` varchar(255) NOT NULL,
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major_info
-- ----------------------------
INSERT INTO `major_info` VALUES ('1', 'gslBrendae', 'aaaaaaaaa', '2018年12月11日 22:19:05', '0', '0', '5', '0', 'noPic');
INSERT INTO `major_info` VALUES ('2', '8th-Cicada', 'ぉはょへ', '2018年12月18日 08:20:24', '7', '1', '11', '0', 'noPic');
INSERT INTO `major_info` VALUES ('3', 'cccoskun', '非洲人没有眼泪', '2018年12月18日 10:38:15', '1', '1', '2', '0', 'noPic');
INSERT INTO `major_info` VALUES ('4', 'chunlankun', 'RNG牛逼！', '2018年12月18日 10:51:41', '10', '6', '25', '0', '/sdcard/twitterPic/1545457893596.jpg');
INSERT INTO `major_info` VALUES ('5', 'iKilaw', '大腐3诚不欺我，盖里奇不值得。', '2018年12月18日 12:19:03', '20', '12', '50', '0', 'noPic');
INSERT INTO `major_info` VALUES ('6', 'lmmiang', '诶嘿', '2018年12月18日 13:05:39', '5', '0', '7', '12', 'noPic');
INSERT INTO `major_info` VALUES ('7', 'Kyrie_x', 'A操可太冷了', '2018年12月18日 14:28:07', '8', '0', '3', '0', 'noPic');
INSERT INTO `major_info` VALUES ('8', 'Patronum_Sx', '今天海王二刷！走起！', '2018年12月18日 15:19:21', '87', '120', '125', '0', 'noPic');
INSERT INTO `major_info` VALUES ('9', 'wuhdjkfa', 'I\'m coming!', '2018年12月18日 16:59:48', '0', '0', '2', '0', 'noPic');
INSERT INTO `major_info` VALUES ('10', 'xiaopohai_hun', '转发这条锦鲤，明天你就会发生一件幸运的事！', '2018年12月18日 17:02:48', '52', '180', '125', '0', 'noPic');
INSERT INTO `major_info` VALUES ('11', 'Patronum_Sx', '拜托了', '2018年12月18日 19::31:27', '0', '12', '4', '10', 'noPic');
INSERT INTO `major_info` VALUES ('13', 'lmmiang', '嘿嘿嘿嘿嘿嘿', '2018年12月19日 22:00:00', '1', '0', '5', '6', 'noPic');
INSERT INTO `major_info` VALUES ('20', 'lmmiang', '阿ju', '2018年12月20日 13:56:07', '0', '0', '1', '0', '/sdcard/twitterPic/1545458161366.jpg');
INSERT INTO `major_info` VALUES ('21', 'lmmiang', '诶嘿嘿嘿', '2018年12月20日 14:22:10', '0', '0', '0', '0', '/sdcard/twitterPic/1545459723366.jpg');
INSERT INTO `major_info` VALUES ('24', 'gslBrendae', 'HAPPY BIRTHDAY!', '2018年12月21日 00:00:00', '0', '0', '1', '0', '/sdcard/twitterPic/1545466074809.jpg');
INSERT INTO `major_info` VALUES ('27', 'Kyrie_x', '『実は素晴らしい家族ということ\nを知ってほしい』作演出の矢島 \n弘一さん。深みのある素敵な本\nを書く方で繊細なお芝居を学ば\nさせて頂きました。またご一緒\n出来るように頑張る。\nこの写真はなんかあれだな…成人\n式に地元に戻ってきました。学生\n時代にとてもお世話になった恩師\nと。的な雰囲気がある。', '2018年12月21日 13:24:22', '59', '142', '234', '0', '/sdcard/twitterPic/1545492193892.jpg');
INSERT INTO `major_info` VALUES ('28', 'lmmiang', '#浙农林的雪#', '2018年12月21日 16:04:49', '0', '0', '1', '0', '/sdcard/twitterPic/1545465873861.jpg');
INSERT INTO `major_info` VALUES ('29', 'Patronum_Sx', 'LEO 1st MINI ALBUM\n<CANVAS>\nOnline Cover Image\n#LEO##CANVAS# ', '2018年12月21日 23:22:53', '0', '0', '0', '0', '/sdcard/twitterPic/1545492164242.jpg');
INSERT INTO `major_info` VALUES ('30', 'gslBrendae', '落在人生中的每一场雪', '2018年12月22日 05:22:28', '0', '1', '0', '0', '/sdcard/twitterPic/1545492133312.jpg');
INSERT INTO `major_info` VALUES ('31', '8th-Cicada', '龟甲糖加话梅！\r\n今天在彩排的时候收到了非常特别的东西\r\n虽然是龟甲糖，\r\n却不仅仅是普通的龟甲糖！！！\r\n中间还放了话梅干\r\n我还是第一次吃到呢\r\n听说对运动后疲惫的身体很有好处！\r\n我就赶紧收下了！\r\n糖糖也吃了\r\n当然晚饭和肉类也会多吃一点，\r\n给自己补充能量继续加油!\r\n', '2018年12月22日 16:09:54', '120', '268', '385', '0', '/sdcard/twitterPic/1545466191399.jpg');
INSERT INTO `major_info` VALUES ('32', 'chunlankun', 'IG牛逼！！！', '2018年12月22日 22:29:16', '101', '127', '190', '0', '/sdcard/twitterPic/1545492554813.jpg');
INSERT INTO `major_info` VALUES ('33', 'lmmiang', 'Ig牛逼呀', '2018年12月22日 23:20:50', '0', '0', '0', '32', '/sdcard/twitterPic/1545492554813.jpg');
INSERT INTO `major_info` VALUES ('34', 'wuhdjkfa', '#杨幂刘恺威离婚# 22日，嘉行传媒\n发表声明宣布杨幂与刘恺威已签署\n协议，和平分手。此前两人曾多次\n传出离婚的消息，近日更有网友爆\n料两人将于20日官宣离婚，没想到\n“威幂夫妇”也最终抵不过时间的考\n验。曾经是娱乐圈内令人艳羡的一\n对，为何走到陌路呢？', '2018年12月22日 23:35:17', '894', '1132', '1257', '0', '/sdcard/twitterPic/1545492915488.jpg');
INSERT INTO `major_info` VALUES ('35', 'xiaopohai_hun', 'This ROVIX. Agent#KENparticipated\nSBS Mon Tue <#My_Strange_Hero> \nOST has been released. \nShow me your ST★RLIGHT power by streamingdownloading! [Start the RT operation! Over and out.]', '2018年12月22日 23:55:16', '93', '120', '128', '0', '/sdcard/twitterPic/1545492338193.jpg');
INSERT INTO `major_info` VALUES ('36', 'Patronum_Sx', 'LEO <You are there, but not there>\nONLINE COVER IMAGE\nTITLE SONG _ You are there,\n but not there (Feat. HANHAE)', '2018年12月23日 08:21:37', '1', '3', '1', '0', '/sdcard/twitterPic/1545492023908.jpg');
INSERT INTO `major_info` VALUES ('37', 'lmmiang', '开心', '2018年12月26日 17:08:29', '0', '0', '0', '0', '/sdcard/twitterPic/1545815302050.jpg');
INSERT INTO `major_info` VALUES ('38', 'lmmiang', 'Repost', '2018年12月29日 14:35:12', '0', '0', '0', '36', '/sdcard/twitterPic/1545492023908.jpg');
INSERT INTO `major_info` VALUES ('39', '8th-Cicada', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊bet on u好好听啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2019年01月02日 21:56:37', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('40', '8th-Cicada', '这个曲风真的好韩风啊啊啊啊啊啊啊啊啊啊啊啊啊啊好像vixx的曲风苍井翔太果然是迷弟', '2019年01月02日 21:57:39', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('41', '8th-Cicada', '好想买香的香水啊好好闻', '2019年01月02日 21:58:16', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('42', '8th-Cicada', '李红豆果然是直男吧哈哈哈哈哈哈喜欢的香水竟然说燕麦沐浴乳', '2019年01月02日 21:59:08', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('43', '8th-Cicada', '果然还是直男比较吸引苍井翔太哈哈哈哈哈哈', '2019年01月02日 21:59:39', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('44', '8th-Cicada', 'Lr真的太合适了吧vixx果然啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2019年01月02日 22:00:11', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('45', '8th-Cicada', '有点想买小米的蓝牙耳机', '2019年01月02日 22:00:32', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('46', '8th-Cicada', '网易云的年度歌单什么时候出来 今年不会没有吧 我好不容易把苍井翔太刷到榜首的啊', '2019年01月02日 22:01:25', '1', '1', '1', '0', 'noPic');
INSERT INTO `major_info` VALUES ('47', '8th-Cicada', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊leo太像斯文败类了', '2019年01月02日 22:02:01', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('48', '8th-Cicada', '哈哈哈哈哈哈把人拖走也太可爱了', '2019年01月02日 22:02:29', '1', '0', '1', '0', 'noPic');
INSERT INTO `major_info` VALUES ('49', '8th-Cicada', 'Ravi的rap好牛逼', '2019年01月02日 22:02:52', '0', '0', '1', '0', 'noPic');
INSERT INTO `major_info` VALUES ('50', '8th-Cicada', '调香师也太斯文败类了吧', '2019年01月02日 22:03:09', '1', '0', '1', '0', 'noPic');
INSERT INTO `major_info` VALUES ('51', 'gslBrendae', '某个人好吵', '2019年01月02日 22:05:39', '0', '0', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('52', 'gslBrendae', '我的补款要死了', '2019年01月02日 22:05:54', '0', '1', '0', '0', 'noPic');
INSERT INTO `major_info` VALUES ('53', 'gslBrendae', '明天我生日', '2019年01月02日 22:06:02', '0', '1', '1', '0', 'noPic');
INSERT INTO `major_info` VALUES ('54', 'gslBrendae', 'Cherry赶紧卖掉吧', '2019年01月02日 22:07:06', '0', '0', '1', '0', '/sdcard/twitterPic/1546438025089.jpg');
INSERT INTO `major_info` VALUES ('55', 'lmmiang', '生日快乐 哈皮', '2019年01月02日 22:07:47', '0', '0', '0', '53', 'null');
INSERT INTO `major_info` VALUES ('56', 'lmmiang', '我易大佬真好 太好了 真好 超级好 \n', '2019年01月02日 22:09:53', '0', '0', '0', '0', '/sdcard/twitterPic/1546438191151.jpg');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `userId` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pass` varchar(255) NOT NULL,
  `headimage` varchar(255) DEFAULT NULL,
  `fan` varchar(255) NOT NULL,
  `follow` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('8th-Cicada', '黄婷婷', 'shiyideyu', '/sdcard/smallIcon/1545460697013.jpg', '2', '2');
INSERT INTO `user_info` VALUES ('BJT', '', 'Baijingting', 'nothing', '0', '0');
INSERT INTO `user_info` VALUES ('cccoskun', 'lhy', 'ccc_kunn', '/sdcard/smallIcon/1545460811119.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('chunlankun', 'ydk', 'chun_111', '/sdcard/smallIcon/1545460843093.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('Dbt', '', 'Eihhhhhh', 'nothing', '0', '0');
INSERT INTO `user_info` VALUES ('Fujunjun', '', 'ffffffff', 'nothing', '0', '0');
INSERT INTO `user_info` VALUES ('gslBrendae', 'ly', '02200058', '/sdcard/smallIcon/1545460739494.jpg', '2', '2');
INSERT INTO `user_info` VALUES ('Huangtt1', '', 'Htt10191', 'nothing', '0', '0');
INSERT INTO `user_info` VALUES ('iKilaw', 'wsy', 'wusiffffff', '/sdcard/smallIcon/1545460893323.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('Kyrie_x', 'xjj', 'xjjljjjss', '/sdcard/smallIcon/1545460929401.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('lmmiang', 'Qunqunqun', 'fjf971213', '/sdcard/smallIcon/1546065261330.jpg', '1', '0');
INSERT INTO `user_info` VALUES ('Patronum_Sx', 'hjm', 'hejiamu1', '/sdcard/smallIcon/1545460970024.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('qunqun', '囷囷', 'qunqun_2', '/sdcard/smallIcon/1545460993797.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('qunqun1', '囷囷囷', 'qunqun_3', '/sdcard/smallIcon/1545461022548.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('wuhdjkfa', 'imking', 'wuhdjkls', '/sdcard/smallIcon/1545461064150.jpg', '0', '0');
INSERT INTO `user_info` VALUES ('Xgz', '', 'xiaogongzhu', 'nothing', '0', '0');
INSERT INTO `user_info` VALUES ('xiaopohai_hun', 'sjw', 'shijiawenhhh', '/sdcard/smallIcon/1545461116573.jpg', '0', '0');
