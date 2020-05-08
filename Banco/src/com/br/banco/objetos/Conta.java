package com.br.banco.objetos;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

	protected String nome;
	protected int TipoConta;
	protected double saldo;
	protected String banco;
	protected List<Operacao> operacoes;
	protected List<Conta> contasList;
	protected double limite;

	protected static int sequencia = 0; // variavel iteravel para incremento do id

	protected int id;

	public Conta(int id, String nome, int tipoConta, double saldo, String banco) {
		super();
		this.id = sequencia++; // metodo de incremento do id
		this.nome = nome;
		TipoConta = tipoConta;
		this.saldo = saldo;
		this.banco = banco;
		this.operacoes = new ArrayList<Operacao>();
	}

	public int getId() {
		return id;
	}

	public List<Conta> getContasList() {
		return contasList;
	}

	public void setContasList(List<Conta> contasList) {
		this.contasList = contasList;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTipoConta() {
		return TipoConta;
	}

	public void setTipoConta(int tipoConta) {
		TipoConta = tipoConta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public List<Operacao> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(List<Operacao> operacoes) {
		this.operacoes = operacoes;
	}

}
