create table if not exists user (
id int not null primary key auto_increment, username varchar(32), password varchar(32),
unique index (username)
);
create table if not exists restaurant (
    id int not null primary key auto_increment, name varchar(32), location varchar(32)
);

create table if not exists menu_item(
    id int not null primary key auto_increment,
    name varchar(32),
    description varchar(255),
    price double,
    restaurant_id int,
    foreign key (restaurant_id) references restaurant(id)
);

create table if not exists customer_order(
    id int not null primary key auto_increment,
    restaurant_id int,
    customer_id int,
    status enum('PENDING', 'DOING', 'DELIVERING', 'DELIVERED', 'CANCELLED'),
    created_at datetime,
    foreign key (restaurant_id) references restaurant(id),
    foreign key (customer_id) references user(id)
);

create table if not exists customer_order_item(
    order_id int,
    menu_item_id int,
    foreign key (order_id) references customer_order(id),
    foreign key (menu_item_id) references menu_item(id),
    unique index (order_id, menu_item_id)
);