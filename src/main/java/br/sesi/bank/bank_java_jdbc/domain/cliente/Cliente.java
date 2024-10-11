package br.sesi.bank.bank_java_jdbc.domain.cliente;

public class Cliente {
    private String nome;
    private String email;
    private String cpf;

    public Cliente (DadosCadastroCliente dados){
        this.nome = dados.nome;
        this.email = dados.email;
        this.cpf = dados.cpf;
    }

    public String getEmail() {
        return email;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
}
