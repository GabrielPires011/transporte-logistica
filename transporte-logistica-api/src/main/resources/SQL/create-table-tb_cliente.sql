CREATE TABLE tb_cliente (
                            id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                            nome VARCHAR(255) NOT NULL,
                            cnpj BIGINT NOT NULL,
                            id_endereco BIGINT NOT NULL,
                            CONSTRAINT fk_cliente_endereco FOREIGN KEY (id_endereco)
                                REFERENCES tb_endereco (id)
);