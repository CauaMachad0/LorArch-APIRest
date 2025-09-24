insert into moto (placa, modelo, status, setor, data_cadastro, data_atualizacao)
values
    ('ABC1D23','Honda Biz 125','DISPONIVEL','Galpao Central', current_timestamp, current_timestamp),
    ('XYZ4J56','Yamaha Fazer 250','EM_MANUTENCAO','Galpao Zona Leste', current_timestamp, current_timestamp);

insert into ocorrencia (tipo, descricao, data, data_registro, moto_id)
values
    ('MANUTENCAO','Troca de pastilha', current_date, current_timestamp, 2);
