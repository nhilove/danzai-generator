# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (userAccount)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 代码生成器表
create table if not exists generator
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(128)                       null comment '名称',
    description text                               null comment '描述',
    basePackage varchar(128)                       null comment '基础包',
    version     varchar(128)                       null comment '版本',
    author      varchar(128)                       null comment '作者',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    picture     varchar(256)                       null comment '图片',
    fileConfig  text                               null comment '文件配置（json数组）',
    modelConfig text                               null comment '模型配置（json数组）',
    distPath    text                               null comment '代码生成器产物路径',
    status      int      default 0                 not null comment '状态',
    userId      bigint                             not null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '代码生成器' collate = utf8mb4_unicode_ci;

insert into user(id,userAccount, userPassword, userName, userAvatar, userProfile, userRole) values(1,'yupi', '123456', '阿成', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '我有一头小莫啊', 'admin');
insert into user(id,userAccount, userPassword, userName, userAvatar, userProfile, userRole) values(2,'liyupi', '123456', '皮城', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '从来也不骑', 'user');

insert into generator(id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId) values (1, 'ACM示例模板','ACM代码生成器','com.yupi','1.0','程序员小成','["java"]','https://pic.yupi.icu/1/_r0_c1851-bf115939332e.jpg','{}','{}',null,0,1);
insert into generator(id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId) values (2, 'spirngbootinit','springboot初始化项目代码生成器','com.yupi','1.0','程序员小王','["java"]','https://pic.yupi.icu/1/_r0_c0726-7e30f8db802a.jpg','{}','{}',null,0,1);
insert into generator(id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId) values (3, 'yupi商超','yupi商超代码生成器','com.yupi','1.0','程序员小李','["java","前端"]','https://pic.yupi.icu/1/_r1_c0cf7-f8e4bd865b4b.jpg','{}','{}',null,0,1);
insert into generator(id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId) values (4, '大众应答','大众应答代码生成器','com.yupi','1.0','程序员大壮','["java","前端"]','https://pic.yupi.icu/1/_r1_c1c15-79cdecf24aed.jpg','{}','{}',null,0,1);
insert into generator(id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId) values (5, '饿了吗','饿了吗代码生成器','com.yupi','1.0','程序员铁牛','["java","前端"]','https://pic.yupi.icu/1/_r1_c0709-8e80689ac1da.jpg','{}','{}',null,0,1);