-- 菜单
CREATE TABLE `sys_menu` (
                            `menu_id` bigint NOT NULL AUTO_INCREMENT,
                            `parent_id` bigint COMMENT '父菜单ID，一级菜单为0',
                            `name` varchar(50) COMMENT '菜单名称',
                            `url` varchar(200) COMMENT '菜单URL',
                            `perms` varchar(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
                            `type` int COMMENT '类型   0：目录   1：菜单   2：按钮',
                            `icon` varchar(50) COMMENT '菜单图标',
                            `order_num` int COMMENT '排序',
                            PRIMARY KEY (`menu_id`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='菜单管理';

-- 系统用户
CREATE TABLE `sys_user` (
                            `user_id` bigint NOT NULL AUTO_INCREMENT,
                            `username` varchar(50) NOT NULL COMMENT '用户名',
                            `password` varchar(100) COMMENT '密码',
                            `salt` varchar(20) COMMENT '盐',
                            `email` varchar(100) COMMENT '邮箱',
                            `mobile` varchar(100) COMMENT '手机号',
                            `status` tinyint COMMENT '状态  0：禁用   1：正常',
                            `create_user_id` bigint(20) COMMENT '创建者ID',
                            `create_time` datetime COMMENT '创建时间',
                            PRIMARY KEY (`user_id`),
                            UNIQUE INDEX (`username`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='系统用户';

-- 系统用户Token
CREATE TABLE `sys_user_token` (
                                  `user_id` bigint(20) NOT NULL,
                                  `token` varchar(100) NOT NULL COMMENT 'token',
                                  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                  PRIMARY KEY (`user_id`),
                                  UNIQUE KEY `token` (`token`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='系统用户Token';

-- 系统验证码
CREATE TABLE `sys_captcha` (
                               `uuid` char(36) NOT NULL COMMENT 'uuid',
                               `code` varchar(6) NOT NULL COMMENT '验证码',
                               `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
                               PRIMARY KEY (`uuid`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='系统验证码';

-- 角色
CREATE TABLE `sys_role` (
                            `role_id` bigint NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(100) COMMENT '角色名称',
                            `remark` varchar(100) COMMENT '备注',
                            `create_user_id` bigint(20) COMMENT '创建者ID',
                            `create_time` datetime COMMENT '创建时间',
                            PRIMARY KEY (`role_id`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='角色';

-- 用户与角色对应关系
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint COMMENT '用户ID',
                                 `role_id` bigint COMMENT '角色ID',
                                 PRIMARY KEY (`id`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='用户与角色对应关系';

-- 角色与菜单对应关系
CREATE TABLE `sys_role_menu` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `role_id` bigint COMMENT '角色ID',
                                 `menu_id` bigint COMMENT '菜单ID',
                                 PRIMARY KEY (`id`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT='角色与菜单对应关系';