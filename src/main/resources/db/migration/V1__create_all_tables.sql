CREATE TABLE product
(
    id           BIGINT PRIMARY KEY,
    title        VARCHAR(255),
    handle       VARCHAR(255),
    body_html    TEXT,
    published_at VARCHAR,
    created_at   VARCHAR,
    updated_at   VARCHAR,
    vendor       VARCHAR(255),
    product_type VARCHAR(255)
);

CREATE TABLE product_tags
(
    product_id BIGINT,
    tag        VARCHAR(255)
);

CREATE TABLE image
(
    id         BIGINT PRIMARY KEY,
    created_at VARCHAR,
    position   INT,
    updated_at VARCHAR,
    alt        VARCHAR(255),
    src        VARCHAR(255),
    width      INT,
    height     INT,
    product_id BIGINT NOT NULL,
    variant_id BIGINT
);

CREATE TABLE variant
(
    id                BIGINT PRIMARY KEY,
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
    created_at        VARCHAR,
    updated_at        VARCHAR,
    product_id        BIGINT NOT NULL,
    featured_image_id BIGINT
);

CREATE TABLE option
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255),
    position   INT,
    product_id BIGINT NOT NULL
);

CREATE TABLE option_entity_values
(
    option_entity_id BIGINT,
    values           VARCHAR(255)
);

ALTER TABLE product_tags
    ADD CONSTRAINT fk_product_tags_product_id FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE;

ALTER TABLE image
    ADD CONSTRAINT fk_image_product_id FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_image_variant_id FOREIGN KEY (variant_id) REFERENCES variant (id) ON
DELETE
CASCADE;

ALTER TABLE variant
    ADD CONSTRAINT fk_variant_product_id FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_variant_featured_image_id FOREIGN KEY (featured_image_id) REFERENCES image (id) ON
DELETE
CASCADE;

ALTER TABLE option
    ADD CONSTRAINT fk_product_option_product_id FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE;

ALTER TABLE option_entity_values
    ADD CONSTRAINT fk_option_values_option_entity_id FOREIGN KEY (option_entity_id) REFERENCES option (id) ON DELETE CASCADE;