CREATE SCHEMA IF NOT EXISTS springlibrarymanagement;

CREATE TABLE IF NOT EXISTS springlibrarymanagement.authors
(
    id serial,
    name character varying(255),
    surname character varying(255),
	UNIQUE (name,surname),
    CONSTRAINT authors_pkey PRIMARY KEY (id)
);

    CREATE TABLE IF NOT EXISTS springlibrarymanagement.books
    (
        id serial,
        name character varying(255) UNIQUE,
        publish_date date,
        quantity integer,
        author_id integer,
        CONSTRAINT books_pkey PRIMARY KEY (id),
        CONSTRAINT fk_author_id FOREIGN KEY (author_id)
            REFERENCES springlibrarymanagement.authors (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
    );

        CREATE TABLE IF NOT EXISTS springlibrarymanagement.clients
        (
            id serial,
            name character varying(255),
            surname character varying(255),
            email character varying(255) UNIQUE,
            phone_number character varying(255) UNIQUE,
            CONSTRAINT clients_pkey PRIMARY KEY (id)
        );

                CREATE TABLE IF NOT EXISTS springlibrarymanagement.orders
                (
                    id serial,
                    due_date date,
                    issue_date date,
                    client_id integer,
                    CONSTRAINT orders_pkey PRIMARY KEY (id),
                    CONSTRAINT fk_client_id FOREIGN KEY (client_id)
                        REFERENCES springlibrarymanagement.clients (id)
                        ON UPDATE CASCADE
                        ON DELETE CASCADE
                );

            CREATE TABLE IF NOT EXISTS springlibrarymanagement.order_books
            (
                order_id integer NOT NULL,
                book_id integer NOT NULL,
                CONSTRAINT fk_book_id FOREIGN KEY (book_id)
                    REFERENCES springlibrarymanagement.books (id)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE,
                CONSTRAINT fk_order_id FOREIGN KEY (order_id)
                    REFERENCES springlibrarymanagement.orders (id)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE
            );