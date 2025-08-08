package ar.edu.unlu.poo.modelo;

public class Dinero {
    private double monto;

    public Dinero(double cantidad){
        this.monto = cantidad;
    }


    //metodo: informa si el jugador tiene dinero.
    public boolean tengoDinero(){
        return monto >= 1.0;
    }


    //metodo: devuelve la cantidad de dinero que posee el objeto.
    public double getMonto(){
        return monto;
    }


    //metodo: informa si el objeto puede transferir la cantidad de dinero pasada por parametro.
    public boolean puedoRealizarLaTransferencia(double cantidad){
        return monto >= cantidad;
    }


    //metodo: actualiza la cantidad de dinero que posee el objeto, dependiendo el signo del monto pasado por parametro.
    public void actualizarMonto(double cantidad){
        monto += cantidad;
    }


    //metodo: devuelve un String personalizado que posee la información del Dinero actual.
    @Override
    public String toString(){
        return String.format("$%.2f ", monto);
    }
}
