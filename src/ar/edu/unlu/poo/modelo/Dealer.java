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

    public void definirResultados(List<Jugador> jugadores){
        for(Jugador j: jugadores){
            List<ManoJugador> manos = j.getManos();

            for(ManoJugador m: manos){
                Apuesta envite = m.getEnvite();

                envite.calcularGanancias(mano.getEstado(), m.getEstado(), mano.getTotalMano(), m.getTotalMano());
                j.recibirDinero(envite.getGanancias());

                j.actualizarMaximoHistorico();
            }
        }
    }

    public void retirarManosJugadas(List<Jugador> jugadores){
        for(Jugador j: jugadores){
            j.limpiarManos();
        }
    }

    public void devolverDineroMano(Jugador j, ManoJugador manoJ){
        j.recibirDinero(manoJ.getMontoApostado());
        j.removerMano(manoJ);
    }

    public void retirarDineroJugador(Jugador j, double monto){
        j.recibirDinero(- monto);
    }

    public void eliminarJugador(Jugador j){
        List<ManoJugador> manos = j.getManos();

        for(ManoJugador m: manos){
            j.recibirDinero(m.getMontoApostado());
        }

        j.limpiarManos();
    }

    @Override
    public String toString(){
        String s = "MANO DEALER: ";
        return s += mano.toString();
    }
}
