package com.br.banco.interfaces;

import java.util.List;

import com.br.banco.objetos.Conta;
import com.br.banco.objetos.ContaCorrente;
import com.br.banco.objetos.ContaPoupanca;

public interface ListarContas {
	public abstract List<ContaCorrente> listarContasCorrentes(List<ContaCorrente> contasList);
	public abstract List<ContaPoupanca> listarContasPoupanca(List<ContaPoupanca> contasList);
	public abstract List<Conta> listarTodasContas(List<Conta> contasList);
	public abstract List<Conta> listarTodasAsContasComOperacoes(List<Conta> contasList);
}
