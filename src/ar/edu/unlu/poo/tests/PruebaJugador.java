package ar.edu.unlu.poo.tests;

import ar.edu.unlu.poo.modelo.*;
import ar.edu.unlu.poo.modelo.estados.EstadoDeLaMano;
import ar.edu.unlu.poo.modelo.estados.PaloCarta;
import ar.edu.unlu.poo.modelo.estados.ValorCarta;

import java.util.ArrayList;
import java.util.List;

public class PruebaJugador {
    public static void main(String[] args) {

        // testeo el printeo correcto de las clases y que estas hagan el trabajo correcto.
        Dealer dealer = new Dealer();
        Jugador j1 = new Jugador("Lucio", 1000.0);
        Jugador j2 = new Jugador("Franco", 1000.0);

        List<Jugador> jugadores = new ArrayList<Jugador>();
        jugadores.add(j1);
        jugadores.add(j2);

        for(Jugador j: jugadores){
            for(int i = 0; i < 2; i++) {
                j.agregarMano(new ManoJugador(300));
            }
        }

        for(Jugador j: jugadores){
            List<ManoJugador> manos = j.getManos();

            for(ManoJugador m: manos){
                do {
                    m.recibirCarta(dealer.repartirCarta());
                }
                while(m.getEstado() == EstadoDeLaMano.TURNO_INICIAL || m.getEstado() == EstadoDeLaMano.EN_JUEGO);
            }
        }

        ManoDealer manoD = dealer.getMano();
        while(manoD.getEstado() == EstadoDeLaMano.TURNO_INICIAL || manoD.getEstado() == EstadoDeLaMano.EN_JUEGO){
            Carta c = dealer.repartirCarta();
            c.revelarCarta();
            manoD.recibirCarta(c);
        }

        dealer.definirResultados(jugadores);
        System.out.println(dealer.toString());


        for(Jugador j: jugadores){
            System.out.println(j.toString());
        }


        // testeo que funciona bien el doblar mano.
        Jugador j3 = new Jugador("Maxi", 1000.0);
        ManoJugador m3 = new ManoJugador(300.0);
        j3.agregarMano(m3);
        m3.recibirCarta(dealer.repartirCarta());
        m3.recibirCarta(dealer.repartirCarta());
        m3.doblarMano(dealer.repartirCarta());
        System.out.println(j3.toString());


        // testeo que funciona bien el separar mano.
        Jugador j4 = new Jugador("Mariana", 1000.0);
        ManoJugador m4 = new ManoJugador(300.0);
        j4.agregarMano(m4);
        m4.recibirCarta(new Carta(PaloCarta.CORAZONES, ValorCarta.CINCO));
        m4.recibirCarta(new Carta(PaloCarta.CORAZONES, ValorCarta.CINCO));

        j4.agregarMano(m4.separarMano());
        List<ManoJugador> manos = j4.getManos();
        for(ManoJugador m: manos){
            m.recibirCarta(dealer.repartirCarta());
        }
        System.out.println(j4.toString());


        // Testeo que funcione bien asegurarse.
        Jugador j5 = new Jugador("Mariana", 1000.0);
        ManoJugador m5 = new ManoJugador(300.0);
        j5.agregarMano(m5);
        m5.recibirCarta(new Carta(PaloCarta.CORAZONES, ValorCarta.CINCO));
        m5.recibirCarta(new Carta(PaloCarta.CORAZONES, ValorCarta.CINCO));
        m5.asegurarme();
        System.out.println(j5.toString());


        // teste que funcione bien rendirme
        Jugador j6 = new Jugador("Mariana", 1000.0);
        ManoJugador m6 = new ManoJugador(300.0);
        dealer.retirarDineroJugador(j6, 300.0);
        j6.agregarMano(m6);
        m6.recibirCarta(new Carta(PaloCarta.CORAZONES, ValorCarta.CINCO));
        m6.recibirCarta(new Carta(PaloCarta.CORAZONES, ValorCarta.CINCO));
        m6.rendirme();
        System.out.println(j6.toString());
        System.out.println("SALDO: " + j6.getMontoSaldo());
    }
}
