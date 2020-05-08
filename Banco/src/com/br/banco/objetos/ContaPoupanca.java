package com.br.banco.objetos;


public class ContaPoupanca extends Conta{
	
	public ContaPoupanca(int id, String nome, int tipoConta, double saldo, String banco) {
		super(id, nome, tipoConta, saldo, banco);
	}

	public int getId() {
		return id;
	}
	
}
