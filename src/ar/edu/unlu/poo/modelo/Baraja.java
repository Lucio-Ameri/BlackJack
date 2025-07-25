package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.estados.PaloCarta;
import ar.edu.unlu.poo.modelo.estados.ValorCarta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {
    private List<Carta> baraja;

    public Baraja(){
        this.baraja = new ArrayList<Carta>();
        generarBaraja();
    }


    //metodo: informa si la baraja esta vacia o no.
    private boolean barajaVacia(){
        return baraja.isEmpty();
    }


    //metodo: genera una baraja con nuevas cartas.
    private void generarBaraja(){
        if(barajaVacia()) {
            baraja.clear();
        }

        crearCartas();
        mezclarCartas();
    }


    //metodo: genera 8 mazos de cartas en la baraja.
    private void crearCartas(){
        ValorCarta[] valores = ValorCarta.values();
        PaloCarta[] palos = PaloCarta.values();

        for(int i = 0; i < 8; i++){
            for(PaloCarta p: palos){
                for(ValorCarta v: valores){

                    baraja.add(new Carta(p, v));
                }
            }
        }
    }


    //metodo: mezcla las cartas de la baraja.
    private void mezclarCartas(){
        Collections.shuffle(baraja);
    }


    //metodo: remueve y devuelve la primer carta de la baraja.
    public Carta repartir(){
        return baraja.remove(0);
    }
}
