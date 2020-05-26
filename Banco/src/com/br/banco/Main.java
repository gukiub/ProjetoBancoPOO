package com.br.banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
		int tipoConta = 0;
		double saldo = 0;
		String banco = null;
		double limite = 0;
		Listar listar = new Listar();
		List<Conta> contasList = new ArrayList<Conta>();

		// faz com que o menu fique em loop até o input seja = 0, ela é inicializada em
		// 1
		while (input != 0) {
			// puxa o metodo que monta o menu de uma classe estática
			Menu.montarMenuPrincipal();
			input = scan.nextInt();

			// faz o cadastro do usuário
			if (input == 1) {
				System.out.println("Digite seu nome: ");
				nome = scan.next();

				System.out.println("tipo de conta deseja cadastrar: [0: conta corrente]/[1: poupança] ");
				try {
					tipoConta = scan.nextInt();
					while (tipoConta < 0 || tipoConta > 1) {
						System.out.println("Digite apenas 0 ou 1");
						tipoConta = scan.nextInt();
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				System.out.println("saldo da conta: ");
				try {
					saldo = scan.nextDouble();
					while (saldo < 0) {
						System.out.println("seu saldo não pode ser negativo, digite um valor valido: ");
						saldo = scan.nextDouble();
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("nome da agência: ");
				try {
					banco = scan.next();
				} catch (Exception e) {
					System.out.println(e);
				}

				if (tipoConta == 0) {
					System.out.println("limite da conta: ");
					try {
						limite = scan.nextDouble();
					} catch (Exception e) {
						System.out.println(e);
					}
				}

				// aqui é onde é definido o tipo de objeto que sairá do metodo
				if (tipoConta == 0) {
					ContaCorrente c1 = new ContaCorrente(0, nome, tipoConta, saldo, banco, limite);
					System.out.println("Para realizar transações utilize o id = " + c1.getId());
					contasList.add(c1);
				} else {
					ContaPoupanca c1 = new ContaPoupanca(0, nome, tipoConta, saldo, banco);
					contasList.add(c1);
				}
			}

			// caixa eletronico
			if (input == 2) {
				// verifica há alguma conta cadastrada
				if (contasList.size() > 0) {
					int entrada = 1;
					while (entrada != 0) {
						Menu.montarMenuCaixa();
						entrada = scan.nextInt();

						if (entrada == 1) {
							System.out.println("digite o id da conta: ");

							try {
								int id = scan.nextInt() - 1;

								System.out.println("qual valor deseja pagar? \nsaldo disponivel: "
										+ contasList.get(id).getSaldo());

								// verifica se contalista é uma instancia de conta corrente. caso verdadeiro ele
								// pega o limite

								if (contasList.get(id) instanceof ContaCorrente) {
									System.out.println(
											"limite disponivel: " + ((ContaCorrente) contasList.get(id)).getLimite());
								}
								float valor = scan.nextFloat();

								// chama o metodo pagar de caixa eletronico passando o objeto escolhido pelo
								// usuario e valor
								CaixaEletronico.pagar(contasList.get(id), valor);
							} catch (Exception e) {
								System.out.println("id inválido, digite um id valido");
							}
						}

						if (entrada == 2) {
							try {
								System.out.println("digite o id da primeira conta: ");
								int id1 = scan.nextInt() - 1;

								System.out.println("digite o id da segunda conta: ");
								int id2 = scan.nextInt() - 1;

								System.out.println("qual valor deseja transferir? \nsaldo disponivel: "
										+ contasList.get(id1).getSaldo());
								if (contasList.get(id1) instanceof ContaCorrente) {
									System.out.println(
											"limite disponivel: " + ((ContaCorrente) contasList.get(id1)).getLimite());
								}
								float valor = scan.nextFloat();

								CaixaEletronico.pagar(contasList.get(id1), contasList.get(id2), valor);
							} catch (Exception e) {
								System.out.println("id invalido, digite um id valido");
							}
						}
					}
				} else {
					System.out.println("nenhuma conta cadastrada");
				}
			}

			if (input == 3) {
				// chama o metodo que lista as contas passando a lista de contas
				listar.listarTodasAsContasComOperacoes(contasList);
			}

			if (input == 4) {
				listar.listarTodasContas(contasList);
			}

			if (input == 5) {
				// chama um metodo que verifica a instancia de correntes e retorna na lista
				// apenas elas
				List<ContaCorrente> contasCorrenteList = separaContasCorrente(contasList);

				// após filtrar faz o sout das contas
				listar.listarContasCorrentes(contasCorrenteList);
			}

			if (input == 6) {
				List<ContaPoupanca> contasPoupancaList = separaContasPoupanca(contasList);

				listar.listarContasPoupanca(contasPoupancaList);
			}

			// meotodo que gera contas para teste
			if (input == 7) {
				Random rn = new Random();
				int saldoAleatorio = rn.nextInt(1000);
				int limiteAleatorio = rn.nextInt(1000);

				saldoAleatorio = rn.nextInt(1000);
				limiteAleatorio = rn.nextInt(1000);

				ContaCorrente conta1 = new ContaCorrente(0, "teste", 0, saldoAleatorio, "itau", limiteAleatorio);

				saldoAleatorio = rn.nextInt(1000);
				limiteAleatorio = rn.nextInt(1000);

				ContaPoupanca conta2 = new ContaPoupanca(0, "teste2", 1, saldoAleatorio, "hscb");

				saldoAleatorio = rn.nextInt(1000);
				limiteAleatorio = rn.nextInt(1000);

				ContaPoupanca conta3 = new ContaPoupanca(0, "teste3", 1, saldoAleatorio, "santander");

				saldoAleatorio = rn.nextInt(1000);
				limiteAleatorio = rn.nextInt(1000);
				ContaCorrente conta4 = new ContaCorrente(0, "teste4", 0, saldoAleatorio, "nubank", limiteAleatorio);
				contasList.add(conta1);
				contasList.add(conta2);
				contasList.add(conta3);
				contasList.add(conta4);
			}
		}

	}

	// metodo para filtrar contas corrente
	public static List<ContaCorrente> separaContasCorrente(List<Conta> contasList) {
		// instancia a lista que vai ser usada como retorno do metodo
		List<ContaCorrente> contasCorrente = new ArrayList<>();

		// percorre a lista de contas
		for (Conta conta : contasList) {
			// sempre que o for rodar verifica se conta é do tipo conta corrente e então
			// adiciona na lista de contas
			// fazendo um cast para o tipo contaCorrente
			if (conta instanceof ContaCorrente) {
				contasCorrente.add((ContaCorrente) conta);
			}
		}

		// após filtrar retorna a lista
		return contasCorrente;
	}

	// metodo para filtrar contas poupança
	public static List<ContaPoupanca> separaContasPoupanca(List<Conta> contasList) {
		List<ContaPoupanca> contasPoupanca = new ArrayList<>();

		for (Conta conta : contasList) {
			if (conta instanceof ContaPoupanca) {
				contasPoupanca.add((ContaPoupanca) conta);
			}
		}

		return contasPoupanca;
	}

}