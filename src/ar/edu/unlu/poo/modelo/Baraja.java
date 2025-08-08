package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.estado.PaloCarta;
import ar.edu.unlu.poo.modelo.estado.ValorCarta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {
    private List<Carta> baraja;

    public Baraja(){
        this.baraja = new ArrayList<Carta>();
        generarLaBaraja();
    }


    //metodo: informa si la baraja esta vacia.
    private boolean barajaVacia(){
        return baraja.isEmpty();
    }


    //metodo: genera las cartas dentro de la baraja.
    private void generarLaBaraja(){
        generarCartas();
        mezclarCartas();
    }


    //metodo: genera 8 pares de cartas dentro de la baraja.
    private void generarCartas(){
        PaloCarta[] palos = PaloCarta.values();
        ValorCarta[] valores = ValorCarta.values();

        for(int i = 0; i < 8; i++){
            for(PaloCarta p: palos){
                for(ValorCarta v: valores){

                    if(p != PaloCarta.OCULTO && v != ValorCarta.OCULTO){
                        baraja.add(new Carta(p, v));
                    }
                }
            }
        }
    }


    //metodo: mezcla las cartas dentro de la baraja.
    private void mezclarCartas(){
        Collections.shuffle(baraja);
    }


    //metodo: remueve la primera carta de la baraja, para poder repartirla.
    public Carta repartirCarta(){
        if(barajaVacia()){
            generarLaBaraja();
        }

        return baraja.remove(0);
    }
}
