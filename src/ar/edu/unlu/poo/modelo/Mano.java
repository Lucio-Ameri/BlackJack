package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.ICarta;
import ar.edu.unlu.poo.modelo.estado.EstadoDeLaMano;
import ar.edu.unlu.poo.modelo.evento.Eventos;

import java.util.ArrayList;
import java.util.List;

public abstract class Mano {
    private List<Carta> cartas;
    private EstadoDeLaMano estado;
    private int totalMano;

    public Mano(){
        this.cartas = new ArrayList<Carta>();
        this.estado = EstadoDeLaMano.TURNO_INICIAL;
        this.totalMano = 0;
    }


    //metodo: devuelve la lista de cartas de la mano.
    public List<Carta> getCartas(){
        return cartas;
    }


    //metodo: devuelve una lista de interfaces de cartas.
    public List<ICarta> getInterfazCarta(){
        List<ICarta> cartasI = new ArrayList<ICarta>();
        for(Carta c: cartas){
            cartasI.add(c);
        }

        return cartasI;
    }


    //metodo: devuelve el estado que posee la mano actualmente.
    public EstadoDeLaMano getEstado(){
        return estado;
    }


    //metodo: devuelve el total que posee la mano actualmente.
    public int getTotalMano(){
        return totalMano;
    }


    //metodo: cambia el estado de la mano por uno nuevo pasado por parametro.
    protected void cambiarEstadoDeLaMano(EstadoDeLaMano en){
        estado = en;
    }


    //metodo: informa si la mano se encuentra en el turno inicial.
    public boolean turnoInicial(){
        return estado == EstadoDeLaMano.TURNO_INICIAL;
    }


    //metodo: calcula el total de la mano y actualiza el estado de la misma segun el total obtenido.
    protected void calcularTotal(){
        int total = 0;
        int cantAs = 0;

        for(Carta c: cartas){

            if(c.esAs()){
                cantAs ++;
            }

            total += c.getValorNumericoCarta();
        }

        while(total > 21 && cantAs > 0){
            cantAs --;
            total -= 10;
        }

        totalMano = total;
        actualizarEstadoDeLaMano(totalMano);
    }


    //metodo: funciones abstractas de la mano.
    protected abstract void actualizarEstadoDeLaMano(int total);
    public abstract void recibirCarta(Carta c);
}
