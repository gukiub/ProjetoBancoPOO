package com.br.banco.metodos;

public class Menu {
	public static void montarMenuPrincipal(){
		System.out.println("===========================================================");
		System.out.println("Seja bem vindo ao seu banco");
		System.out.println("Selecione as op��es abaixo para realizar uma opera��o: ");
		System.out.println("1 - Cadastro de conta");
		System.out.println("2 - Caixa eletronico");
		System.out.println("3 - Lista todas as contas com Opera��es");
		System.out.println("4 - Lista todas as contas");
		System.out.println("5 - Lista contas corrente");
		System.out.println("6 - Lista contas poupan�a"); 
		System.out.println("7 - criar contas default");
		System.out.println("0 - finaliza a opera��o");
		System.out.println("===========================================================");
		System.out.println("Qual opera��o deseja realizar: ");
	} 
	
	public static void montarMenuCaixa() {
		System.out.println("===========================================================");
		System.out.println("Caixa eletr�nico");
		System.out.println("Selecione as op��es abaixo para realizar uma opera��o: ");
		System.out.println("1 - Pagamento de boleto com debito em conta");
		System.out.println("2 - transf�rencia");
		System.out.println("0 - retornar");
		System.out.println("===========================================================");
		System.out.println("Qual opera��o deseja realizar: ");
	}
}
