INSERT INTO COZINHA (id, NOME) VALUES (1, 'Tailandesa');
INSERT INTO COZINHA (id, NOME) VALUES (2, 'Indiana');
INSERT INTO COZINHA (id, NOME) VALUES (3, 'Japonesa');

INSERT INTO RESTAURANTE (NOME, TAXA_FRETE, cozinha_id) VALUES ('Thai Gourmet', 10.0, 1);
INSERT INTO RESTAURANTE (NOME, TAXA_FRETE, cozinha_id) VALUES ('Thai Delivery', 9.5, 1);
INSERT INTO RESTAURANTE (NOME, TAXA_FRETE, cozinha_id) VALUES ('Tuk Tuk Comida Indiana', 15.0, 2);

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');