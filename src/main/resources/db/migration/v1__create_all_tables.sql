-- v1__create_all_tables.sql

CREATE TABLE product
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255),
    handle       VARCHAR(255),
    body_html    TEXT,
    published_at TIMESTAMP,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    vendor       VARCHAR(255),
    product_type VARCHAR(255)
);

CREATE TABLE product_tags
(
    product_id BIGINT,
    tag        VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE image
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP,
    position   INT,
    updated_at TIMESTAMP,
    alt        VARCHAR(255),
    src        VARCHAR(255),
    width      INT,
    height     INT,
    product_id BIGINT NOT NULL,
    variant_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES variant (id) ON DELETE CASCADE
);

CREATE TABLE `option`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    position   INT,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE option_values
(
    option_id BIGINT,
    value     VARCHAR(255),
    FOREIGN KEY (option_id) REFERENCES `option` (id) ON DELETE CASCADE
);

CREATE TABLE variant
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(255),
    option1           VARCHAR(255),
    option2           VARCHAR(255),
    option3           VARCHAR(255),
    sku               VARCHAR(255),
    requires_shipping BOOLEAN,
    taxable           BOOLEAN,
    available         BOOLEAN,
    price             DECIMAL(10, 2),
    grams             INT,
    compare_at_price  DECIMAL(10, 2),
    position          INT,
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    product_id        BIGINT NOT NULL,
    featured_image_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    FOREIGN KEY (featured_image_id) REFERENCES image (id) ON DELETE CASCADE
);