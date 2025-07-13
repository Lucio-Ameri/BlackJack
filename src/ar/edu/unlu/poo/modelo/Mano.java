package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.estados.EstadoDeLaMano;

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


    //metodo: devuelve la lista de cartas que posee la mano.
    public List<Carta> getCartasDeLaMano(){
        return cartas;
    }


    //metodo: devuelve el puntaje total que posee la mano.
    public int getTotalMano(){
        return totalMano;
    }


    //metodo: devuelve el estado de la mano.
    public EstadoDeLaMano getEstado(){
        return estado;
    }


    //metodo: informa si la mano esta en el turno inicial.
    public boolean turnoInicial(){
        return estado == EstadoDeLaMano.TURNO_INICIAL;
    }


    //metodo: metodo que permite cambiar el estado a uno pasado por parametro.
    protected void cambiarEstadoDeLaMano(EstadoDeLaMano en){
        estado = en;
    }


    //metodo: calcula el total de la mano según el caso. Si se pasa y posee un as, hace que el mismo valga 1 en vez de 11.
    protected void calcularTotal(){
        int total = 0;
        int cantAs = 0;

        for(Carta c: cartas){

            if(!c.cartaOculta()){
                if(c.esAs()){
                    cantAs ++;
                }
                total += c.getValorCarta();
            }
        }

        while(total > 21 && cantAs > 0){
            cantAs --;
            total -= 10;
        }

        totalMano = total;
        actualizarEstadoDeLaMano(totalMano);
    }

    protected abstract void actualizarEstadoDeLaMano(int total);
    public abstract void recibirCarta(Carta c);
}
