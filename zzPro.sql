CREATE TABLE `Zz_Account`
(
    `zz_account_id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '账户主键',
    `zz_username`            varchar(20)      DEFAULT NULL COMMENT '用户名',
    `zz_password`            varchar(64)      DEFAULT NULL COMMENT '密码',
    `zz_salt`                varchar(32)      DEFAULT NULL COMMENT '加密盐',
    `zz_real_name`           varchar(50)      DEFAULT NULL COMMENT '真实姓名',
    `zz_sex`                 char(1)          DEFAULT NULL COMMENT '性别（M: 男, F: 女, N: 不明）',
    `zz_email`               varchar(100)     DEFAULT NULL COMMENT '邮箱',
    `zz_phone`               varchar(20)      DEFAULT NULL COMMENT '手机号',
    `zz_create_time`         datetime         DEFAULT NULL COMMENT '创建时间',
    `zz_modified_time`       datetime         DEFAULT NULL COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0、否 1、是)',
    PRIMARY KEY (`zz_account_id`),
    UNIQUE KEY `uniq_email` (`zz_email`),
    UNIQUE KEY `uniq_phone` (`zz_phone`),
    UNIQUE KEY `uniq_username` (`zz_username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='账户表';


CREATE TABLE `Zz_Role`
(
    `zz_role_id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '角色主键',
    `zz_role_name`           varchar(50)      DEFAULT NULL COMMENT '角色名称',
    `zz_role_code`           varchar(20)     NOT NULL COMMENT '角色标识',
    `zz_parent_id`           bigint unsigned  DEFAULT NULL COMMENT '父角色ID，NULL表示顶级角色',
    `zz_create_time`         datetime         DEFAULT NULL COMMENT '创建时间',
    `zz_modified_time`       datetime         DEFAULT NULL COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0、否 1、是)',
    PRIMARY KEY (`zz_role_id`),
    KEY `zz_parent_id` (`zz_parent_id`),
    CONSTRAINT `zz_role_ibfk_1` FOREIGN KEY (`zz_parent_id`) REFERENCES `Zz_Role` (`zz_role_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色表';


CREATE TABLE `Zz_Permission`
(
    `zz_permission_id`       bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '权限主键',
    `zz_permission_name`     varchar(50)     NOT NULL COMMENT '权限名称',
    `zz_permission_code`     varchar(50)      DEFAULT NULL COMMENT '权限标识码',
    `zz_parent_id`           bigint unsigned  DEFAULT NULL COMMENT '父权限ID，NULL表示顶级权限',
    `zz_create_time`         datetime         DEFAULT NULL COMMENT '创建时间',
    `zz_modified_time`       datetime         DEFAULT NULL COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0、否 1、是)',
    PRIMARY KEY (`zz_permission_id`),
    KEY `zz_parent_id` (`zz_parent_id`),
    CONSTRAINT `zz_permission_ibfk_1` FOREIGN KEY (`zz_parent_id`) REFERENCES `Zz_Permission` (`zz_permission_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='权限表';


CREATE TABLE `Zz_Resource`
(
    `zz_resource_id`         bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '资源主键',
    `zz_resource_name`       varchar(50)     NOT NULL COMMENT '资源名称',
    `zz_parent_id`           bigint unsigned  DEFAULT NULL COMMENT '父资源ID，NULL表示顶级资源',
    `zz_link`                varchar(200)     DEFAULT NULL COMMENT '资源链接',
    `zz_order`               int              DEFAULT NULL COMMENT '显示顺序',
    `zz_resource_type`       tinyint          DEFAULT NULL COMMENT '资源类型(0:目录, 1:菜单, 2:按钮)',
    `zz_create_time`         datetime         DEFAULT NULL COMMENT '创建时间',
    `zz_modified_time`       datetime         DEFAULT NULL COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0、否 1、是)',
    PRIMARY KEY (`zz_resource_id`),
    KEY `zz_parent_id` (`zz_parent_id`),
    CONSTRAINT `zz_resource_ibfk_1` FOREIGN KEY (`zz_parent_id`) REFERENCES `Zz_Resource` (`zz_resource_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='资源表';


CREATE TABLE `Zz_Account_Role`
(
    `zz_account_id` bigint unsigned NOT NULL COMMENT '账户主键',
    `zz_role_id`    bigint unsigned NOT NULL COMMENT '角色主键',
    PRIMARY KEY (`zz_account_id`, `zz_role_id`),
    KEY `zz_role_id` (`zz_role_id`),
    CONSTRAINT `zz_account_role_ibfk_1` FOREIGN KEY (`zz_account_id`) REFERENCES `Zz_Account` (`zz_account_id`) ON DELETE CASCADE,
    CONSTRAINT `zz_account_role_ibfk_2` FOREIGN KEY (`zz_role_id`) REFERENCES `Zz_Role` (`zz_role_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='账户角色关联表';


CREATE TABLE `Zz_Role_Permission`
(
    `zz_role_id`       bigint unsigned NOT NULL COMMENT '角色主键',
    `zz_permission_id` bigint unsigned NOT NULL COMMENT '权限主键',
    PRIMARY KEY (`zz_role_id`, `zz_permission_id`),
    KEY `zz_permission_id` (`zz_permission_id`),
    CONSTRAINT `zz_role_permission_ibfk_1` FOREIGN KEY (`zz_role_id`) REFERENCES `Zz_Role` (`zz_role_id`) ON DELETE CASCADE,
    CONSTRAINT `zz_role_permission_ibfk_2` FOREIGN KEY (`zz_permission_id`) REFERENCES `Zz_Permission` (`zz_permission_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色权限关联表';


CREATE TABLE `Zz_Role_Permission`
(
    `zz_role_id`       bigint unsigned NOT NULL COMMENT '角色主键',
    `zz_permission_id` bigint unsigned NOT NULL COMMENT '权限主键',
    PRIMARY KEY (`zz_role_id`, `zz_permission_id`),
    KEY `zz_permission_id` (`zz_permission_id`),
    CONSTRAINT `zz_role_permission_ibfk_1` FOREIGN KEY (`zz_role_id`) REFERENCES `Zz_Role` (`zz_role_id`) ON DELETE CASCADE,
    CONSTRAINT `zz_role_permission_ibfk_2` FOREIGN KEY (`zz_permission_id`) REFERENCES `Zz_Permission` (`zz_permission_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色权限关联表';

CREATE TABLE `Zz_Role_Resource`
(
    `zz_role_id`     bigint unsigned NOT NULL COMMENT '角色主键',
    `zz_resource_id` bigint unsigned NOT NULL COMMENT '资源主键',
    PRIMARY KEY (`zz_role_id`, `zz_resource_id`),
    KEY `zz_resource_id` (`zz_resource_id`),
    CONSTRAINT `zz_role_resource_ibfk_1` FOREIGN KEY (`zz_role_id`) REFERENCES `Zz_Role` (`zz_role_id`) ON DELETE CASCADE,
    CONSTRAINT `zz_role_resource_ibfk_2` FOREIGN KEY (`zz_resource_id`) REFERENCES `Zz_Resource` (`zz_resource_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色资源关联表';


CREATE TABLE `Zz_Book`
(
    `zz_book_id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '书籍主键',
    `zz_title`               varchar(50)      DEFAULT NULL COMMENT '书籍标题',
    `zz_author`              varchar(255)     DEFAULT NULL COMMENT '作者',
    `zz_url`                 varchar(255)     DEFAULT NULL COMMENT '图像URL',
    `zz_price`               decimal(10, 2)   DEFAULT NULL COMMENT '价格',
    `zz_inventory_count`     int              DEFAULT NULL COMMENT '库存数量',
    `zz_create_time`         datetime         DEFAULT NULL COMMENT '创建时间',
    `zz_modified_time`       datetime         DEFAULT NULL COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0-否, 1-是)',
    PRIMARY KEY (`zz_book_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='书籍表';


CREATE TABLE `Zz_Customer`
(
    `zz_customer_id`         bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '客户主键',
    `zz_real_name`           varchar(50)      DEFAULT NULL COMMENT '真实姓名',
    `zz_sex`                 char(1)          DEFAULT NULL COMMENT '性别',
    `zz_age`                 tinyint unsigned DEFAULT NULL COMMENT '年龄',
    `zz_email`               varchar(100)     DEFAULT NULL COMMENT '邮箱',
    `zz_phone`               varchar(11)      DEFAULT NULL COMMENT '电话号码',
    `zz_address`             varchar(200)     DEFAULT NULL COMMENT '地址',
    `zz_password`            varchar(255)     DEFAULT NULL COMMENT '密码',
    `zz_balance`             decimal(10, 2)   DEFAULT '0.00' COMMENT '账户余额',
    `zz_create_time`         datetime         DEFAULT NULL COMMENT '创建时间',
    `zz_modified_time`       datetime         DEFAULT NULL COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0-否, 1-是)',
    PRIMARY KEY (`zz_customer_id`),
    UNIQUE KEY `uniq_email` (`zz_email`),
    UNIQUE KEY `uniq_phone` (`zz_phone`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='客户表';


CREATE TABLE `Zz_Order`
(
    `zz_order_id`            bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '订单主键',
    `zz_customer_id`         bigint unsigned NOT NULL COMMENT '客户主键',
    `zz_order_total`         decimal(10, 2)  NOT NULL COMMENT '订单总金额',
    `zz_create_time`         datetime COMMENT '创建时间',
    `zz_modified_time`       datetime COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0-否, 1-是)',
    PRIMARY KEY (`zz_order_id`),
    FOREIGN KEY (`zz_customer_id`) REFERENCES `Zz_Customer` (`zz_customer_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='订单表';


CREATE TABLE `Zz_OrderItem`
(
    `zz_item_id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '订单项目主键',
    `zz_order_id`            bigint unsigned NOT NULL COMMENT '订单主键',
    `zz_book_id`             bigint unsigned NOT NULL COMMENT '书籍主键',
    `zz_quantity`            int             NOT NULL COMMENT '数量',
    `zz_price`               decimal(10, 2)  NOT NULL COMMENT '价格',
    `zz_create_time`         datetime COMMENT '创建时间',
    `zz_modified_time`       datetime COMMENT '修改时间',
    `zz_create_account_id`   bigint unsigned  DEFAULT NULL COMMENT '创建人账户ID',
    `zz_modified_account_id` bigint unsigned  DEFAULT NULL COMMENT '修改人账户ID',
    `zz_deleted`             tinyint unsigned DEFAULT '0' COMMENT '逻辑删除标识(0-否, 1-是)',
    PRIMARY KEY (`zz_item_id`),
    FOREIGN KEY (`zz_order_id`) REFERENCES `Zz_Order` (`zz_order_id`),
    FOREIGN KEY (`zz_book_id`) REFERENCES `Zz_Book` (`zz_book_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='订单项目表';

