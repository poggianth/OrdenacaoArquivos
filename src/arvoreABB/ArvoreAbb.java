package arvoreABB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import model.Conta;
import model.VetConta;

public class ArvoreAbb {
	private NoAbb raiz;
	private int quant;

	public ArvoreAbb() {
		this.quant = 0;
		this.raiz = null;
	}

	public int getQuant() {
		return quant;
	}

	public boolean inserir(Conta conta) {
		NoAbb aux = pesquisar(conta.getCpf());
		if (aux != null) {
			aux.getContas().add(conta);
			return false;
		} else {
			this.raiz = inserir(conta, this.raiz);
			return true;
		}
	}

	private NoAbb inserir(Conta conta, NoAbb no) {
		if (no == null) {
			no = new NoAbb(conta);
			this.quant++;
		} else if (conta.getCpf().compareTo(no.getItem().getCpf()) > 0) {
			no.setDir(inserir(conta, no.getDir()));
		} else if (conta.getCpf().equals(no.getItem().getCpf())) {
			no.inserirEmContas(conta);
		} else {
			no.setEsq(inserir(conta, no.getEsq()));
		}
		return no;
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
				this.quant++;
			}

			leitor.close();
			return true;

		} catch (FileNotFoundException error) {
			System.out.println("Erro ao carregar o arquivo: " + error);
			error.printStackTrace();
			return false;
		}
	}

	public NoAbb pesquisar(String cpf) {
		return pesquisar(cpf, this.raiz);
	}

	private NoAbb pesquisar(String cpf, NoAbb no) {
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

	public VetConta CamCentral() {
		VetConta vetor = new VetConta(quant);
		return (fazCamCentral(this.raiz, vetor));
	}

	private VetConta fazCamCentral(NoAbb no, VetConta vetorContas) {
		if (no != null) {
			vetorContas = this.fazCamCentral(no.getEsq(), vetorContas);
			vetorContas.inserir(no.getItem());
			vetorContas = this.fazCamCentral(no.getDir(), vetorContas);
		}
		return vetorContas;
	}

	public ArvoreAbb balancear() {
		ArvoreAbb arv = new ArvoreAbb();
		VetConta vetor = CamCentral();
		balancear(vetor, arv, 0, quant - 1);
		return arv;
	}

	private void balancear(VetConta vetor, ArvoreAbb arv, int inicio, int fim) {
		int meio;
		if (inicio <= fim) {
			meio = (inicio + fim) / 2;
			arv.inserir(vetor.getConta(meio));
			// balancear a parte esquerda do vetor
			balancear(vetor, arv, inicio, meio - 1);
			// balancear a parte direita do vetor
			balancear(vetor, arv, meio + 1, fim);
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

		return gerarArqABB(result, tamanho);
	}

	public String pesquisarPorCpf(String cpf) {
		String msg = "";
		double saldoTotal = 0;
		NoAbb result;
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

	private boolean gerarArqABB(String dados, int tamanho) {
		String diretorio = "./src/arquivos/resultArvores";
		String nomeArquivo = "resultABB" + tamanho + ".txt";

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

	// Método para exibir a árvore em ordem
	public void exibirEmOrdem() {
		exibirEmOrdemRec(raiz);
	}

	private void exibirEmOrdemRec(NoAbb conta) {
		if (conta != null) {
			exibirEmOrdemRec(conta.getEsq());
			System.out.println(conta.toString());
			exibirEmOrdemRec(conta.getDir());
		}
	}
}
