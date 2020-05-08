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
		if (contasList.size() > 0) {
			for (ContaCorrente conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				if (conta.getTipoConta() == 0) {
					System.out.println("conta: corrente");
				} else {
					System.out.println("conta: poupan�a");
				}

				if (contasList instanceof ContaCorrente) {
					System.out.println("limite");
				}
				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco() + "\nOpera��es:");

				if (conta instanceof ContaCorrente) {
					System.out.println("limite disponivel: " + ((ContaCorrente) conta).getLimite());
				}
			} 
		}

		return contasList;
	}

	@Override
	public List<ContaPoupanca> listarContasPoupanca(List<ContaPoupanca> contasList) {
		if (contasList.size() > 0) {
			for (ContaPoupanca conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				if (conta.getTipoConta() == 0) {
					System.out.println("conta: corrente");
				} else {
					System.out.println("conta: poupan�a");
				}

				if (contasList instanceof ContaCorrente) {
					System.out.println("limite");
				}
				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco() + "\nOpera��es:");
			}
		}

		return contasList;
	}

	@Override
	public List<Conta> listarTodasContas(List<Conta> contasList) {
		if (contasList.size() > 0) {
			for (Conta conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				if (conta.getTipoConta() == 0) {
					System.out.println("conta: corrente");
				} else {
					System.out.println("conta: poupan�a");
				}

				if (contasList instanceof ContaCorrente) {
					System.out.println("limite");
				}
				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco());

				if (conta instanceof ContaCorrente) {
					System.out.println("limite disponivel: " + ((ContaCorrente) conta).getLimite());
				}
			}
		}

		return contasList;
	}

	@Override
	public List<Conta> listarTodasAsContasComOperacoes(List<Conta> contasList) {
		if (contasList.size() > 0) {
			for (Conta conta : contasList) {
				System.out.println("\nId: " + conta.getId() + "\nNome: " + conta.getNome());
				if (conta.getTipoConta() == 0) {
					System.out.println("conta: corrente");
				} else {
					System.out.println("conta: poupan�a");
				}

				if (contasList instanceof ContaCorrente) {
					System.out.println("limite");
				}
				System.out.println("Saldo: " + conta.getSaldo() + "\nBanco: " + conta.getBanco());

				if (conta instanceof ContaCorrente) {
					System.out.println("limite disponivel: " + ((ContaCorrente) conta).getLimite());
				}
				
				if(conta.getOperacoes().size() <= 0) {
					System.out.println("Opera��es: Nenhuma opera��o realizada at� o momento.");
				} else {
					System.out.println("Opera��es:");
				}
				
				for (Operacao op : conta.getOperacoes()) {
					System.out.println(" valor: " + op.getValor());
					if(op.isDebito() == true) {
						System.out.println(" tipo da transa��o: pagamento de boleto");
					} else {
						System.out.println(" tipo de transa��o: transferencia");
					}
				}
			}
		} else {
			System.out.println("nenhuma conta encontrada\n");
		}

		return contasList;
	}

}
