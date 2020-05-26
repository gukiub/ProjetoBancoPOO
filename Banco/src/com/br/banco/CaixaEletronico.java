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
			// situa��o onde o valor debitado � maior que o saldo
			if (aux < 0) {
				System.out.println("saldo insuficiente");
			} else { // caso o valor seja valido � debitado e sobe uma nova opera��o
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
			} else { // caso n�o seja
				if (valor < origem.getSaldo()) { // se o valor n�o vai negativar o a conta
					origem.setSaldo(origem.getSaldo() - valor);
					destino.setSaldo(destino.getSaldo() + valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, false));
						destino.getOperacoes().add(new Operacao(valor, false));
					}
				} else { // cobre todas as outras situa��es
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
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				} catch (Exception e) {
					// caso algo d� errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					System.out.println(
							"infelizmente n�o foi possivel realizar a transa��o \ntente novamente mais tarde.");
				}

			}
		}

		else { // caso seja uma conta corrente
			double limiteAux = (((ContaCorrente) origem).getLimite() + origem.getSaldo()); // realiza a soma do saldo +
																							// limite

			if (valor > limiteAux) { // caso o valor seja superior do que o valor do saldo + limite
				System.out.println("saldo insuficiente");
			} else { // caso n�o for
				if (valor < origem.getSaldo()) { // se o valor for menor que o saldo
					origem.setSaldo(origem.getSaldo() - valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				} else { // para todas as outras situa��es
					origem.setSaldo(origem.getSaldo() - valor);
					if (valor > 0) {
						origem.getOperacoes().add(new Operacao(valor, true));
					}
				}
			}
		}
	}
}
