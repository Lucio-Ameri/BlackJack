package ar.edu.unlu.poo.modelo;

import java.util.List;

public class Dealer {
    private ManoDealer mano;
    private Baraja baraja;

    public Dealer(){
        this.mano = new ManoDealer();
        this.baraja = new Baraja();
    }

    public ManoDealer getMano(){
        return mano;
    }

    public boolean condicionSeguro(){
        return mano.primerCartaEsAs();
    }

    public void revelarMano(){
        mano.revelarManoCompleta();
    }

    public Carta repartirCarta(){
        return baraja.repartir();
    }
}
