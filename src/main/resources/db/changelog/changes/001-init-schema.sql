CREATE TABLE IF NOT EXISTS authors
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255),
    surname VARCHAR(255),
    CONSTRAINT uk_authors_name_surname UNIQUE (name, surname)
);

CREATE TABLE IF NOT EXISTS books
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) UNIQUE,
    publish_date DATE,
    quantity     INTEGER,
    author_id    INTEGER REFERENCES authors (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS clients
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255),
    surname      VARCHAR(255),
    email        VARCHAR(255) UNIQUE,
    phone_number VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS orders
(
    id         SERIAL PRIMARY KEY,
    issue_date DATE,
    due_date   DATE,
    client_id  INTEGER REFERENCES clients (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_books
(
    order_id INTEGER NOT NULL REFERENCES orders (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    book_id  INTEGER NOT NULL REFERENCES books (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT pk_order_books PRIMARY KEY (order_id, book_id)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    id   SERIAL PRIMARY KEY,
    role VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id INTEGER NOT NULL REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES user_roles (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id)
);
