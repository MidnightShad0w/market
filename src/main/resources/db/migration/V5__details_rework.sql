drop sequence if exists cart_details_seq;
drop sequence if exists orders_details_seq;

drop table if exists carts_details;
drop table if exists orders_details;

create sequence if not exists details_seq start with 1 increment by 1;

create table if not exists details (
    id integer not null,
    cart_id integer,
    order_id integer,
    product_id integer,
    amount integer,
    price double precision,
    primary key (id)
);

alter table if exists details add constraint details_cart_fk foreign key (cart_id) references carts;
alter table if exists details add constraint details_order_fk foreign key (order_id) references orders;
alter table if exists details add constraint details_product_fk foreign key (product_id) references products;