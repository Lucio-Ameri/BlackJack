package ar.edu.unlu.poo.modelo;

import java.util.ArrayList;
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

    }

    private void mezclarCartas(){

    }

    public Carta repartir(){
        return baraja.remove(0);
    }
}
