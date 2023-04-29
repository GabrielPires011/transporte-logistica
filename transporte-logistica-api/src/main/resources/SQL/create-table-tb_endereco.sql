CREATE TABLE tb_endereco (
                          id BIGINT PRIMARY KEY,
                          rua VARCHAR(255) NOT NULL,
                          numero VARCHAR(10) NOT NULL,
                          bairro VARCHAR(255) NOT NULL,
                          cidade VARCHAR(255) NOT NULL,
                          estado VARCHAR(2) NOT NULL,
                          cep VARCHAR(8) NOT NULL,
                          latitude DECIMAL(10, 6) NOT NULL,
                          longitude DECIMAL(10, 6) NOT NULL
);