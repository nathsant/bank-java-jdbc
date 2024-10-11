package br.sesi.bank.bank_java_jdbc.domain.conta;

import br.sesi.bank.bank_java_jdbc.domain.cliente.Cliente;

import java.math.BigDecimal;

public class Conta {
    private Integer numero;
    private BigDecimal Valor;
    private Cliente titular;

    public Conta(Integer numero, BigDecimal Valor, Cliente titular){
        this.numero = numero;
        this.Valor = Valor;
        this.titular = titular;
    }
    public boolean possuiSaldo(){
        return true;
    }
    public void sacar(BigDecimal valor){

    }
    public void depositar(BigDecimal valor){

    }
    public Integer getNumero(){
        return numero;
    }
    public BigDecimal getSaldo(){
        return Valor;
    }
    public Cliente getTitular(){
        return titular;
    }
}
