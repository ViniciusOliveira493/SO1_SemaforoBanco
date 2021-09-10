package controller;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

public class ThreadTransacao extends Thread{
	Semaphore saque;
	Semaphore deposito;
	
	int idConta;
	double saldoConta;
	double valorTransacao;
	static DecimalFormat df = new DecimalFormat("#,##0.00");
	public ThreadTransacao(int idConta, double saldoConta, double valorTransacao,Semaphore saque,Semaphore deposito) {
		this.idConta = idConta;
		this.saldoConta = saldoConta;
		this.valorTransacao = valorTransacao;
		
		this.saque = saque;
		this.deposito = deposito;
	}
	
	@Override
	public void run() {
		realizarTransacao();
	}
	
	private void realizarTransacao() {
		if(valorTransacao>0) {
			try {
				deposito.acquire();
				saldoConta+=valorTransacao;
				System.out.println("A conta #"+idConta+" recebeu um depósito de R$"+df.format(valorTransacao)+" "
								+ "e possui um saldo total de R$"+df.format(saldoConta));
				this.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				deposito.release();
			}
		}else {
			try {
				saque.acquire();
				saldoConta+=valorTransacao;
				System.err.println("A conta #"+idConta+" executou um saque de R$"+df.format(valorTransacao*-1)+" "
						+ "e possui um saldo total de R$"+df.format(saldoConta));
				this.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				saque.release();
			}
		}
	}
	
}
