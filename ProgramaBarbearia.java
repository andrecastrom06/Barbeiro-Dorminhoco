package br.edu.cesarschool.poo.cc.barbeiro;

import java.util.Scanner;
import java.util.Random;

public class ProgramaBarbearia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número de barbeiros: ");
        int numBarbeiros = scanner.nextInt(); //ESSE VAI SER O NUMERO DE BARBEIROS
        System.out.print("Digite o número de cadeiras: ");
        int numCadeiras = scanner.nextInt(); //ESSE VAI SER O NUMERO DE CADEIRAS PARA A FILA DE CLIENTES
        Barbearia barbearia = new Barbearia(numBarbeiros, numCadeiras); // "CRIA" A BARBEARIA
        scanner.close();
        for (int i = 0; i < numBarbeiros; i++) {
            new Thread(new Barbeiro(i + 1, barbearia)).start(); // CRIA AS THREADS DE BARBEIROS DE ACORDO COM O NUMERO ESCOLHIDO
        }
        int clienteId = 1; // CONFIGURA QUE O PRIMEIRO CLIENTE VAI TER O ID DE 1
        Random random = new Random(); //CRIA UM RANDOM
        while (true) {
            new Thread(new Cliente(clienteId++, barbearia)).start(); // CRIA OS CLIENTES ONDE QUE O PROXIMO SEMPRE SERA UM ID A MAIS QUE O ANTERIOR
            try {
                Thread.sleep(random.nextInt(2000) + 3000); // RANDOMIZA EM UM ESPAÇO DE TEMPO ENTRE 3 A 5 SEGUNDOS A GERAÇÃO DE UM OUTRO CLIENTE
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // FECHA A THREAD EM CASO DE ERRO
            }
        }
    }
}
