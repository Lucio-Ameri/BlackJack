package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.IJugador;
import ar.edu.unlu.poo.interfaz.IManoJugador;

import java.util.ArrayList;
import java.util.List;

public class Jugador implements IJugador {
    private List<ManoJugador> manos;
    private Dinero saldo;
    private String nombre;
    private double maximoHistorico;

    public Jugador(String nombre, double montoInicial){
        this.manos = new ArrayList<ManoJugador>();
        this.saldo = new Dinero(montoInicial);
        this.nombre = nombre;
        this.maximoHistorico = montoInicial;
    }


    //metodo: devuelve el nombre del jugador.
    @Override
    public String getNombre(){
        return nombre;
    }


    //metodo: devuelve la lista de manos que posee el jugador.
    public List<ManoJugador> getManos(){
        return manos;
    }


    //metodo: devuelve la lista de manos interfaces del jugador.
    @Override
    public List<IManoJugador> getInterfazManos(){
        List<IManoJugador> manosI = new ArrayList<IManoJugador>();

        for(ManoJugador m: manos){
            manosI.add(m);
        }

        return manosI;
    }


    //metodo: devuelve el saldo que posee el jugador.
    @Override
    public double getSaldoJugador(){
        return saldo.getMonto();
    }


    //metodo: devuelve el puntaje maximo que hizo el jugador.
    @Override
    public double getMaximoHistorico(){
        return maximoHistorico;
    }


    //metodo: actualiza el puntaje maximo historico que hizo el jugador, si es que su saldo es mayor al anterior mencionado.
    public void actualizarMaximoHistorico(){
        double monto = getSaldoJugador();
        if(maximoHistorico < monto){
            maximoHistorico = monto;
        }
    }



    //metodo: informa si el jugador puede realizar la transferencia deseada o no.
    public boolean transferenciaRealizable(double cantidad){
        return saldo.puedoRealizarLaTransferencia(cantidad);
    }


    //metodo: actualizo el saldo del jugador, ya sea con un monto negativo o positivo.
    public void actualizarSaldo(double monto){
        saldo.actualizarMonto(monto);
    }


    //metodo: informa si el jugador perdio o no.
    @Override
    public boolean perdio(){
        return !saldo.tengoDinero();
    }


    //metodo: agrega una mano nueva al final de la lista.
    public void agregarMano(ManoJugador m){
        manos.add(m);
    }


    //metodo: agrega una mano nueva en una posicion indicada por parametro.
    public void agregarManoEnPosicion(int index, ManoJugador m){
        manos.add(index, m);
    }


    //metodo: remueve una mano del jugador.
    public void removerMano(ManoJugador m){
        manos.remove(m);
    }


    //metodo: remueve todas las manos que estan en la lista.
    public void limpiarManos(){
        manos.clear();
    }


    //metodo: devuelve un String personalizado que posee la información del Jugador actual.
    @Override
    public String toString(){
        String s = String.format("JUGADOR %s  -  SALDO: %s -  MAXIMO HISTORICO: %.2f \n", nombre, saldo.toString(), maximoHistorico);
        int i = 1;
        for(ManoJugador m: manos){
            s += String.format("MANO %d: %s\n", i, m.toString());
            i++;
        }

        return s;
    }
}
