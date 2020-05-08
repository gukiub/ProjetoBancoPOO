package com.br.banco.objetos;

public class Operacao {
	private float valor;
	private boolean isDebito;

	public Operacao(float valor, boolean isDebito) {
		super();
		this.valor = valor;
		this.isDebito = isDebito;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public boolean isDebito() {
		return isDebito;
	}

	public void setDebito(boolean isDebito) {
		this.isDebito = isDebito;
	}
}
