package ar.edu.unlu.poo.interfaz;

import ar.edu.unlu.poo.modelo.estado.EstadoDeLaMano;

import java.util.List;

public interface IManoJugador {

    IApuesta getInterfazApuesta();
    List<ICarta> getInterfazCarta();
    EstadoDeLaMano getEstado();
    int getTotalMano();

    @Override
    String toString();
}
