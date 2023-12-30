package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PartidaDeXadrez;
import chess.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    private PartidaDeXadrez partidaDeXadrez;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
    }
    
    @Override
    public String toString(){
        return "R";
    }

    private boolean podeMover(Posicao posicao){
        PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    private boolean testeTorreRoque(Posicao posicao){
        PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorDeMovimentos() == 0; 
    }

    @Override
    public boolean[][] movimentosPossiveis(){
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        
        Posicao p = new Posicao(0, 0);

        // acima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // abaixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // direita
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // noroeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // nordeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudoeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento especial Roque
        if(getContadorDeMovimentos() == 0 && !partidaDeXadrez.getXeque()){
            // Movimento especial Roque pequeno (torre do lado do rei)
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);

            if(testeTorreRoque(posT1)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);

                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null){
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }

            // Movimento especial Roque grande (torre do lado da rainha)
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);

            if(testeTorreRoque(posT2)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);

                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null){
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }

        return mat;
    }
}
