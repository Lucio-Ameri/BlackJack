package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.estados.EstadoDeLaMano;

import java.util.List;

public class ManoDealer extends Mano{

    public ManoDealer(){
        super();
        cambiarEstadoDeLaMano(EstadoDeLaMano.EN_JUEGO);
    }


    //metodo: informa si la primer carta de la mano es un AS.
    public boolean primerCartaEsAs(){
        List<Carta> cartas = getCartasDeLaMano();
        return cartas.get(0).esAs();
    }


    //metodo: revela todas las cartas de la mano, para poder seguir repartiendose cartas el dealer.
    protected void revelarManoCompleta(){
        List<Carta> cartas = getCartasDeLaMano();

        for(Carta c: cartas){
            if(c.cartaOculta()){
                c.revelarCarta();
            }
        }

        calcularTotal();
    }


    //metodo: recibe una carta, dejandola oculta si es la 2da que recibe.
    @Override
    public void recibirCarta(Carta c){
        List<Carta> cartas = getCartasDeLaMano();

        if(cartas.size() != 1){
            c.revelarCarta();
        }

        cartas.add(c);
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


    //metodo: devuelve un string personalizado que representa la información de la mano.
    @Override
    public String toString(){
        List<Carta> cartas = getCartasDeLaMano();
        String s = "";

        for(Carta c: cartas){
            s += c.toString();
        }

        return s + " --- TOTAL: [ "+ getTotalMano() + " ] " + " --- ESTADO DE LA MANO: " + getEstado() + ".\n\n";
    }
}
