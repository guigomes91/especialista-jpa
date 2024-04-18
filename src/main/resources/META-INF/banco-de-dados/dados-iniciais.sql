insert into produto (id, nome, preco, data_criacao, descricao) values (1, 'Kindle', 499.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome, preco, data_criacao, descricao) values (3, 'Camera GoPro Hero7', 4499.0, date_sub(sysdate(), interval 1 day), 'Melhor camera do mercado, foco e ação!');

insert into cliente (id, nome, cpf) values (1, 'Maria José', '123456');
insert into cliente (id, nome, cpf) values (2, 'João Pedro', '1234567');

insert into cliente_detalhe (sexo, cliente_id) values ('MASCULINO', 1);
insert into cliente_detalhe (sexo, cliente_id) values ('MASCULINO', 2);

insert into pedido (id, cliente_id, data_criacao, total, status, data_conclusao) values (1, 1, sysdate(), 2398.0, 'AGUARDANDO', date_sub(sysdate(), interval 5 day));
insert into pedido (id, cliente_id, data_criacao, total, status, data_conclusao) values (2, 1, sysdate(), 499.0, 'AGUARDANDO', sysdate());

insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499, 2);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 3, 1400, 1);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499, 1);

insert into pagamento (pedido_id, status, numero_cartao, tipo_pagamento, codigo_barras) values (2, 'PROCESSANDO', '123', 'cartao', '1234569877-98787-8878-1000024');

insert into nota_fiscal (pedido_id, xml, data_emissao) values (2, '<xml/>', sysdate());

insert into categoria (nome) values ('Eletrônicos');
insert into categoria (nome) values ('Livros');
insert into categoria (nome) values ('Esportes');
insert into categoria (nome) values ('Futebol');
insert into categoria (nome) values ('Natação');
insert into categoria (nome) values ('Notebooks');
insert into categoria (nome) values ('Smartphones');

insert into produto_categoria (produto_id, categoria_id) values (1, 2);