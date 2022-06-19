alter table pedido add codigo varchar(36) not null after id;
-- uuid gera codigos unicos fazendo com que nao haja a necessidade de expor os Id's dos pedidos na hora de chamar o endpoint, pois nessa situação estamos tratando o id como um dado sensivel.
update pedido set codigo = uuid();
alter table pedido add constraint uk_pedido_codigo unique (codigo);