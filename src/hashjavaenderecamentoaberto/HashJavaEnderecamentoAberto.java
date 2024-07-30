package hashjavaenderecamentoaberto;

import java.util.Scanner;

public class HashJavaEnderecamentoAberto<T> {
    private static final int VAZIO = 0;
    private static final int REMOVIDO = 1;
    private static final int OCUPADO = 2;
    private static final double LOAD_FACTOR = 0.75;

    private static class Dado<T> {
        long key;
        T value;

        Dado(long key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class HashEntry<T> {
        Dado<T> dado;
        int estado;

        HashEntry() {
            this.estado = VAZIO;
        }
    }

    private HashEntry<T>[] tabela;
    private int tamanho;
    private int numElementos;
    private int comparacoes;

    public HashJavaEnderecamentoAberto(int tamanho) {
        this.tamanho = tamanho;
        tabela = new HashEntry[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new HashEntry<>();
        }
    }

    private int funcaoHash(long chave, int i) {
        return (int) ((chave + i) % tamanho);
    }

    public void put(long key, T value) {
        if ((double) numElementos / tamanho >= LOAD_FACTOR) {
            redimensiona();
        }
        
        Dado<T> dado = new Dado<>(key, value);
        int i = 0;
        int map;
        comparacoes = 0;
        do {
            map = funcaoHash(key, i);
            if (tabela[map].estado != OCUPADO) {
                tabela[map].dado = dado;
                tabela[map].estado = OCUPADO;
                numElementos++;
                return;
            }
            comparacoes++;
            i++;
        } while (i < tamanho);
    }// fim funcao put
//------------------------------------------------------
    public boolean containsKey(long key) {
        int i = 0;
        int map;
        do {
            map = funcaoHash(key, i);
            if (tabela[map].estado == VAZIO) {
                return false;
            }
            if (tabela[map].estado == OCUPADO && tabela[map].dado.key == key) {
                return true;
            }
            i++;
        } while (i < tamanho);
        return false;
    }

    private int getIndex(long key) {
        int i = 0;
        int map;
        do {
            map = funcaoHash(key, i);
            if (tabela[map].estado == VAZIO) {
                return -1;
            }
            if (tabela[map].estado == OCUPADO && tabela[map].dado.key == key) {
                return map;
            }
            i++;
        } while (i < tamanho);
        return -1;
    }

    public boolean deleteHash(long key) {
        // buscar com a função getIndex e marcar como removido
        return false;
    }

    public void printHash() {
        for (int i = 0; i < tamanho; i++) {
            if (tabela[i].estado == OCUPADO) {
                System.out.printf("[%d] - %d - %s |%n", i, tabela[i].dado.key, tabela[i].dado.value.toString());
            } else {
                System.out.printf("[%d] -___|%n", i);
            }
        }
    }

    private void redimensiona() {
        int novoTamanho = tamanho * 2;
        HashEntry<T>[] novaTabela = new HashEntry[novoTamanho];
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new HashEntry<>();
        }

        for (HashEntry<T> entry : tabela) {
            if (entry.estado == OCUPADO) {
                int i = 0;
                int map;
                do {
                    map = (int) ((entry.dado.key + i) % novoTamanho);
                    if (novaTabela[map].estado != OCUPADO) {
                        novaTabela[map].dado = entry.dado;
                        novaTabela[map].estado = OCUPADO;
                        break;
                    }
                    i++;
                } while (i < novoTamanho);
            }
        }

        tabela = novaTabela;
        tamanho = novoTamanho;
    }

    private static int menu(Scanner scanner) {
        System.out.println("\t\t*** IFSULDEMINAS - CAMPUS MACHADO ***");
        System.out.println("\t\t*** Estrutura de Dados I ***");
        System.out.println("\t\t*** HASH OPEN ADDRESS ***");
        System.out.println("1-Inserir");
        System.out.println("2-Remover");
        System.out.println("3-Buscar");
        System.out.println("4-Alterar");
        System.out.println("0-Sair");
        System.out.print("Escolha uma opcao: ");
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Tamanho da tabela: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer
        HashJavaEnderecamentoAberto<String> hashTable = new HashJavaEnderecamentoAberto<>(n);
        int op;
        do {
            hashTable.printHash();
            op = menu(scanner);
            switch (op) {
                case 1:
                    System.out.print("Entre com a chave: ");
                    long chave = scanner.nextLong();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Entre com o objeto: ");
                    String nome = scanner.nextLine();
                    hashTable.put(chave, nome);
                    break;

                case 2:
                    System.out.print("Chave para remover: ");
                    chave = scanner.nextLong();
                    scanner.nextLine(); // Limpar o buffer
                    boolean removeu = hashTable.deleteHash(chave);
                    if (!removeu) {
                        System.out.println("Chave nao existente para remocao");
                    } else {
                        System.out.println("Chave removida com sucesso! :)");
                    }
                    break;

                case 3:
                    System.out.print("Chave para busca: ");
                    chave = scanner.nextLong();
                    scanner.nextLine(); // Limpar o buffer
                    boolean encontrado = hashTable.containsKey(chave);
                    if (!encontrado) {
                        System.out.println("Chave nao encontrada :(");
                    } else {
                        System.out.println("Chave encontrada!");
                    }
                    break;

                case 4:
                    System.out.print("Chave para alterar: ");
                    chave = scanner.nextLong();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Novo valor: ");
                    String novoValor = scanner.nextLine();
                    //criar funcao alterar e chamar
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
            try {
                Thread.sleep(1000); // Pausa para simular o getch()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J"); // Limpar a tela (ANSI escape code)
            System.out.flush();
        } while (op != 0);

        scanner.close();
    }
}
