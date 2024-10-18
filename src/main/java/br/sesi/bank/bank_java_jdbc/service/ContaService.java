package br.sesi.bank.bank_java_jdbc.service;

import br.sesi.bank.bank_java_jdbc.domain.cliente.Cliente;
import br.sesi.bank.bank_java_jdbc.domain.conta.Conta;
import br.sesi.bank.bank_java_jdbc.domain.conta.DadosAberturaConta;
import br.sesi.bank.bank_java_jdbc.exceptions.RegraDeNegocioException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ContaService {
    private Set<Conta> contas = new HashSet<>();

    public Set<Conta> listarContasAbertas(){
        return this.contas;
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta){
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        Cliente cliente = new Cliente(dadosDaConta.dadosCliente);
        Conta conta = new Conta(dadosDaConta.numero, BigDecimal.ZERO, cliente);
        if (contas.contains(conta)) {
            throw new RegraDeNegocioException("Já existe outra conta aberta com o mesmo número!");
        }

        contas.add(conta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo Insuficiente!");
        }

        conta.sacar(valor);
    }
    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        conta.depositar(valor);
    }
    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda não possui saldo!");
        }

        contas.remove(conta);
    }
    private Conta buscarContaPorNumero(Integer numero){
        return contas
                .stream()
                .filter(c -> c.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Não existe conta cadastrada com esse número!"));

    }
}
