package ar.edu.unlu.poo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private List<ManoJugador> manos;
    private Dinero saldo;
    private String nombre;
    private double maximoHistorico;

    public Jugador(String nombre, double saldo){
        this.nombre = nombre;
        this.saldo = new Dinero(saldo);
        this.manos = new ArrayList<ManoJugador>();
        this.maximoHistorico = saldo;
    }


    //metodo: devuelve el nombre del jugador.
    public String getNombre(){
        return nombre;
    }


    //metodo: devuelve la lista de manos que posee el jugador.
    public List<ManoJugador> getManos(){
        return manos;
    }


    //metodo: devuelve el monto que posee actualmente el jugador.
    public double getMontoSaldo(){
        return saldo.getMonto();
    }


    //metodo: devuelve el maximo historico que posee el jugador.
    public double getMaximoHistorico(){
        return maximoHistorico;
    }


    //metodo: actualiza el maximo historico del jugador.
    public void actualizarMaximoHistorico(){
        if(maximoHistorico < saldo.getMonto()){
            maximoHistorico = saldo.getMonto();
        }
    }


    //metodo: informa si el jugador puede realizar cierta transferencia de dinero.
    public boolean transferenciaRealizable(double cantidad){
        return saldo.puedoRealizarLaTransferencia(cantidad);
    }


    //metodo: el jugador actualiza su saldo, segun la cantidad pasada por parametro, sea esta negativa o positiva.
    public void recibirDinero(double cantidad){
        saldo.actualizarMonto(cantidad);
    }


    //metodo: informa si el jugador no posee dinero, para borrarlo del juego.
    public boolean jugadorPerdio(){
        return !saldo.tengoDinero();
    }


    //metodo: agrega una mano en la ultima posicion de la lista de manos.
    public void agregarMano(ManoJugador mano){
        manos.add(mano);
    }


    //metodo: agrega una mano en cierta posicion pasada por parametro.
    public void agregarManoEnPosicion(int index, ManoJugador mano){
        manos.add(index, mano);
    }


    //metodo: limpia las manos que posee el jugador.
    public void limpiarManos(){
        manos.clear();
    }


    //metodo: remueve una mano pasada por parametro.
    public void removerMano(ManoJugador mano){
        manos.remove(mano);
    }


    //metodo: devuelve un string personalizado que representa la información del Jugador.
    @Override
    public String toString(){
        String s = "JUGADOR " + nombre + "    SALDO: $" + getMontoSaldo() + " :\n";
        int i = 1;

        for(ManoJugador m: manos){
            s += " --- MANO " + i + ": ";
            s += m.toString();
            i++;
        }

        return s + "\n\n";
    }
}
