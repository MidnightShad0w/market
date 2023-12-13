alter table orders_details alter column amount type integer using amount::integer;
alter table orders_details alter column price type double precision using price::double precision;