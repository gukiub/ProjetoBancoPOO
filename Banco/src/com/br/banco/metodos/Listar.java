package com.br.banco.metodos;

import java.util.ArrayList;
import java.util.List;

import com.br.banco.interfaces.ListarContas;
import com.br.banco.objetos.Conta;
import com.br.banco.objetos.ContaCorrente;
import com.br.banco.objetos.ContaPoupanca;
import com.br.banco.objetos.Operacao;

public class Listar implements ListarContas {

	@Override
	public List<ContaCorrente> listarContasCorrentes(List<ContaCorrente> contasList) {
		// verifica se a lista não está vazia
		if (contasList.size() > 0) {
			// percorre a lista de contas trazendo objetos do tipo conta Corrente
			for (ContaCorrente conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				
				// troca o valor 0 por uma string
				if (conta.getTipoConta() == 0) {
					System.out.println("conta: corrente");
				}

				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco());
				System.out.println("limite disponivel: " + ((ContaCorrente) conta).getLimite());
			}
		} else {
			System.out.println("nenhuma conta encontrada\n");
		}

		return contasList;
	}

	@Override
	public List<ContaPoupanca> listarContasPoupanca(List<ContaPoupanca> contasList) {
		if (contasList.size() > 0) {
			for (ContaPoupanca conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				
				// troca o valor 1 por uma string
				if (conta.getTipoConta() == 1) {
					System.out.println("conta: poupança");
				} 

				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco());
			}
		} else {
			System.out.println("nenhuma conta encontrada\n");
		}

		return contasList;
	}

	@Override
	public List<Conta> listarTodasContas(List<Conta> contasList) {
		// verifica se a lista não está vazia
		if (contasList.size() > 0) {
			for (Conta conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				if (conta.getTipoConta() == 0) {
					System.out.println("conta: corrente");
				} else {
					System.out.println("conta: poupança");
				}

				if (contasList instanceof ContaCorrente) {
					System.out.println("limite");
				}
				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco());

				if (conta instanceof ContaCorrente) {
					System.out.println("limite disponivel: " + ((ContaCorrente) conta).getLimite());
				}
			}
		} else {
			System.out.println("nenhuma conta encontrada\n");
		}

		return contasList;
	}

	@Override
	public List<Conta> listarTodasAsContasComOperacoes(List<Conta> contasList) {
		// verifica se a lista não está vazia
		if (contasList.size() > 0) {
			for (Conta conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				// verifica qual o tipo da conta e substitui o numero por uma string
				
				if (conta.getTipoConta() == 0) {
					System.out.println("conta: corrente");
				} else {
					System.out.println("conta: poupança");
				}
				
				// verifica se o que está vindo é um objeto do tipo conta corrente e então puxa o limite
				if (contasList instanceof ContaCorrente) {
					System.out.println("limite");
				}
				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco());

				if (conta instanceof ContaCorrente) {
					System.out.println("limite disponivel: " + ((ContaCorrente) conta).getLimite());
				}

				if (conta.getOperacoes().size() <= 0) {
					System.out.println("Operações: Nenhuma operação realizada até o momento.");
				} else {
					System.out.println("Operações:");
				}

				// percorre as operações para printar na tela
				for (Operacao op : conta.getOperacoes()) {
					System.out.println(" valor: " + op.getValor());
					// verifica se debito é igual a true caso for assume que é um boleto
					if (op.isDebito() == true) {
						System.out.println(" tipo da transação: pagamento de boleto");
					} else {
						System.out.println(" tipo de transação: transferencia");
					}
				}
			}
		} else {
			System.out.println("nenhuma conta encontrada\n");
		}

		return contasList;
	}

}
