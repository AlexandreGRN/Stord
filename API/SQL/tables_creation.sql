DROP DATABASE IF EXISTS stord_test_db;
CREATE DATABASE IF NOT EXISTS stord_test_db;
USE stord_test_db;
CREATE table IF NOT EXISTS owners(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS categories(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                VARCHAR(255),
    description         VARCHAR(750),
    imageURL            VARCHAR(255),
    favorite            BOOLEAN,
    owner_id            INT NOT NULL,
    parent_category_id  INT,

    FOREIGN KEY (owner_id) REFERENCES owners(id),
    FOREIGN KEY (parent_category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS items(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                VARCHAR(255),
    description         VARCHAR(255),
    remaining           BIGINT,
    alert               BIGINT,
    imageURL            VARCHAR(255),
    favorite            BOOLEAN,
    parent_category_id  INT,

    FOREIGN KEY (parent_category_id) REFERENCES categories(id)
);