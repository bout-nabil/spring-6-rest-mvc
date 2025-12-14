
    drop table if exists coffee;

    drop table if exists customer;

    create table coffee (
        coffee_style tinyint check (coffee_style between 0 and 24),
        price_coffee decimal(38,2),
        quantity_coffee integer,
        version_coffee integer,
        created_at_coffee datetime(6),
        updated_at_coffee datetime(6),
        id_coffee varchar(36) not null,
        name_coffee varchar(50) not null,
        description_coffee varchar(250),
        primary key (id_coffee)
    ) engine=InnoDB;

    create table customer (
        version_customer integer,
        created_at_customer datetime(6),
        updated_at_customer datetime(6),
        id_customer varchar(36) not null,
        email_customer varchar(255),
        name_customer varchar(255),
        phone_customer varchar(255),
        primary key (id_customer)
    ) engine=InnoDB;
