
CREATE TABLE GALAXY_TB_API_CLIENT (
    client_id varchar(20) PRIMARY KEY,
    client_secret varchar(64),
    client_enable varchar(64),

    create_date CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modify_date CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);