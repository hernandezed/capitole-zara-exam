CREATE TABLE BRAND
(
    ID IDENTITY PRIMARY KEY,
    NAME VARCHAR(255) not null
);

CREATE TABLE PRODUCT
(
    ID IDENTITY PRIMARY KEY,
    PRODUCT_CODE BIGINT,
    BRAND_ID     BIGINT       not null,
    NAME         VARCHAR(255) not null
);

CREATE TABLE PRICE
(
    ID IDENTITY PRIMARY KEY,
    PRODUCT_ID BIGINT         not null,
    AMOUNT     DECIMAL(20, 2) not null,
    CURRENCY   VARCHAR(3)     not null,
    foreign key (PRODUCT_ID) references PRODUCT (ID)
);

CREATE TABLE RATE
(
    ID IDENTITY PRIMARY KEY,
    PRICE_ID   BIGINT         not null,
    PRIORITY   INTEGER null default 0,
    RATE_VALUE DECIMAL(10, 2) not null,
    START_DATE TIMESTAMP      not null,
    END_DATE   TIMESTAMP      not null,
    foreign key (PRICE_ID) references PRICE (ID)
);






