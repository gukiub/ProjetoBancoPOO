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
			// situação onde o valor debitado é maior que o saldo
			if (aux < 0) {
				System.out.println("saldo insuficiente");
			} else { // caso o valor seja valido é debitado e sobe uma nova operação
				origem.setSaldo(aux);
				destino.setSaldo(destino.getSaldo() + valor);
				if (valor > 0) {
					origem.getOperacoes().add(new Operacao(valor, false));
					destino.getOperacoes().add(new Operacao(valor, false));
				}
			}

		} else { // caso seja uma conta corrente
			double limiteAux = (((ContaCorrente) origem).getLimite() + origem.getSaldo()); // soma o total disponivel na
																							// conta

			if (valor > limiteAux) { // se o valor for maior que o saldo + o limite
				System.out.println("saldo insuficiente");
			} else { // caso não seja
				if (valor < origem.getSaldo()) { // se o valor não vai negativar o a conta
					origem.setSaldo(origem.getSaldo() - valor);
					destino.setSaldo(destino.getSaldo() + valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, false));
						destino.getOperacoes().add(new Operacao(valor, false));
					}
				} else { // cobre todas as outras situações
					origem.setSaldo(origem.getSaldo() - valor);
					destino.setSaldo(destino.getSaldo() + valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, false));
						destino.getOperacoes().add(new Operacao(valor, false));
					}
				}
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
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				} catch (Exception e) {
					// caso algo dê errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					System.out.println(
							"infelizmente não foi possivel realizar a transação \ntente novamente mais tarde.");
				}

			}
		}

		else { // caso seja uma conta corrente
			double limiteAux = (((ContaCorrente) origem).getLimite() + origem.getSaldo()); // realiza a soma do saldo +
																							// limite

			if (valor > limiteAux) { // caso o valor seja superior do que o valor do saldo + limite
				System.out.println("saldo insuficiente");
			} else { // caso não for
				if (valor < origem.getSaldo()) { // se o valor for menor que o saldo
					origem.setSaldo(origem.getSaldo() - valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				} else { // para todas as outras situações
					origem.setSaldo(origem.getSaldo() - valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				}
			}
		}
	}
}
