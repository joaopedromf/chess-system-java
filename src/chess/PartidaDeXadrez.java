package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Bispo;
import chess.pieces.Cavalo;
import chess.pieces.Peao;
import chess.pieces.Rainha;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaDeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean xeque;
    private boolean xequeMate;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public PartidaDeXadrez(){
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        setupInicial();
    }

    public int getTurno(){
        return turno;
    }

    public Cor getJogadorAtual(){
        return jogadorAtual;
    }

    public boolean getXeque(){
        return xeque;
    }

    public boolean getXequeMate(){
        return xequeMate;
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

    public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
        Posicao posicao = posicaoOrigem.paraPosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaDeXadrez efetuarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
        Posicao origem = posicaoOrigem.paraPosicao();
        Posicao destino = posicaoDestino.paraPosicao();
        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem, destino);
        Peca pecaCapturada = realizarMovimento(origem, destino);

        if(testeXeque(jogadorAtual)){
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezExcecao("Você não pode se colocar em xeque");
        }

        xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;

        if(testeXequeMate(oponente(jogadorAtual))){
            xequeMate = true;
        }
        else{
            proximoTurno();
        }

        return (PecaDeXadrez) pecaCapturada;
    }

    private Peca realizarMovimento(Posicao origem, Posicao destino){
        PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(origem);
        p.aumentarContadorDeMovimentos();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(p, destino);

        if(pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        // Movimento especial Roque pequeno (torre do lado do rei)

        if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.aumentarContadorDeMovimentos();
        }

        // Movimento especial Roque grande (torre do lado da rainha)

        if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.aumentarContadorDeMovimentos();
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
        PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
        p.diminuirContadorDeMovimentos();
        tabuleiro.colocarPeca(p, origem);

        if(pecaCapturada != null){
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }

        // Movimento especial Roque pequeno (torre do lado do rei)

        if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.diminuirContadorDeMovimentos();
        }

        // Movimento especial Roque grande (torre do lado da rainha)

        if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2){
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.diminuirContadorDeMovimentos();
        }
    }

    private void validarPosicaoOrigem(Posicao posicao){
        if(!tabuleiro.haUmaPeca(posicao)){
            throw new XadrezExcecao("Não há nenhuma peça na posição de origem");
        }
        if(jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()){
            throw new XadrezExcecao("A peça escolhida não é sua");
        }
        if(!tabuleiro.peca(posicao).haAlgumMovimentoPossivel()){
            throw new XadrezExcecao("Não há movimentos possíveis para a peça escolhida");
        }
    }

    private void validarPosicaoDestino(Posicao origem, Posicao destino){
        if(!tabuleiro.peca(origem).movimentoPossivel(destino)){
            throw new XadrezExcecao("A peça escolhida não pode se mover para a posição de destino");
        }
    }

    private void proximoTurno(){
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private Cor oponente(Cor cor){
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaDeXadrez rei(Cor cor){
        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
        
        for(Peca p : lista){
            if(p instanceof Rei){
                return (PecaDeXadrez)p;
            }
        }

        throw new IllegalStateException("Não existe rei " + cor + " no tabuleiro");
    }

    private boolean testeXeque(Cor cor){
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
        List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());

        for(Peca p : pecasDoOponente){
            boolean[][] mat = p.movimentosPossiveis();

            if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                return true;
            }
        }
        return false;
    }

    private boolean testeXequeMate(Cor cor){
        if(!testeXeque(cor)){
            return false;
        }

        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());

        for(Peca p : lista){
            boolean[][] mat = p.movimentosPossiveis();

            for(int i=0; i<tabuleiro.getLinhas(); i++){
                for(int j=0; j<tabuleiro.getColunas(); j++){
                    if(mat[i][j]){
                        Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().paraPosicao();
                        Posicao destino = new Posicao(i, j);

                        Peca pecaCapturada = realizarMovimento(origem, destino);

                        boolean testeXeque = testeXeque(cor);
                        desfazerMovimento(origem, destino, pecaCapturada);
                        if(!testeXeque){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca){
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void setupInicial(){
        colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
    }
}