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

    private boolean barajaVacia(){
        return baraja.isEmpty();
    }

    private void generarBaraja(){
        if(barajaVacia()) {
            baraja.clear();
        }

        crearCartas();
        mezclarCartas();
    }

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

    private void mezclarCartas(){
        Collections.shuffle(baraja);
    }

    public Carta repartir(){
        return baraja.remove(0);
    }
}
