package chess;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{
    
    private Cor cor;
    private int contadorDeMovimentos;

    public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor){
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor(){
        return cor;
    }

    public int getContadorDeMovimentos(){
        return contadorDeMovimentos;
    }

    public void aumentarContadorDeMovimentos(){
        contadorDeMovimentos++;
    }

    public void diminuirContadorDeMovimentos(){
        contadorDeMovimentos++;
    }

    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.daPosicao(posicao);
    }

    protected boolean haUmaPecaDoOponente(Posicao posicao){
        PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }
}
