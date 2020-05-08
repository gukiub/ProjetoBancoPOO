package com.br.banco;

import com.br.banco.objetos.Conta;
import com.br.banco.objetos.ContaCorrente;
import com.br.banco.objetos.ContaPoupanca;
import com.br.banco.objetos.Operacao;

public class CaixaEletronico {
	// metodo de transferencia de uma conta para outra
	public static void pagar(Conta origem, Conta destino, float valor) {

		// calcula quanto vai extrair do limite
		double aux = origem.getSaldo() - valor;
		
		// verifica se a origem � do tipo poupan�a
		if (origem instanceof ContaPoupanca) {
			// variaveis para rollback caso a opera��o n�o ocorra bem
			double valorAnteriorDestino = destino.getSaldo();
			double valorAnteriorOrigem = origem.getSaldo();

			// verifica se o saldo menos o valor � negativo
			if (origem.getSaldo() - valor < 0) {
				System.out.println("saldo insuficiente");

				// caso d� certo esse � o caminho default
			} else {
				try {
					// inicia a transa��o
					origem.setSaldo(origem.getSaldo() - valor);
					
					if(destino instanceof ContaCorrente) {
						double valorBackup = valor;
						
						// verifica se o limite atual � diferente do limite total da conta
						if(((ContaCorrente) destino).getLimite() != ((ContaCorrente) destino).getLimiteTotal()) {
							// divida atual pega a diferen�a entre a o limite atual e o total
							double dividaAtual = Math.abs(((ContaCorrente) destino).getLimiteTotal() - ((ContaCorrente) destino).getLimite());
							
							// atualiza a divida atual com o valor que recebeu
							
							dividaAtual -= valorBackup;
							
							if(dividaAtual < 0) { // verifica se o valor recebido � maior que a divida atual
								valorBackup = (float) dividaAtual; // valor que sobrou da opera��o 
								
								destino.setSaldo(destino.getSaldo() + Math.abs(valorBackup)); // coloca o valor recebido em saldo
								((ContaCorrente) destino).setLimite(((ContaCorrente) destino).getLimiteTotal()); // quita a divida do limite e converte ela para o valor total
							}
							else {
								((ContaCorrente) destino).setLimite(((ContaCorrente) destino).getLimite() + valorBackup);
							}
						}
						else {
							System.out.println("a pessoa n�o possui divida");
							destino.setSaldo(valorBackup + destino.getSaldo());
						}
						
					}
						System.out.println("valor da variavel valor: " + valor);
					if (valor > 0) {
						// seta as opera��es
						origem.getOperacoes().add(new Operacao(valor, false));
						destino.getOperacoes().add(new Operacao(valor, false));
					}
				} catch (Exception e) {
					// caso algo d� errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					destino.setSaldo(valorAnteriorDestino);
					System.out.println(
							"infelizmente n�o foi possivel realizar a transa��o \ntente novamente mais tarde.");
				}

			}
		}

		// verifica se o saldo da conta corrente � negativo essa verifica��o s� acontece
		// por conta do limite
		else if ((origem.getSaldo() + ((ContaCorrente) origem).getLimite()) - valor < 0) {
			System.out.println("saldo insuficiente");
		}

		// caminho onde a conta corrente usa do limite
		else {
			try {
				if (aux < 0) {
					// nesse caso o saldo para n�o ficar negativo � setado como zero
					origem.setSaldo(0);
					
					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);
					if (valor > 0) {
						// adiciona a opera��o na lista
						origem.getOperacoes().add(new Operacao(valor, false));
					}
				} else {
					// caso onde a conta n�o possui limite
					origem.setSaldo(origem.getSaldo() - valor);
					if(valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, false));
					}
				}
				// depois das verifica��es envia o valor para a conta destino
				destino.setSaldo(destino.getSaldo() + valor);
				if(valor > 0) {
					destino.getOperacoes().add(new Operacao(valor, false));
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	
	
	// metodo de pagamento de boleto
	public static void pagar(Conta origem, float valor) {
		// verifica se origem � uma instancia de poupan�a
		if (origem instanceof ContaPoupanca) {

			// variaveis para rollback caso a opera��o n�o ocorra bem
			double valorAnteriorOrigem = origem.getSaldo();

			// verifica se o saldo menos o valor � negativo
			if (origem.getSaldo() - valor < 0) {
				System.out.println("saldo insuficiente");
			} else {
				try {
					// inicia a transa��o
					origem.setSaldo(origem.getSaldo() - valor);

					if (valor > 0) {
						// para para a opera��o se ela � uma transferencia ou pagamento
						origem.getOperacoes().add(new Operacao(valor, false));
					}
				} catch (Exception e) {
					// caso algo d� errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					System.out.println(
							"infelizmente n�o foi possivel realizar a transa��o \ntente novamente mais tarde.");
				}

			}
		}

		// situa��o onde o saldo � insuficiente, somente contas corrente caem nesse
		// metodo
		else if ((origem.getSaldo() + ((ContaCorrente) origem).getLimite()) - valor < 0) {
			System.out.println("saldo insuficiente");
		}

		// caso tudo d� certo
		else {
			try {
				// faz o calculo para obter a diferen�a, para extrair do limite
				double aux = origem.getSaldo() - valor;

				if (aux < 0) {
					// nesse caso o saldo para n�o ficar negativo � setado como zero
					origem.setSaldo(0);

					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);

					if (valor > 0) {
						// adiciona a opera��o na lista
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				} else {
					// caso onde a conta n�o possui limite
					origem.setSaldo(origem.getSaldo() - valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
