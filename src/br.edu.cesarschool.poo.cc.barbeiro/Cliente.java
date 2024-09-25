package br.edu.cesarschool.poo.cc.barbeiro;
import java.util.Random;

public class Cliente extends Pessoa implements Runnable {
    private Barbearia barbearia;

    public Cliente(int id, Barbearia barbearia) {
        super(id);
        this.barbearia = barbearia;
    }

    @Override // COMENTARIO PARA DIZER QUE ESTA SENDO REESCRITO
    public void run() {
        while (true) {
            if (barbearia.cortaCabelo(this)) { // VERIFICA SE É POSSIVEL CORTAR O CABELO
                try {
                    Thread.sleep(new Random().nextInt(2000) + 3000); // RANDOMIZA UM INTERVALO DE TEMPO PARA O CORTE DE CABELO
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // FECHA A THREAD EM CASO DE ERRO
                }
            } else {
                System.out.println("Cliente " + getID() + " tentou entrar na barbearia, mas está lotada... indo dar uma voltinha"); // CASO NÃO SEJA POSSIVEL CORTAR O CABELO O CLIENTE SAI
            }
            try {
                Thread.sleep(new Random().nextInt(2000) + 3000); // RANDOMIZA UM ESPAÇO DE TEMPO
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // FECHA A THREAD EM CASO DE ERRO
            }
        }
    }
}
