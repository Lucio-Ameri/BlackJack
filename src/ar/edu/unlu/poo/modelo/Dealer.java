package ar.edu.unlu.poo.modelo;

import java.util.List;

public class Dealer {
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


    //metodo: informa si se cumple o no la condicion para poder asegurar una mano.
    public boolean condicionSeguro(){
        return mano.primerCartaEsAs();
    }


    //metodo: revela todas las cartas del dealer.
    public void revelarMano(){
        mano.revelarManoCompleta();
    }


    //metodo: dealer se encarga de devolver la carta de la baraja, como si la estuviera repartiendo.
    public Carta repartirCarta(){
        return baraja.repartir();
    }


    //metodo: calcula el resultado y las ganancias de cada jugador, segun su mano y las de los jugadores.
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


    //metodo: retira todas las manos de los jugadores para poder empezar una nueva ronda o para que el jugador pueda irse.
    public void retirarManosJugadas(List<Jugador> jugadores){
        for(Jugador j: jugadores){
            j.limpiarManos();
        }
    }


    //metodo: se encarga de devolver el dinero que aposto en cierta mano si es que el jugador desea eliminar una apuesta.
    public void devolverDineroMano(Jugador j, ManoJugador manoJ){
        j.recibirDinero(manoJ.getMontoApostado());
        j.removerMano(manoJ);
    }


    //metodo: se encarga de retirar saldo del jugador segun lo que el mismo va a apostar.
    public void retirarDineroJugador(Jugador j, double monto){
        j.recibirDinero(- monto);
    }


    //metodo: se encarga de remover las apuestas, devolver dinero y limpiar la lista de manos, ya que el jugador quiere irse de la ronda.
    public void eliminarJugador(Jugador j){
        List<ManoJugador> manos = j.getManos();

        for(ManoJugador m: manos){
            j.recibirDinero(m.getMontoApostado());
        }

        j.limpiarManos();
    }


    //metodo: devuelve un string personalizado que representa la información del DEALER.
    @Override
    public String toString(){
        String s = "MANO DEALER: ";
        return s += mano.toString();
    }
}
