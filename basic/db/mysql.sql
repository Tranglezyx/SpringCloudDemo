create table sys_user
(
    id          bigint auto_increment,
    account     varchar(50)  null comment '账号',
    mobile      varchar(20)  null comment '电话',
    password    varchar(200) null comment '密码',
    nick_name   varchar(50)  null comment '别名',
    create_time bigint       null comment '创建时间',
    update_time bigint       null comment '更新时间',
    constraint sys_user_pk
        primary key (id)
);

