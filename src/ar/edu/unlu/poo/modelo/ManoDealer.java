package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.IManoDealer;
import ar.edu.unlu.poo.modelo.estado.EstadoDeLaMano;

import java.util.List;

public class ManoDealer extends Mano implements IManoDealer {

    public ManoDealer(){
        super();
    }


    //metodo: informa si la primera carta del dealer es un as.
    public boolean primeraCartaEsAs(){
        return getCartas().get(0).esAs();
    }


    //metodo: revela todas las cartas del dealer para poder empezar a jugar.
    public void revelarManoCompleta(){
        List<Carta> cartas = getCartas();

        for(Carta c: cartas){
            if(c.cartaOculta()){
                c.revelarCarta();
            }
        }

        calcularTotal();
    }


    //metodo: recibe una carta, revelandola directamente si cumple la condicion.
    @Override
    public void recibirCarta(Carta c){
        List<Carta> cartas = getCartas();

        if(cartas.size() != 1){
            c.revelarCarta();
        }

        cartas.add(c);

        if(cartas.size() == 3){
            cambiarEstadoDeLaMano(EstadoDeLaMano.EN_JUEGO);
        }

        calcularTotal();
    }


    //metodo: actualiza el estado de la mano, segun las reglas del dealer.
    @Override
    protected void actualizarEstadoDeLaMano(int total){
        if(total > 21){
            cambiarEstadoDeLaMano(EstadoDeLaMano.PASADA);
        }

        else if(total > 16){
            if(turnoInicial() && total == 21){
                cambiarEstadoDeLaMano(EstadoDeLaMano.BLACKJACK);
            }

            else{
                cambiarEstadoDeLaMano(EstadoDeLaMano.QUEDADA);
            }
        }

        else{
            cambiarEstadoDeLaMano(getEstado());
        }
    }


    //metodo: devuelve un string personalizado que representa la información de la ManoDealer.
    @Override
    public String toString(){
        List<Carta> cartas = getCartas();
        String s = "";

        for(Carta c: cartas){
            s += c.toString();
        }

        return String.format("%s    TOTAL MANO [ %d ] --- ESTADO DE LA MANO: %s .\n", s, getTotalMano(), getEstado());
    }
}
