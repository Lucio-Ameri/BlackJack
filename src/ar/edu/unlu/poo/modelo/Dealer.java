package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.IManoDealer;

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

    public IManoDealer getInterfazMano(){
        return mano;
    }

    public boolean condicionSeguro(){
        return mano.primeraCartaEsAs();
    }

    public void revelarMano(){
        mano.revelarManoCompleta();
    }

    public Carta repartirCarta(){
        return baraja.repartirCarta();
    }

    public void definirResultados(List<Jugador> jugadores){
        for(Jugador j: jugadores){
            List<ManoJugador> manos = j.getManos();

            for(ManoJugador m: manos){
                Apuesta envite = m.getApuesta();
                envite.calcularGanancias(mano.getEstado(), m.getEstado(), mano.getTotalMano(), m.getTotalMano());
                j.actualizarSaldo(envite.getGanancias());
                j.actualizarMaximoHistorico();
            }
        }
    }

    public void retirarManosJugadas(List<Jugador> jugadores){
        for(Jugador j: jugadores){
            j.limpiarManos();
        }
    }

    public void devolverDinero(Jugador j, ManoJugador m){
        j.actualizarSaldo(m.getApuesta().getMontoApostado());
        j.removerMano(m);
    }

    public void retirarDineroJugador(Jugador j, double monto){
        j.actualizarSaldo(- monto);
    }

    public void eliminarJugador(Jugador j){
        List<ManoJugador> manos = j.getManos();

        for(ManoJugador m: manos){
            j.actualizarSaldo(m.getApuesta().getMontoApostado());
        }

        j.limpiarManos();
    }

    @Override
    public String toString(){
        String s = String.format("DEALER:\nMANO DEALER: %s\n", mano.toString());
        return s;
    }
}
