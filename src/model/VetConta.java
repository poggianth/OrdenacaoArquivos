package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class VetConta {
	private Conta[] vetor;
	private int quant;

	public VetConta(int tamanho) {
		this.vetor = new Conta[tamanho];
		this.quant = 0;
	}

	public Conta getConta(int posicao) {
		return this.vetor[posicao];
	}

	public void inserir(Conta conta) {
		if (this.quant < vetor.length) {
			vetor[this.quant] = conta;
			this.quant++;
		} else {
			// Se o vetor estiver cheio, você pode considerar redimensioná-lo ou lançar uma
			// exceção.
			System.out.println("O vetor está cheio. Não é possível adicionar mais itens.");
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

		} catch (FileNotFoundException error) {
			System.out.println("Erro ao carregar o arquivo: " + error);
			error.printStackTrace();
			return false;
		}

		return true;
	}

	public void quicksort() {
		ordena(0, this.quant - 1);
	}

	private void ordena(int esq, int dir) {
		int i = esq, j = dir;
		Conta pivo = this.vetor[(i + j) / 2];
		do {
			while (compara(this.vetor[i], pivo) < 0)
				i++;
			while (compara(this.vetor[j], pivo) > 0)
				j--;
			if (i <= j) {
				Conta temp = this.vetor[i];
				this.vetor[i] = this.vetor[j];
				this.vetor[j] = temp;
				i++;
				j--;
			}
		} while (i <= j);
		if (esq < j)
			ordena(esq, j);
		if (dir > i)
			ordena(i, dir);
	}

	private int compara(Conta c1, Conta c2) {
	    int resultadoCpf = c1.getCpf().compareTo(c2.getCpf());
	    if (resultadoCpf != 0) {
	        return resultadoCpf;
	    } else {
	        // Se os CPFs são iguais, compare por agência
	        return Integer.compare(c1.getAgencia(), c2.getAgencia());
	    }
	}

	public void shellsort() {
	    int i, j, h;
	    Conta temp;
	    h = 1;
	    do {
	        h = 3 * h + 1;
	    } while (h < this.quant);

	    do {
	        h = h / 3;
	        for (i = h; i < this.quant; i++) {
	            temp = this.vetor[i];
	            j = i;
	            while (j - h >= 0 && compara(this.vetor[j - h], temp) > 0) {
	                this.vetor[j] = this.vetor[j - h];
	                j -= h;
	            }
	            this.vetor[j] = temp;
	        }
	    } while (h != 1);
	}
	
	public void gerarArquivo(String metodo) {
		String diretorio = "./src/arquivos/" + metodo;
		String nomeArquivo = "ordena" + metodo + this.quant + ".txt";
		String conteudo = "";

		for (Conta conta : this.vetor) {
			if (conta != null) {
				conteudo += conta.getAgencia() + ";" + conta.getConta() + ";" + conta.getSaldo() + ";" + conta.getCpf()
						+ "\n";
			}
		}

		// Caminho para o arquivo
		Path caminhoDiretorio = Paths.get(diretorio);
		Path caminhoArquivo = caminhoDiretorio.resolve(nomeArquivo);

		try (BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo)) {
			// Escreve o conteúdo no arquivo
			writer.write(conteudo);
			System.out.println("Arquivo criado com sucesso: " + caminhoArquivo.toAbsolutePath());
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao criar o arquivo: " + e.getMessage());
		}
	}

	public String toString() {
		String msg = "";

		for (Conta conta : vetor) {
			msg += conta.toString() + "\n";
		}

		return msg + " quant: " + this.quant;
	}

}
