package arvoreAVL;

import java.util.ArrayList;

import model.Conta;

public class NoAvl {
	private Conta item;
	private ArrayList<Conta> contas;
	private NoAvl esq, dir;
	private int fatorBalanceamento;

	public NoAvl(Conta conta) {
		this.item = conta;
		this.contas = new ArrayList<Conta>();
		this.contas.add(conta);
	}

	public Conta getItem() {
		return item;
	}

	public void setItem(Conta item) {
		this.item = item;
	}

	public ArrayList<Conta> getContas() {
		return contas;
	}

	public void setContas(ArrayList<Conta> contas) {
		this.contas = contas;
	}

	public NoAvl getEsq() {
		return esq;
	}

	public void setEsq(NoAvl esq) {
		this.esq = esq;
	}

	public NoAvl getDir() {
		return dir;
	}

	public void setDir(NoAvl dir) {
		this.dir = dir;
	}

	public int getFatorBalanceamento() {
		return fatorBalanceamento;
	}

	public void setFatorBalanceamento(int fatorBalanceamento) {
		this.fatorBalanceamento = fatorBalanceamento;
	}

	public void inserirEmContas(Conta conta) {
		this.contas.add(conta);
	}

	public String toString() {
		String msg = "";

		for (int c = 0; c < this.contas.size(); c++) {
			msg += this.contas.get(c).toString();
		}

		return msg;
	}

// E todos os gets e sets necessarios
}
