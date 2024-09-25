package br.edu.cesarschool.poo.cc.barbeiro;

import java.util.LinkedList;
import java.util.Queue;

public class Barbearia {
    private final int numBarbeiros;
    private final int numCadeiras;
    private final Queue<Cliente> filaClientes;
    private int clientesAtendendo;
    
    public Barbearia(int numBarbeiros, int numCadeiras) {
        this.numBarbeiros = numBarbeiros;
        this.numCadeiras = numCadeiras;
        this.filaClientes = new LinkedList<>();
        this.clientesAtendendo = 0;
    }

    public synchronized boolean cortaCabelo(Cliente cliente) {
        if (clientesAtendendo < numBarbeiros && filaClientes.size() < numCadeiras) { // VERIFICA SE EXISTEM CADEIRAS DISPONIVEIS
            filaClientes.add(cliente); // ADICIONA O CLIENTE A FILA
            System.out.println("Cliente " + cliente.getID() + " esperando corte..."); 
            notifyAll(); // NOTIFICA OS BARBEIROS QUE TEM UM CLIENTE NA FILA
            return true; // NOTIFICA O CLIENTE QUE ELE ENTROU NA FILA
        }
        return false; // CASO NÃO TENHA CADEIRAS DISPONIVEIS NOTIFICA O CLIENTE QUE NÃO POSSUI CADEIRAS DISPONIVEIS NA FILA
    }
    
    public synchronized boolean filaVazia() {
        return filaClientes.isEmpty(); // VERIFICA SE A FILA ESTA VAZIA
    }

    public synchronized Cliente proximoCliente() {
        while (filaClientes.isEmpty()) {
            try {
                wait(); // ENQUANTO A FILA ESTIVER VAZIA O PROGRAMA VAI FICAR PARADO
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // FECHA A THREAD EM CASO DE ERRO
            }
        }
        clientesAtendendo++; // CASO A FILA NÃO ESTEJA VAZIA INCREMENTA EM UM A VARIAVEL CLIENTESATENDENDO
        return filaClientes.poll(); // REMOVE E RETORNA O PRIMEIRO ELEMENTO DA FILA
    }

    public synchronized void corteTerminado(Cliente cliente) {
        clientesAtendendo--; // INCRELMENTA EM -1 A VARIAVEL CLIENTES ATENDENDO
        System.out.println("Cliente " + cliente.getID() + " terminou o corte... saindo da barbearia!");
        notifyAll(); // NOTIFICA OS BARBEIROS
    }
}
