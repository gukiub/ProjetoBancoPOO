package com.br.banco;

import com.br.banco.objetos.Conta;
import com.br.banco.objetos.ContaCorrente;
import com.br.banco.objetos.ContaPoupanca;
import com.br.banco.objetos.Operacao;

public class CaixaEletronico {
	public static void pagar(Conta origem, Conta destino, float valor) {
		
		double aux = origem.getSaldo() - valor;
		
		
		// verifica se a origem é do tipo poupança
		if (origem instanceof ContaPoupanca) {
			// variaveis para rollback caso a operação não ocorra bem
			double valorAnteriorDestino = destino.getSaldo();
			double valorAnteriorOrigem = origem.getSaldo();

			// verifica se o saldo menos o valor é negativo
			if (origem.getSaldo() - valor < 0) {
				System.out.println("saldo insuficiente");
			} else {
				try {
					// inicia a transação
					origem.setSaldo(origem.getSaldo() - valor);
					destino.setSaldo(destino.getSaldo() + valor);

					origem.getOperacoes().add(new Operacao(valor, false));
					destino.getOperacoes().add(new Operacao(valor, false));

					System.out.println("numero de operações da conta de origem: " + origem.getOperacoes().size());
					System.out.println("numero de operações da conta de destino: " + destino.getOperacoes().size());
				} catch (Exception e) {
					// caso algo dê errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					destino.setSaldo(valorAnteriorDestino);
					System.out.println(
							"infelizmente não foi possivel realizar a transação \ntente novamente mais tarde.");
				}

			}
		}
		
		
		else if ((origem.getSaldo() + ((ContaCorrente) origem).getLimite()) - valor < 0) {
			System.out.println("saldo insuficiente");
		}

		
		else {
			try {
				if (aux < 0) {
					// nesse caso o saldo para não ficar negativo é setado como zero
					origem.setSaldo(0);
					
					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);
					
					// adiciona a operação na lista
					origem.getOperacoes().add(new Operacao(valor, false));
				} else {
					// caso onde a conta não possui limite
					origem.setSaldo(origem.getSaldo() - valor);
					origem.getOperacoes().add(new Operacao(valor, false));
				}
				// depois das verificações envia o valor para a conta destino
				destino.setSaldo(destino.getSaldo() + valor);
				destino.getOperacoes().add(new Operacao(valor, false));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	
	public static void pagar(Conta origem, float valor) {
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

					origem.getOperacoes().add(new Operacao(valor, false));

					System.out.println("numero de operações da conta de origem: " + origem.getOperacoes().size());
				} catch (Exception e) {
					// caso algo dê errado faz o rollback
					origem.setSaldo(valorAnteriorOrigem);
					System.out.println(
							"infelizmente não foi possivel realizar a transação \ntente novamente mais tarde.");
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
					// nesse caso o saldo para não ficar negativo é setado como zero
					origem.setSaldo(0);
					
					// cast de origem para extrair do limite
					((ContaCorrente) origem).setLimite(((ContaCorrente) origem).getLimite() + aux);
					
					// adiciona a operação na lista
					origem.getOperacoes().add(new Operacao(valor, true));
				} else {
					// caso onde a conta não possui limite
					origem.setSaldo(origem.getSaldo() - valor);
					origem.getOperacoes().add(new Operacao(valor, true));
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
