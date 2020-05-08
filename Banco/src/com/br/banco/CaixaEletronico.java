package com.br.banco;

import com.br.banco.objetos.Conta;
import com.br.banco.objetos.ContaCorrente;
import com.br.banco.objetos.ContaPoupanca;
import com.br.banco.objetos.Operacao;

public class CaixaEletronico {
	public static void pagar(Conta origem, Conta destino, float valor) {
		
		double aux = origem.getSaldo() - valor;
		
		
		// verifica se a origem � do tipo poupan�a
		if (origem instanceof ContaPoupanca) {
			// variaveis para rollback caso a opera��o n�o ocorra bem
			double valorAnteriorDestino = destino.getSaldo();
			double valorAnteriorOrigem = origem.getSaldo();

			// verifica se o saldo menos o valor � negativo
			if (origem.getSaldo() - valor < 0) {
				System.out.println("saldo insuficiente");
			} else {
				try {
					// inicia a transa��o
					origem.setSaldo(origem.getSaldo() - valor);
					destino.setSaldo(destino.getSaldo() + valor);

					origem.getOperacoes().add(new Operacao(valor, false));
					destino.getOperacoes().add(new Operacao(valor, false));

					System.out.println("numero de opera��es da conta de origem: " + origem.getOperacoes().size());
					System.out.println("numero de opera��es da conta de destino: " + destino.getOperacoes().size());
				} catch (Exception e) {
					// caso algo d� errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					destino.setSaldo(valorAnteriorDestino);
					System.out.println(
							"infelizmente n�o foi possivel realizar a transa��o \ntente novamente mais tarde.");
				}

			}
		}
		
		
		else if ((origem.getSaldo() + ((ContaCorrente) origem).getLimite()) - valor < 0) {
			System.out.println("saldo insuficiente");
		}

		
		else {
			try {
				if (aux < 0) {
					// nesse caso o saldo para n�o ficar negativo � setado como zero
					origem.setSaldo(0);
					
					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);
					
					// adiciona a opera��o na lista
					origem.getOperacoes().add(new Operacao(valor, false));
				} else {
					// caso onde a conta n�o possui limite
					origem.setSaldo(origem.getSaldo() - valor);
					origem.getOperacoes().add(new Operacao(valor, false));
				}
				// depois das verifica��es envia o valor para a conta destino
				destino.setSaldo(destino.getSaldo() + valor);
				destino.getOperacoes().add(new Operacao(valor, false));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	
	public static void pagar(Conta origem, float valor) {
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

					origem.getOperacoes().add(new Operacao(valor, false));

					System.out.println("numero de opera��es da conta de origem: " + origem.getOperacoes().size());
				} catch (Exception e) {
					// caso algo d� errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					System.out.println(
							"infelizmente n�o foi possivel realizar a transa��o \ntente novamente mais tarde.");
				}

			}
		}
		
		else if ((origem.getSaldo() + ((ContaCorrente) origem).getLimite()) - valor < 0) {
			System.out.println("saldo insuficiente");
		}
		
		else {
			try {
				double aux = origem.getSaldo() - valor;
				if (aux < 0) {
					// nesse caso o saldo para n�o ficar negativo � setado como zero
					origem.setSaldo(0);
					
					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);
					
					// adiciona a opera��o na lista
					origem.getOperacoes().add(new Operacao(valor, true));
				} else {
					// caso onde a conta n�o possui limite
					origem.setSaldo(origem.getSaldo() - valor);
					origem.getOperacoes().add(new Operacao(valor, true));
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
