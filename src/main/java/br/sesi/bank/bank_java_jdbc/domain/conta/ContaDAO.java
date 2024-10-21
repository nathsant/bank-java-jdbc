package br.sesi.bank.bank_java_jdbc.domain.conta;

import java.sql.Connection;

public class ContaDAO {

    private Connection conn;

    ContaDAO(Connection connection) {
        this.conn = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta){
    }
}
