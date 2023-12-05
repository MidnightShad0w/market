drop table if exists carts_products;
create table if not exists carts_details (
    id integer not null,
    cart_id integer not null,
    product_id integer not null,
    details_id integer not null,
    amount integer,
    price double precision,
    primary key (id)
    );
alter table if exists carts_details drop constraint if exists carts_details_products;
alter table if exists carts_details add constraint carts_details_products foreign key (product_id) references products;
alter table if exists carts_details drop constraint if exists carts_details_carts;
alter table if exists carts_details add constraint carts_details_carts foreign key (cart_id) references carts;
alter table if exists carts_details drop constraint if exists carts_details_details;
alter table if exists carts_details add constraint carts_details_details foreign key (details_id) references carts_details;