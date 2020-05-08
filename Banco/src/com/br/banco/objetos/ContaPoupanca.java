package com.br.banco.objetos;

import java.util.List;


public class ContaPoupanca extends Conta{
	
	public ContaPoupanca(int id, String nome, int tipoConta, double saldo, String banco) {
		super(id, nome, tipoConta, saldo, banco);
	}

	public static int getSequencia() {
		return sequencia;
	}

	public static void setSequencia(int sequencia) {
		ContaPoupanca.sequencia = sequencia;
	}

	public int getId() {
		return id;
	}
	
	
}
