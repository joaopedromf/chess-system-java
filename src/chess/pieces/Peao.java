package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PartidaDeXadrez;
import chess.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

    private PartidaDeXadrez partidaDeXadrez;

    public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        
        Posicao p = new Posicao(0, 0);

        if(getCor() == Cor.BRANCO){
            p.setValores(posicao.getLinha() - 1, posicao.getColuna());
            
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorDeMovimentos() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            
            if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            
            if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento especial En passant branco
            if(posicao.getLinha() == 3){
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);

                if(getTabuleiro().existePosicao(esquerda) && haUmaPecaDoOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadrez.getEnPassantVulneravel()){
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);

                if(getTabuleiro().existePosicao(direita) && haUmaPecaDoOponente(direita) && getTabuleiro().peca(direita) == partidaDeXadrez.getEnPassantVulneravel()){
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }
        }
        else{
            p.setValores(posicao.getLinha() + 1, posicao.getColuna());
            
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorDeMovimentos() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            
            if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            
            if(getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento especial En passant preto
            if(posicao.getLinha() == 4){
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);

                if(getTabuleiro().existePosicao(esquerda) && haUmaPecaDoOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadrez.getEnPassantVulneravel()){
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);

                if(getTabuleiro().existePosicao(direita) && haUmaPecaDoOponente(direita) && getTabuleiro().peca(direita) == partidaDeXadrez.getEnPassantVulneravel()){
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
    
        return mat;
    }

    @Override
    public String toString(){
        return "P";
    }
    
}
