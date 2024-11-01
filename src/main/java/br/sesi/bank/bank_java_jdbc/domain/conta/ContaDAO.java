package br.sesi.bank.bank_java_jdbc.domain.conta;

import br.sesi.bank.bank_java_jdbc.domain.cliente.Cliente;
import br.sesi.bank.bank_java_jdbc.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private Connection conn;

    public ContaDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(DadosAberturaConta dadosDaConta) {
        Cliente cliente = new Cliente(dadosDaConta.dadosCliente);
        Conta conta = new Conta(dadosDaConta.numero, BigDecimal.ZERO, cliente, true);
        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email, ativo)" + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2,BigDecimal.ZERO);
            preparedStatement.setString(3, dadosDaConta.dadosCliente.nome);
            preparedStatement.setString(4, dadosDaConta.dadosCliente.cpf);
            preparedStatement.setString(5, dadosDaConta.dadosCliente.email);
            preparedStatement.setBoolean(6, conta.isAtivo());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar(){
        Set<Conta> contas = new HashSet<>();
        String sql = "select * from conta";

        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Integer numero = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                Boolean ativo = resultSet.getBoolean(6);
                Cliente cliente = new Cliente(new DadosCadastroCliente(nome, cpf, email));
                Conta conta = new Conta(numero,saldo, cliente, ativo);
                contas.add(conta);
            }

            resultSet.close();
            ps.close();
            conn.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contas;
    }

    public void alterarSaldo(Integer numero, BigDecimal valor){
        String sql = "update conta set saldo = ? where numero = ?";
        PreparedStatement ps;

        try{
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, valor);
            ps.setInt(2, numero);

            ps.execute();
            ps.close();
            conn.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer numeroConta){
        String sql = "delete from contas where numero = ?";
        PreparedStatement ps;

        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numeroConta);

            ps.execute();
            ps.close();
            conn.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Conta listarPorNumero(Integer numero){
        String sql = "select * from conta where numero = " + numero + "and esta_ativa = true";

        PreparedStatement ps;
        ResultSet resultSet;
        Conta conta = null;

        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numero);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                Integer numeroRecuperado = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                boolean estaAtiva = resultSet.getBoolean(6);

                DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(dadosCadastroCliente);

                conta = new Conta(numeroRecuperado, saldo, cliente, estaAtiva);
            }

            resultSet.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conta;
    }
}