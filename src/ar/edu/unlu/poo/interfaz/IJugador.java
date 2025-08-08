package ar.edu.unlu.poo.interfaz;

import java.util.List;

public interface IJugador {

    String getNombre();
    List<IManoJugador> getInterfazManos();
    double getSaldoJugador();
    double getMaximoHistorico();
    boolean perdio();

    @Override
    String toString();
}
