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
		
		// verifica se a origem é do tipo poupança
		if (origem instanceof ContaPoupanca) {
			// variaveis para rollback caso a operação não ocorra bem
			double valorAnteriorDestino = destino.getSaldo();
			double valorAnteriorOrigem = origem.getSaldo();

			// verifica se o saldo menos o valor é negativo
			if (origem.getSaldo() - valor < 0) {
				System.out.println("saldo insuficiente");

				// caso dê certo esse é o caminho default
			} else {
				try {
					// inicia a transação
					origem.setSaldo(origem.getSaldo() - valor);
					
					if(destino instanceof ContaCorrente) {
						double valorBackup = valor;
						
						// verifica se o limite atual é diferente do limite total da conta
						if(((ContaCorrente) destino).getLimite() != ((ContaCorrente) destino).getLimiteTotal()) {
							// divida atual pega a diferença entre a o limite atual e o total
							double dividaAtual = Math.abs(((ContaCorrente) destino).getLimiteTotal() - ((ContaCorrente) destino).getLimite());
							
							// atualiza a divida atual com o valor que recebeu
							
							dividaAtual -= valorBackup;
							
							if(dividaAtual < 0) { // verifica se o valor recebido é maior que a divida atual
								valorBackup = (float) dividaAtual; // valor que sobrou da operação 
								
								destino.setSaldo(destino.getSaldo() + Math.abs(valorBackup)); // coloca o valor recebido em saldo
								((ContaCorrente) destino).setLimite(((ContaCorrente) destino).getLimiteTotal()); // quita a divida do limite e converte ela para o valor total
							}
							else {
								((ContaCorrente) destino).setLimite(((ContaCorrente) destino).getLimite() + valorBackup);
							}
						}
						else {
							System.out.println("a pessoa não possui divida");
							destino.setSaldo(valorBackup + destino.getSaldo());
						}
						
					}
						System.out.println("valor da variavel valor: " + valor);
					if (valor > 0) {
						// seta as operações
						origem.getOperacoes().add(new Operacao(valor, false));
						destino.getOperacoes().add(new Operacao(valor, false));
					}
				} catch (Exception e) {
					// caso algo dê errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					destino.setSaldo(valorAnteriorDestino);
					System.out.println(
							"infelizmente não foi possivel realizar a transação \ntente novamente mais tarde.");
				}

			}
		}

		// verifica se o saldo da conta corrente é negativo essa verificação só acontece
		// por conta do limite
		else if ((origem.getSaldo() + ((ContaCorrente) origem).getLimite()) - valor < 0) {
			System.out.println("saldo insuficiente");
		}

		// caminho onde a conta corrente usa do limite
		else {
			try {
				if (aux < 0) {
					// nesse caso o saldo para não ficar negativo é setado como zero
					origem.setSaldo(0);
					
					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);
					if (valor > 0) {
						// adiciona a operação na lista
						origem.getOperacoes().add(new Operacao(valor, false));
					}
				} else {
					// caso onde a conta não possui limite
					origem.setSaldo(origem.getSaldo() - valor);
					if(valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, false));
					}
				}
				// depois das verificações envia o valor para a conta destino
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
		// verifica se origem é uma instancia de poupança
		if (origem instanceof ContaPoupanca) {

			// variaveis para rollback caso a operação não ocorra bem
			double valorAnteriorOrigem = origem.getSaldo();

			// verifica se o saldo menos o valor é negativo
			if (origem.getSaldo() - valor < 0) {
				System.out.println("saldo insuficiente");
			} else {
				try {
					// inicia a transação
					origem.setSaldo(origem.getSaldo() - valor);

					if (valor > 0) {
						// para para a operação se ela é uma transferencia ou pagamento
						origem.getOperacoes().add(new Operacao(valor, false));
					}
				} catch (Exception e) {
					// caso algo dê errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					System.out.println(
							"infelizmente não foi possivel realizar a transação \ntente novamente mais tarde.");
				}

			}
		}

		// situação onde o saldo é insuficiente, somente contas corrente caem nesse
		// metodo
		else if ((origem.getSaldo() + ((ContaCorrente) origem).getLimite()) - valor < 0) {
			System.out.println("saldo insuficiente");
		}

		// caso tudo dê certo
		else {
			try {
				// faz o calculo para obter a diferença, para extrair do limite
				double aux = origem.getSaldo() - valor;

				if (aux < 0) {
					// nesse caso o saldo para não ficar negativo é setado como zero
					origem.setSaldo(0);

					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);

					if (valor > 0) {
						// adiciona a operação na lista
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				} else {
					// caso onde a conta não possui limite
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
