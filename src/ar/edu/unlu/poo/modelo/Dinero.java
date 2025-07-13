package ar.edu.unlu.poo.modelo;

public class Dinero {
    private double monto;

    public Dinero(double cantidad){
        this.monto = cantidad;
    }


    //metodo: informa si el monto que posee el objeto es "valido".
    public boolean tengoDinero(){
        return monto >= 1.0;
    }


    //metodo: devuelve el monto que posee el objeto.
    public double getMonto(){
        return monto;
    }


    //metodo: informa si se podria realizar la transferencia de una cierta cantidad.
    public boolean puedoRealizarLaTransferencia(double cantidad){
        return monto >= cantidad;
    }


    //metodo: actualiza el monto segun la canitdad pasada por parametro.
    public void actualizarMonto(double cantidad){
        monto += cantidad;
    }
}
