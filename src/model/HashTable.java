package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTable {
	private ArrayList<ArrayList<Conta>> vetor;
	private int tamanho;

	public HashTable(int tamanho) {
		this.tamanho = tamanho;
		vetor = new ArrayList<>(tamanho);
		for (int i = 0; i < tamanho; i++) {
			vetor.add(i, new ArrayList<>());
		}
	}

	private int hashFunction(String cpf) {
		// Utilizando uma função de hash simples baseada no tamanho da tabela
		return Math.abs(cpf.hashCode() % tamanho);
	}

	public void inserir(Conta conta) {
		int index = hashFunction(conta.getCpf());

		while (true) {
			ArrayList<Conta> vetorAtual = vetor.get(index);

			if (vetorAtual.isEmpty()) {
				vetorAtual.add(conta);
				break;
			} else if (vetorAtual.get(0).getCpf().equals(conta.getCpf())) {
				vetorAtual.add(conta);
				break;
			} else {
				index = (index + 1) % tamanho;
				if (index >= tamanho) {
					index = 0;
				}
			}
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
				result += pesquisaPorCpf(cpf) + "\n";

			}

			leitor.close();
		} catch (Exception e) {
			System.out.println("Erro ao pesquisar cpfs do arquivo: " + e);
		}

		return gerarArqHash(result, tamanho);
	}

	public String pesquisaPorCpf(String cpf) {
		String msg = "";
		double saldoTotal = 0;
		ArrayList<Conta> result = arrayPorCpf(cpf);

		if (result == null) {
			msg += "CPF: " + cpf + "\nINEXISTENTE!\n";
		} else {
			msg += "CPF: " + cpf;
			for (int c = 0; c < result.size(); c++) {
				Conta conta = result.get(c);
				msg += "\nagencia " + conta.getAgencia() + "   Conta " + conta.getConta() + "   Saldo "
						+ conta.getSaldo();
				saldoTotal += conta.getSaldo();
			}
			msg += "\nSaldo total: " + saldoTotal + "\n";
		}

		return msg;
	}

	public ArrayList<Conta> arrayPorCpf(String cpf) {
		int index = hashFunction(cpf);

		while (true) {
			ArrayList<Conta> vetorAtual = vetor.get(index);

			if (!vetorAtual.isEmpty()) {
				if (vetorAtual.get(0).getCpf().equals(cpf)) {
					return vetorAtual;
				}
			}

			index = (index + 1) % tamanho;
			if (index == hashFunction(cpf)) {
				break; // Se voltar ao ponto inicial, encerra a busca
			}
		}

		return null; // Retorna null se não encontrar a conta com o CPF especificado
	}
	
	private boolean gerarArqHash(String dados, int tamanho) {
		String diretorio = "./src/arquivos/hashTable";
		String nomeArquivo = "resultHash" + tamanho + ".txt";

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

		} catch (FileNotFoundException error) {
			System.out.println("Erro ao carregar o arquivo: " + error);
			error.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "HashTable [vetor=" + vetor + ", tamanho=" + tamanho + "]";
	}

}