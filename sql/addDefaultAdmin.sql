create table user
(
    user_id int not null
        primary key,
    active bit not null,
    authority varchar(255) null,
    password varchar(255) null,
    user_name varchar(255) null,
    constraint UK_lqjrcobrh9jc8wpcar64q1bfh
        unique (user_name)
);

INSERT INTO user (user_id, active, authority, password, user_name) VALUES (1, true, 'ADMIN', '$2a$10$qEOdpaB2kwVABt.UmltqJ.2646AiALAMoSebM1a9FrJWi3gBvT3yy', 'admin');