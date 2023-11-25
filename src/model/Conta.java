package model;

public class Conta {
	private int agencia;
	private int conta;
	private float saldo;
	private String cpf;

	public Conta(int agencia, int conta, float saldo, String cpf) {
		super();
		this.agencia = agencia;
		this.conta = conta;
		this.saldo = saldo;
		this.cpf = cpf;
	}

	public Conta() {
		super();
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getConta() {
		return conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Conta [agencia=" + agencia + ", conta=" + conta + ", saldo=" + saldo + ", cpf=" + cpf + "]";
	}

}
