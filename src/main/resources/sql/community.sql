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