package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.IDealer;
import ar.edu.unlu.poo.interfaz.IManoDealer;

import java.util.List;

public class Dealer implements IDealer {
    private ManoDealer mano;
    private Baraja baraja;

    public Dealer(){
        this.mano = new ManoDealer();
        this.baraja = new Baraja();
    }


    //metodo: devuelve la mano del dealer.
    public ManoDealer getMano(){
        return mano;
    }


    //metodo: devuelve una interfaz de la mano del dealer.
    @Override
    public IManoDealer getInterfazMano(){
        return mano;
    }


    //metodo: informa si la condicion para asegurar una mano se cumple.
    public boolean condicionSeguro(){
        return mano.primeraCartaEsAs();
    }


    //metodo: revela la mano por completo del dealer llamano al metodo de ManoDealer.
    public void revelarMano(){
        mano.revelarManoCompleta();
    }


    //metodo: reparte una carta del mazo.
    public Carta repartirCarta(){
        return baraja.repartirCarta();
    }


    //metodo: define los resultados de la ronda, repartiendo las ganancias pertinentes a cada jugador.
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


    //metodo: retira todas las manos de los jugadores luego de que acabe la ronda.
    public void retirarManosJugadas(List<Jugador> jugadores){
        for(Jugador j: jugadores){
            j.limpiarManos();
        }
    }


    //metodo: devuelve el dinero de cierta apuesta al jugador.
    public void devolverDinero(Jugador j, ManoJugador m){
        j.actualizarSaldo(m.getApuesta().getMontoApostado());
        j.removerMano(m);
    }


    //metodo: funcion que retira el dinero del jugador dada una apuesta.
    public void retirarDineroJugador(Jugador j, double monto){
        j.actualizarSaldo(- monto);
    }


    //metodo: devuelve el dinero de todas las manos apostadas del jugador para su futura eliminacion de la mesa.
    public void eliminarJugador(Jugador j){
        List<ManoJugador> manos = j.getManos();

        for(ManoJugador m: manos){
            j.actualizarSaldo(m.getApuesta().getMontoApostado());
        }

        j.limpiarManos();
    }


    //metodo: devuelve un String personalizado que posee la información del dealer.
    @Override
    public String toString(){
        String s = String.format("DEALER:\nMANO DEALER: %s\n", mano.toString());
        return s;
    }
}
