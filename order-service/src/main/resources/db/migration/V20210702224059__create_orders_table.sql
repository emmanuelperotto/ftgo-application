create table orders
(
    id int auto_increment,
    created_at datetime not null,
    constraint orders_pk
        primary key (id)
);
