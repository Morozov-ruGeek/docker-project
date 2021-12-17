drop table if exists payments;
create table payments
(
    id                  serial primary key,
    payment_id          bigint  not null,
    payment_amount      decimal not null,
    date_of_payment     date default current_date
);