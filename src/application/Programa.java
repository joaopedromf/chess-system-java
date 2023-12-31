package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.PartidaDeXadrez;
import chess.PecaDeXadrez;
import chess.PosicaoXadrez;
import chess.XadrezExcecao;

public class Programa {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
        List<PecaDeXadrez> capturado = new ArrayList<>();

        while(!partidaDeXadrez.getXequeMate()){
            try{
                UI.limparTela();
                UI.imprimirPartida(partidaDeXadrez, capturado);
                System.out.println();
                System.out.print("Origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

                boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
                UI.limparTela();
                UI.imprimirTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

                PecaDeXadrez pecaCapturada = partidaDeXadrez.efetuarMovimentoXadrez(origem, destino);
            
                if(pecaCapturada != null){
                    capturado.add(pecaCapturada);
                }

                if(partidaDeXadrez.getPromovido() != null){
                    System.out.print("Insira a peça para promoção (B/C/T/Q): ");
                    String tipo = sc.nextLine().toUpperCase();
                    while(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")){
                        System.out.print("Valor inválido! Insira a peça para promoção (B/C/T/Q): ");
                        tipo = sc.nextLine().toUpperCase();
                    }
                    partidaDeXadrez.substituirPecaPromovida(tipo);
                }
            } 
            catch(XadrezExcecao e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch(InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limparTela();
        UI.imprimirPartida(partidaDeXadrez, capturado);
    }
}