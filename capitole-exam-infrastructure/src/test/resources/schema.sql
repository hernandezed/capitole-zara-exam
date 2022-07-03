CREATE TABLE BRAND
(
    ID IDENTITY PRIMARY KEY,
    NAME VARCHAR(255) not null
);

CREATE TABLE PRICE
(
    ID         IDENTITY PRIMARY KEY,            -- en el examen PRICE_LIST
    PRODUCT_ID BIGINT         not null,
    PRIORITY   INTEGER null default 0,
    BRAND_ID   BIGINT         not null,
    PRICE      DECIMAL(20, 2) not null,
    CURRENCY   VARCHAR(3)     not null, -- en el examen CURR
    START_DATE TIMESTAMP      not null,
    END_DATE   TIMESTAMP      not null,
    foreign key (BRAND_ID) references BRAND (ID)
);







