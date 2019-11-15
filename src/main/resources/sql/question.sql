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