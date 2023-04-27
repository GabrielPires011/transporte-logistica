CREATE TABLE tb_cliente (
                         id BIGINT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         cnpj BIGINT NOT NULL,
                         id_endereco BIGINT NOT NULL REFERENCES tb_endereco(id)
);