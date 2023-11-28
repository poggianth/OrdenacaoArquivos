package main;

import java.util.ArrayList;
import java.util.Scanner;

import arvoreABB.ArvoreAbb;
import arvoreAVL.ArvoreAvl;
import model.VetConta;

public class Menu {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int op;

		do {
			System.out.println("\nEscolha uma opção:" + "\n");
			System.out.println("[ 1 ] Executar QuickSort");
			System.out.println("[ 2 ] Executar ShellSort");
			System.out.println("[ 3 ] Rodar Árvore ABB");
			System.out.println("[ 4 ] Rodar Árvore AVL");
			// System.out.println("[ 5 ] Rodar Hashing");
			System.out.println("[ 0 ] Sair\n");

			System.out.print("Digite o número da opção desejada: ");
			op = scan.nextInt();

			switch (op) {
			case 1:
				System.out.println("Você escolheu Rodar QuickSort");

				try {
					VetConta vet500 = new VetConta(500);
					vet500.carregarDados("./src/arquivos/conta500.txt");
					vet500.quicksort();
					vet500.gerarArquivo("quicksort");

					VetConta vet1000 = new VetConta(1000);
					vet1000.carregarDados("./src/arquivos/conta1000.txt");
					vet1000.quicksort();
					vet1000.gerarArquivo("quicksort");

					VetConta vet5000 = new VetConta(5000);
					vet5000.carregarDados("./src/arquivos/conta5000.txt");
					vet5000.quicksort();
					vet5000.gerarArquivo("quicksort");

					VetConta vet10000 = new VetConta(10000);
					vet10000.carregarDados("./src/arquivos/conta10000.txt");
					vet10000.quicksort();
					vet10000.gerarArquivo("quicksort");

					VetConta vet50000 = new VetConta(500000);
					vet50000.carregarDados("./src/arquivos/conta50000.txt");
					vet50000.quicksort();
					vet50000.gerarArquivo("quicksort");

				} catch (Exception e) {
					System.out.println("Erro ao executar o quicksort: " + e);
				}

				break;
			case 2:
				System.out.println("Você escolheu Rodar ShellSort");

				try {
					VetConta vet500 = new VetConta(500);
					vet500.carregarDados("./src/arquivos/conta500.txt");
					vet500.shellsort();
					vet500.gerarArquivo("shellsort");

					VetConta vet1000 = new VetConta(1000);
					vet1000.carregarDados("./src/arquivos/conta1000.txt");
					vet1000.shellsort();
					vet1000.gerarArquivo("shellsort");

					VetConta vet5000 = new VetConta(5000);
					vet5000.carregarDados("./src/arquivos/conta5000.txt");
					vet5000.shellsort();
					vet5000.gerarArquivo("shellsort");

					VetConta vet10000 = new VetConta(10000);
					vet10000.carregarDados("./src/arquivos/conta10000.txt");
					vet10000.shellsort();
					vet10000.gerarArquivo("shellsort");

					VetConta vet50000 = new VetConta(500000);
					vet50000.carregarDados("./src/arquivos/conta50000.txt");
					vet50000.shellsort();
					vet50000.gerarArquivo("shellsort");

				} catch (Exception e) {
					System.out.println("Erro ao executar o shellsort: " + e);
				}
				break;
			case 3:
				System.out.println("Você escolheu Rodar Árvore ABB");
				ArrayList<String> nomeArquivosAbb = new ArrayList<String>();
				nomeArquivosAbb.add("conta500");
				nomeArquivosAbb.add("conta1000");
				nomeArquivosAbb.add("conta5000");
				nomeArquivosAbb.add("conta10000");
				nomeArquivosAbb.add("conta50000");

				for (int c = 0; c < nomeArquivosAbb.size(); c++) {
					int tamanhoArq = Integer.parseInt(nomeArquivosAbb.get(c).split("conta")[1]);
					try {
						ArvoreAbb arvoreAbb = new ArvoreAbb();
						if (arvoreAbb.carregarDados("./src/arquivos/" + nomeArquivosAbb.get(c) + ".txt")) {
							System.out.println("Árvore carregada!");
						}

						System.out.println(
								"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
						System.out.println(
								"\n--------------------------------- PESQUISAR POR CPFs ---------------------------------");

						arvoreAbb.pesquisarCpfArquivo("./src/arquivos/CPF.txt", tamanhoArq);

						System.out.println(
								"--------------------------------------------------------------------------------------\n");
					} catch (Exception e) {
						System.out.println("Erro ao carregar a árvore ABB: " + e);
					}
				}

				break;
			case 4:
				System.out.println("Você escolheu Rodar Árvore AVL");

				ArrayList<String> nomeArquivosAvl = new ArrayList<String>();
				nomeArquivosAvl.add("conta500");
				nomeArquivosAvl.add("conta1000");
				nomeArquivosAvl.add("conta5000");
				nomeArquivosAvl.add("conta10000");
				nomeArquivosAvl.add("conta50000");

				for (int c = 0; c < nomeArquivosAvl.size(); c++) {
					int tamanhoArq = Integer.parseInt(nomeArquivosAvl.get(c).split("conta")[1]);

					try {
						ArvoreAvl arvoreAvl = new ArvoreAvl();
						if (arvoreAvl.carregarDados("./src/arquivos/" + nomeArquivosAvl.get(c) + ".txt")) {
							System.out.println("Árvore carregada!");
						}

						System.out.println(
								"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
						System.out.println(
								"\n--------------------------------- PESQUISAR POR CPFs ---------------------------------");

						arvoreAvl.pesquisarCpfArquivo("./src/arquivos/CPF.txt", tamanhoArq);

						System.out.println(
								"--------------------------------------------------------------------------------------\n");
					} catch (Exception e) {
						System.out.println("Erro ao carregar a árvore ABB: " + e);
					}
				}

				break;
//			case 5:
//				System.out.println("Você escolheu Rodar Hashing");
//				// new InitHash(conta500);
//				break;
			case 0:
				System.out.println("Fim do programa.");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		} while (op != 0);

		scan.close();
	}

}
