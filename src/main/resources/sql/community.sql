CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT primary key,
  `ACCOUNT_ID` varchar(100),
  `NAME` varchar(50) ,
  `TOKEN` char(36),
  `GMT_CREATE` bigint(20),
  `GMT_MODIFIED` bigint(20) ,
  `bio` varchar(256) ,
  `avatar_url` varchar(100)
)


create table if not EXISTS publish(
id int PRIMARY key not null auto_increment,
title VARCHAR(25),
description TEXT,
gmt_create BIGINT,
gmt_modified BIGINT,
creator int,
comment_count int DEFAULT 0,
view_count int default 0,
like_count int default 0,
tag VARCHAR(256)
)

create table if not EXISTS comment(
id int(11) PRIMARY key not null auto_increment,
parent_id int(11) not null,
type int(11),
commentator int(11),
gmt_create BIGINT,
gmt_modified BIGINT,
like_count int(11),
context LONGTEXT,
comment_count int(11) DEFAULT 0
)

create table if not EXISTS notification(
id int(11) PRIMARY key not null auto_increment,
notifier int(11),
receiver int(11),
outer_id int(11),#区分是问题的id还是回复的id
type int(11),
gmt_create bigint(20),
gmt_modified bigint(20),
status int(11) DEFAULT 0
)
