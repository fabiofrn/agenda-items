CREATE TABLE agenda_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    session_open_time TIMESTAMP,
    session_close_time TIMESTAMP
);

CREATE TABLE votes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_agenda_item BIGINT NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    vote CHAR(1) NOT NULL,
    UNIQUE (id_agenda_item, cpf),
    FOREIGN KEY (id_agenda_item) REFERENCES agenda_items(id)
);




