package ar.edu.unlu.poo.interfaz;

import ar.edu.unlu.poo.modelo.estado.PaloCarta;
import ar.edu.unlu.poo.modelo.estado.ValorCarta;

public interface ICarta {

    PaloCarta getTipoDePalo();
    ValorCarta getTipoDeValor();

    @Override
    String toString();
}
