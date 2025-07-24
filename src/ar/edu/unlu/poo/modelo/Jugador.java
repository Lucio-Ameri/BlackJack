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

    public String getNombre(){
        return nombre;
    }

    public List<ManoJugador> getManos(){
        return manos;
    }

    public double getMontoSaldo(){
        return saldo.getMonto();
    }

    public double getMaximoHistorico(){
        return maximoHistorico;
    }

    public void actualizarMaximoHistorico(){
        if(maximoHistorico < saldo.getMonto()){
            maximoHistorico = saldo.getMonto();
        }
    }

    public boolean transferenciaRealizable(double cantidad){
        return saldo.puedoRealizarLaTransferencia(cantidad);
    }

    public void recibirDinero(double cantidad){
        saldo.actualizarMonto(cantidad);
    }

    public boolean jugadorPerdio(){
        return !saldo.tengoDinero();
    }

    public void agregarMano(ManoJugador mano){
        manos.add(mano);
    }

    public void agregarManoEnPosicion(int index, ManoJugador mano){
        manos.add(index, mano);
    }

    public void limpiarManos(){
        manos.clear();
    }

    public void removerMano(ManoJugador mano){
        manos.remove(mano);
    }

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
