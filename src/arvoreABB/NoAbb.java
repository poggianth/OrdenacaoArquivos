package arvoreABB;

import java.util.ArrayList;

import model.Conta;

public class NoAbb {
	private Conta item;
	private ArrayList<Conta> contas;
	private NoAbb esq, dir;

	public NoAbb(Conta conta) {
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

	public NoAbb getEsq() {
		return esq;
	}

	public void setEsq(NoAbb esq) {
		this.esq = esq;
	}

	public NoAbb getDir() {
		return dir;
	}

	public void setDir(NoAbb dir) {
		this.dir = dir;
	}

	public ArrayList<Conta> getContas() {
		return contas;
	}

	public void setContas(ArrayList<Conta> contas) {
		this.contas = contas;
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

}
