package br.edu.cesarschool.poo.cc.barbeiro;

import java.util.Random;

public class Barbeiro extends Pessoa implements Runnable {
    private Barbearia barbearia;
    private boolean dormindo;

    public Barbeiro(int id, Barbearia barbearia) {
        super(id);
        this.barbearia = barbearia;
        this.dormindo = false;
    }

    @Override // COMENTARIO QUE INDICA QUE ESTA REESCREVENDO
    public void run() {
        while (true) {
            synchronized (barbearia) {
                while (barbearia.filaVazia()) { // VERIFICA SE A FILA ESTA VAZIA
                    if (!dormindo) {
                        System.out.println("Barbeiro " + getID() + " indo dormir um pouco... não há clientes na barbearia...");
                        dormindo = true; // SE A DEFINE A FLAG COMO TRUE
                    }
                    try {
                        barbearia.wait(); // O BARBEIRO FICA INATIVO
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // EM CASO DE ERRO FECHA A THREAD
                    }
                }

                if (dormindo) { // QUANDO O BARBEIRO É ATIVADO VERIFICA SE ELE ESTAVA DORMINDO POR MEIO DA FLAG
                    System.out.println("Barbeiro " + getID() + " acordou! Começando os trabalhos!"); // NOTIFICA QUE O BARBEIRO ACORDOU
                    dormindo = false; // DEFINE A FLAG COMO FALSE
                }

                Cliente cliente = barbearia.proximoCliente(); // CHAMA O CLIENTE
                if (cliente != null) {
                    System.out.println("Cliente " + cliente.getID() + " cortando cabelo com Barbeiro " + getID());
                    try {
                        Thread.sleep(new Random().nextInt(2000) + 1000); // RANDOMIZA UM TEMPO PARA O CORTE
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // EM CASO DE ERRO FECHA A THREAD
                    }
                    barbearia.corteTerminado(cliente); // TERMINA O CORTE DO CLIENTE
                }
            }
        }
    }
}
