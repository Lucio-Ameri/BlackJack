package ar.edu.unlu.poo.interfaz;

import ar.edu.unlu.poo.modelo.estado.EstadoDeLaMano;

import java.util.List;

public interface IManoDealer {

    List<ICarta> getInterfazCarta();
    int getTotalMano();
    EstadoDeLaMano getEstado();


    @Override
    String toString();
}
