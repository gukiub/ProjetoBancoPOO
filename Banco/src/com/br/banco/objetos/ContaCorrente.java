package com.br.banco.objetos;

public class ContaCorrente extends Conta{
	
	private double limite;
	private double limiteTotal;

	public ContaCorrente(int id, String nome, int tipoConta, double saldo, String banco, double limite) {
		super(id, nome, tipoConta, saldo, banco);
		this.limite = limite;
	}

	public int getId() {
		return id;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public double getLimiteTotal() {
		return limiteTotal;
	}

	public void setLimiteTotal(double limiteTotal) {
		this.limiteTotal = limiteTotal;
	}
	
	

}
