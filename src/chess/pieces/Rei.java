package chess.pieces;

import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }
    
    @Override
    public String toString(){
        return "R";
    }

    @Override
    public boolean[][] movimentosPossiveis(){
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        return mat;
    }
}
