package com.br.banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.br.banco.interfaces.ListarContas;
import com.br.banco.metodos.Listar;
import com.br.banco.metodos.Menu;
import com.br.banco.objetos.Conta;
import com.br.banco.objetos.ContaCorrente;
import com.br.banco.objetos.ContaPoupanca;
import com.br.banco.objetos.Operacao;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int input = 1;
		String nome;
		int tipoConta;
		double saldo = 0;
		String banco;
		double limite = 0;
		Listar listar = new Listar();
		List<Conta> contasList = new ArrayList<Conta>();

		while (input != 0) {
			Menu.montarMenuPrincipal();
			input = scan.nextInt();

			if (input == 1) {
				System.out.println("Digite seu nome: ");
				nome = scan.next();

				System.out.println("tipo de conta deseja cadastrar: [0: conta corrente]/[1: poupança] ");
				tipoConta = scan.nextInt();
				while (tipoConta < 0 || tipoConta > 1) {
					System.out.println("Digite apenas 0 ou 1");
					tipoConta = scan.nextInt();
				}

				System.out.println("saldo da conta: ");
				try {
					saldo = scan.nextDouble();
					while (saldo < 0) {
						System.out.println("seu saldo não pode ser negativo, digite um valor valido: ");
						saldo = scan.nextDouble();
					}
				} catch (Exception e) {
					System.out.println("erro");
					saldo = scan.nextDouble();
				}
				System.out.println("nome da agência: ");
				banco = scan.next();

				if (tipoConta == 0) {
					System.out.println("limite da conta: ");
					limite = scan.nextDouble();
				}

				if (tipoConta == 0) {
					ContaCorrente c1 = new ContaCorrente(0, nome, tipoConta, saldo, banco, limite);
					System.out.println(c1.getLimite());
					contasList.add(c1);
				} else {
					ContaPoupanca c1 = new ContaPoupanca(0, nome, tipoConta, saldo, banco);
					contasList.add(c1);
				}
			}

			if (input == 2) {
				if (contasList.size() > 0) {
					int entrada = 1;
					while (entrada != 0) {
						Menu.montarMenuCaixa();
						entrada = scan.nextInt();

						if (entrada == 1) {
							System.out.println("digite o id da conta: ");
							int id = scan.nextInt();

							System.out.println(
									"qual valor deseja pagar? \nsaldo disponivel: " + contasList.get(id).getSaldo());
							if (contasList.get(id) instanceof ContaCorrente) {
								System.out.println(
										"\nlimite disponivel: " + ((ContaCorrente) contasList.get(id)).getLimite());
							}
							float valor = scan.nextFloat();

							CaixaEletronico.pagar(contasList.get(id), valor);

						}

						if (entrada == 2) {
							System.out.println("digite o id da primeira conta: ");
							int id1 = scan.nextInt();

							System.out.println("digite o id da segunda conta: ");
							int id2 = scan.nextInt();

							System.out.println("qual valor deseja trasferir? \nsaldo disponivel: "
									+ contasList.get(id1).getSaldo());
							if (contasList.get(id1) instanceof ContaCorrente) {
								System.out.println(
										"\nlimite disponivel: " + ((ContaCorrente) contasList.get(id1)).getLimite());
							}
							float valor = scan.nextFloat();

							CaixaEletronico.pagar(contasList.get(id1), contasList.get(id2), valor);
						}
					}
				} else {
					System.out.println("nenhuma conta cadastrada");
				}
			}

			if (input == 3) {
				listar.listarTodasAsContasComOperacoes(contasList);
			}

			if (input == 4) {
				listar.listarTodasContas(contasList);
			}

			if (input == 5) {
				List<ContaCorrente> contasCorrenteList = separaContasCorrente(contasList);

				listar.listarContasCorrentes(contasCorrenteList);
			}

			if (input == 6) {
				List<ContaPoupanca> contasPoupancaList = separaContasPoupanca(contasList);

				listar.listarContasPoupanca(contasPoupancaList);
			}

			if (input == 7) {
				ContaCorrente conta1 = new ContaCorrente(0, "teste", 0, 500, "itau", 500);
				ContaPoupanca conta2 = new ContaPoupanca(0, "teste2", 1, 500, "hscb");
				ContaPoupanca conta3 = new ContaPoupanca(0, "teste3", 1, 500, "santander");
				ContaCorrente conta4 = new ContaCorrente(0, "teste4", 0, 500, "nubank", 500);
				contasList.add(conta1);
				contasList.add(conta2);
				contasList.add(conta3);
				contasList.add(conta4);
			}
		}

	}

	public static List<ContaCorrente> separaContasCorrente(List<Conta> contasList) {
		List<ContaCorrente> contasCorrente = new ArrayList<>();

		for (ContaCorrente conta : contasCorrente) {
			if (conta instanceof ContaCorrente) {
				contasCorrente.add(conta);
			}
		}

		return contasCorrente;
	}

	public static List<ContaPoupanca> separaContasPoupanca(List<Conta> contasList) {
		List<ContaPoupanca> contasPoupanca = new ArrayList<>();

		for (ContaPoupanca conta : contasPoupanca) {
			if (conta instanceof ContaPoupanca) {
				contasPoupanca.add(conta);
			}
		}

		return contasPoupanca;
	}
}
