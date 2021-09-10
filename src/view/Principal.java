package view;

import java.util.concurrent.Semaphore;

import controller.ThreadTransacao;

public class Principal {
	public static void main(String[] args) {
		int permissao = 1;
		int qtdTransacoes = 20;
		Semaphore saque = new Semaphore(permissao);
		Semaphore deposito = new Semaphore(permissao);
		
		ThreadTransacao transacoes[] = new ThreadTransacao[qtdTransacoes];
		for(int i=1;i<=qtdTransacoes;i++) {
			double saldo = ((Math.random()*3001)+500);
			double valorTransacao = ((Math.random()*495)+5);
			int parOuImpar = (int) (Math.random()*11);
			if(parOuImpar%2!=0) {
				valorTransacao*=-1;
			}
			ThreadTransacao t = new ThreadTransacao(i, saldo, valorTransacao, saque, deposito);
			transacoes[i-1]=t;
		}
		
		for (int i = 0; i < transacoes.length; i++) {
			transacoes[i].start();
		}
	}
}
