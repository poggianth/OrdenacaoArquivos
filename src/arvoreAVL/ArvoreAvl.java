package arvoreAVL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import model.Conta;

public class ArvoreAvl {
	private NoAvl raiz;
	private boolean h;
	private int quant;

	public ArvoreAvl() {
		this.quant = 0;
		this.raiz = null;
	}
	
	public int getQuant() {
		return quant;
	}

	public void inserir(Conta conta) {
		this.raiz = this.inserir(conta, this.raiz);
	}

	private NoAvl inserir(Conta conta, NoAvl no) {
		if (no == null) {
			NoAvl novo = new NoAvl(conta);
			this.h = true;
			
			return novo;
		} else {
			if (conta.getCpf().compareTo(no.getItem().getCpf()) == 0) {
				no.inserirEmContas(conta);
				
				return no;
			} else {
				if (conta.getCpf().compareTo(no.getItem().getCpf()) < 0) {
					// Insere à esquerda e verifica se precisa
					// balancear à direita
					no.setEsq(this.inserir(conta, no.getEsq()));
					no = this.balancearDir(no);
					
					return no;
				} else {
					// Insere à direita e verifica se precisa
					// balancear à esquerda
					no.setDir(this.inserir(conta, no.getDir()));
					no = this.balancearEsq(no);
					
					return no;
				}
			}

		}
	}

	private NoAvl balancearDir(NoAvl no) {
		if (this.h) {
			switch (no.getFatorBalanceamento()) {
			case 1:
				no.setFatorBalanceamento(0);
				this.h = false;
				break;
			case 0:
				no.setFatorBalanceamento(-1);
				break;
			case -1:
				no = this.rotaçãoDireita(no);
			}
		}
		return no;
	}

	private NoAvl balancearEsq(NoAvl no) {
		if (this.h) {
			switch (no.getFatorBalanceamento()) {
			case -1:
				no.setFatorBalanceamento(0);
				this.h = false;
				break;
			case 0:
				no.setFatorBalanceamento(1);
				break;
			case 1:
				no = this.rotaçãoEsquerda(no);
			}
		}
		return no;
	}

	private NoAvl rotaçãoDireita(NoAvl no) {
		NoAvl temp1, temp2;
		temp1 = no.getEsq();
		if (temp1.getFatorBalanceamento() == -1) {// Faz RD
			no.setEsq(temp1.getDir());
			temp1.setDir(no);
			no.setFatorBalanceamento(0);
			no = temp1;
		} else { // Faz RDD
			temp2 = temp1.getDir();
			temp1.setDir(temp2.getEsq());
			temp2.setEsq(temp1);
			no.setEsq(temp2.getDir());
			temp2.setDir(no);
			// Recalcula o FB do nó à direita na nova árvore
			if (temp2.getFatorBalanceamento() == -1)
				no.setFatorBalanceamento(1);
			else
				no.setFatorBalanceamento(0);
			// Recalcula o FB do nó à esquerda na nova árvore
			if (temp2.getFatorBalanceamento() == 1)
				temp1.setFatorBalanceamento(-1);
			else
				temp1.setFatorBalanceamento(0);
			no = temp2;
		}
		no.setFatorBalanceamento(0);
		this.h = false;
		return no;
	}

	private NoAvl rotaçãoEsquerda(NoAvl no) {
		NoAvl temp1, temp2;
		temp1 = no.getDir();
		if (temp1.getFatorBalanceamento() == 1) {
			no.setDir(temp1.getEsq());
			temp1.setEsq(no);
			no.setFatorBalanceamento(0);
			no = temp1;
		} else {
			temp2 = temp1.getEsq();
			temp1.setEsq(temp2.getDir());
			temp2.setDir(temp1);
			no.setDir(temp2.getEsq());
			temp2.setEsq(no);
			if (temp2.getFatorBalanceamento() == 1)
				no.setFatorBalanceamento(-1);
			else
				no.setFatorBalanceamento(0);
			if (temp2.getFatorBalanceamento() == -1)
				temp1.setFatorBalanceamento(1);
			else
				temp1.setFatorBalanceamento(0);
			no = temp2;
		}
		no.setFatorBalanceamento(0);
		this.h = false;
		return no;
	}

	// A pesquisa em AVL é igual à pesquisa em ABB
	public NoAvl pesquisar(String cpf) {
		return pesquisar(cpf, this.raiz);
	}

	private NoAvl pesquisar(String cpf, NoAvl no) {
		if (no == null) {
			return null;
		} else if (Objects.equals(cpf, no.getItem().getCpf())) {
			return no;
		} else if (cpf.compareTo(no.getItem().getCpf()) > 0) {
			return pesquisar(cpf, no.getDir());
		} else {
			return pesquisar(cpf, no.getEsq());
		}
	}

	public boolean pesquisarCpfArquivo(String caminho, int tamanho) {
		String result = "";
		// ler o arquivo

		try {
			File arquivo = new File(caminho);
			Scanner leitor = new Scanner(arquivo);

			while (leitor.hasNext()) {
				String cpf = leitor.nextLine();
				result += pesquisarPorCpf(cpf) + "\n";

			}

			leitor.close();
		} catch (Exception e) {
			System.out.println("Erro ao pesquisar cpfs do arquivo: " + e);
		}

		return gerarArqAVL(result, tamanho);
	}

	public String pesquisarPorCpf(String cpf) {
		String msg = "";
		double saldoTotal = 0;
		NoAvl result;
		result = pesquisar(cpf);

		if (result == null) {
			msg += "CPF: " + cpf + "\nINEXISTENTE!\n";
		} else {
			msg += "CPF: " + cpf;
			for (int c = 0; c < result.getContas().size(); c++) {
				Conta conta = result.getContas().get(c);
				msg += "\nagencia " + conta.getAgencia() + "   Conta " + conta.getConta() + "   Saldo "
						+ conta.getSaldo();
				saldoTotal += conta.getSaldo();
			}
			msg += "\nSaldo total: " + saldoTotal + "\n";
		}

		return msg;
	}

	private boolean gerarArqAVL(String dados, int tamanho) {
		String diretorio = "./src/arquivos/resultArvores";
		String nomeArquivo = "resultAVL" + tamanho + ".txt";

		// Caminho para o arquivo
		Path caminhoDiretorio = Paths.get(diretorio);
		Path caminhoArquivo = caminhoDiretorio.resolve(nomeArquivo);

		try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
			// Escreve o conteúdo no arquivo
			writer.write(dados);
			System.out.println("Arquivo criado com sucesso: " + caminhoArquivo.toAbsolutePath());

			return true;
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao criar o arquivo: " + e.getMessage());
			return false;
		}
	}

	public boolean carregarDados(String caminho) {
		try {
			File arquivo = new File(caminho);
			Scanner leitor = new Scanner(arquivo);

			String atributos[] = new String[3];

			while (leitor.hasNext()) {
				String data = leitor.nextLine();
				atributos = data.split(";");

				Conta conta = new Conta(Integer.parseInt((atributos[0])), Integer.parseInt((atributos[1])),
						Float.parseFloat(atributos[2]), (atributos[3]));

//				System.out.println("\n" + conta.toString());
				this.inserir(conta);
			}

			leitor.close();
			return true;

		} catch (FileNotFoundException error) {
			System.out.println("Erro ao carregar o arquivo: " + error);
			error.printStackTrace();
			return false;
		}
	}

	// Outros metodos
}
