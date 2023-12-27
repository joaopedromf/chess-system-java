package chess;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaDeXadrez {

    private Tabuleiro tabuleiro;

    public PartidaDeXadrez(){
        tabuleiro = new Tabuleiro(8, 8);
        setupInicial();
    }

    public PecaDeXadrez[][] getPecas(){
        PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for(int i=0; i<tabuleiro.getLinhas(); i++){
            for(int j=0; j<tabuleiro.getColunas(); j++){
                mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
            }
        }

        return mat;
    }

    public PecaDeXadrez efetuarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
        Posicao origem = posicaoOrigem.paraPosicao();
        Posicao destino = posicaoDestino.paraPosicao();
        validarPosicaoOrigem(origem);
        Peca pecaCapturada = realizarMovimento(origem, destino);

        return (PecaDeXadrez) pecaCapturada;
    }

    private Peca realizarMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);
        
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao posicao){
        if(!tabuleiro.haUmaPeca(posicao)){
            throw new XadrezExcecao("Não há nenhuma peça na posição de origem");
        }
    }

    private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca){
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
    }

    private void setupInicial(){
        colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));

        colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));
    }
}