insert into user(username, password) values ('moe', '123456');
insert into restaurant(id, name, location) values (1, 'Istanbul', '123456,123455');
insert into restaurant(id, name, location) values (2, 'Stefan Steak', '122345,123455');
insert into restaurant(id, name, location) values (3, 'Amigo', '222345,123455');

insert into menu_item(name, description, price, restaurant_id) values ('Shish Kebab', 'whatever', 10.0, 1);
insert into menu_item(name, description, price, restaurant_id) values ('Kana roll', 'whatever', 9.0, 1);
insert into menu_item(name, description, price, restaurant_id) values ('Kleftikko', 'whatever', 20.0, 1);

insert into menu_item(name, description, price, restaurant_id) values ('Shish Kebab', 'whatever', 10.0, 2);
insert into menu_item(name, description, price, restaurant_id) values ('Kana roll', 'whatever', 9.0, 2);
insert into menu_item(name, description, price, restaurant_id) values ('Kleftikko', 'whatever', 20.0, 2);

insert into menu_item(name, description, price, restaurant_id) values ('Shish Kebab', 'whatever', 10.0, 3);
insert into menu_item(name, description, price, restaurant_id) values ('Kana roll', 'whatever', 9.0, 3);
insert into menu_item(name, description, price, restaurant_id) values ('Kleftikko', 'whatever', 20.0, 3);

-- MOCK order with items
insert into customer_order(id, restaurant_id, customer_id, status, created_at) values(1, 1, 1, 'DELIVERED', CURRENT_TIMESTAMP);
insert into customer_order_item(order_id, menu_item_id) values (1,1);
insert into customer_order_item(order_id, menu_item_id) values (1,3);
