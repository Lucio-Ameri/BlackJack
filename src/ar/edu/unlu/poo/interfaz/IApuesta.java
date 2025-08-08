package ar.edu.unlu.poo.interfaz;

public interface IApuesta {

    double getMontoApostado();
    boolean estaAsegurado();
    double getMontoSeguro();
    boolean gananciasCalculadas();
    double getGanancias();

    @Override
    String toString();
}
