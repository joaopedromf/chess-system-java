package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaDeXadrez;

public class Rainha extends PecaDeXadrez {

    public Rainha(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }
    
    @Override
    public String toString(){
        return "Q";
    }

    @Override
    public boolean[][] movimentosPossiveis(){
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        
        Posicao p = new Posicao(0, 0);

        // acima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        
        // direita
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // abaixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // noroeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() - 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // nordeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() + 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }
        
        // sudeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() + 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudoeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() - 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
