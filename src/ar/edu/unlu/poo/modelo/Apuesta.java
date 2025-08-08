package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.IApuesta;
import ar.edu.unlu.poo.modelo.estado.EstadoDeLaApuesta;
import ar.edu.unlu.poo.modelo.estado.EstadoDeLaMano;

public class Apuesta implements IApuesta {
    private Dinero apuesta;
    private Dinero seguro;
    private Dinero ganancia;
    private EstadoDeLaApuesta estado;

    public Apuesta(double monto){
        this.apuesta = new Dinero(monto);
        this.seguro = null;
        this.ganancia = null;
        this.estado = EstadoDeLaApuesta.JUGANDO;
    }


    //metodo: devuelve el monto apostado.
    @Override
    public double getMontoApostado(){
        return apuesta.getMonto();
    }


    //metodo: informa si se aposto o no un seguro.
    @Override
    public boolean estaAsegurado(){
        return seguro != null;
    }


    //metodo: devuelve el monto de seguro apostado.
    @Override
    public double getMontoSeguro(){
        return estaAsegurado() ? seguro.getMonto() : 0.0;
    }


    //metodo: apuesta el seguro segun el monto apostado.
    public void asegurarse(){
        seguro = new Dinero(getMontoApostado() / 2.0);
    }


    //metodo: informa si las ganancias fueron calculadas o no.
    @Override
    public boolean gananciasCalculadas(){
        return ganancia != null;
    }


    //metodo: devuelve la ganancia obtenida.
    @Override
    public double getGanancias(){
        return gananciasCalculadas() ? ganancia.getMonto() : -1.0;
    }


    //metodo: dobla la apuesta inicial de la mano.
    public void doblarApuesta(){
        apuesta.actualizarMonto(getMontoApostado());
    }


    //metodo: calcula las ganancias segun el estado de la mano del dealer y el jugador.
    public void calcularGanancias(EstadoDeLaMano estadoD, EstadoDeLaMano estadoJ, int totalD, int totalJ){
        this.ganancia = new Dinero( 0.0);

        if(estadoJ == EstadoDeLaMano.RENDIDA){
            ganancia.actualizarMonto(getMontoApostado() / 2.0);
            estado = EstadoDeLaApuesta.PERDIO;
        }

        else{

            switch(estadoD){
                case BLACKJACK -> {
                    calcularGananciasDealerBlackJack(estadoJ);

                    if(estaAsegurado()){
                        ganancia.actualizarMonto(getMontoSeguro() * 2.0);
                    }
                    break;
                }

                case QUEDADA -> {
                    calcularGananciasDealerSeQuedo(estadoJ, totalD, totalJ);
                    break;
                }

                case PASADA -> {
                    calcularGananciasDealerSePaso(estadoJ);
                    break;
                }
            }
        }
    }


    //metodo: metodo privado que se encarga de calcular las ganancias si el dealer saco blackjack.
    private void calcularGananciasDealerBlackJack(EstadoDeLaMano estadoJ){
        if(estadoJ == EstadoDeLaMano.BLACKJACK){
            estado = EstadoDeLaApuesta.EMPATO;
            ganancia.actualizarMonto(getMontoApostado());
        }

        else{
            estado = EstadoDeLaApuesta.PERDIO;
        }
    }


    //metodo: metodo privado que se encarga de calcular las ganancias si el dealer se quedo.
    private void calcularGananciasDealerSeQuedo(EstadoDeLaMano estadoJ, int totalD, int totalJ){
        double monto = getMontoApostado();

        if(estadoJ == EstadoDeLaMano.BLACKJACK){
            estado = EstadoDeLaApuesta.GANO;
            ganancia.actualizarMonto(monto + (monto * 1.5));
        }

        else if(estadoJ == EstadoDeLaMano.QUEDADA){
            if(totalD == totalJ){
                estado = EstadoDeLaApuesta.EMPATO;
                ganancia.actualizarMonto(monto);
            }

            else if(totalJ > totalD){
                estado = EstadoDeLaApuesta.GANO;
                ganancia.actualizarMonto(monto * 2.0);
            }

            else{
                estado = EstadoDeLaApuesta.PERDIO;
            }
        }

        else{
            estado = EstadoDeLaApuesta.PERDIO;
        }
    }


    //metodo: metodo privado que se encarga de calcular las ganancias si el dealer se paso.
    private void calcularGananciasDealerSePaso(EstadoDeLaMano estadoJ){
        if(estadoJ == EstadoDeLaMano.PASADA){
            estado = EstadoDeLaApuesta.PERDIO;
        }

        else{
            estado = EstadoDeLaApuesta.GANO;
            double monto = getMontoApostado();

            if(estadoJ == EstadoDeLaMano.BLACKJACK){
                ganancia.actualizarMonto(monto + (monto * 1.5));
            }

            else{
                ganancia.actualizarMonto(monto * 2.0);
            }
        }
    }


    //metodo: devuelve un string personalizado que representa la información de la Apuesta.
    @Override
    public String toString(){
        String situacion = "";

        switch(estado){
            case GANO -> situacion = "GANO!";
            case EMPATO -> situacion = "EMPATO!";
            case PERDIO -> situacion = "PERDIO!";
        }

        if(estado == EstadoDeLaApuesta.JUGANDO){
            return String.format("--- MONTO APOSTADO: $%.2f --- SEGURO APOSTADO: $%.2f --- SITUACION: JUGANDO! ", getMontoApostado(), getMontoSeguro());
        }

        return String.format("--- MONTO APOSTADO: $%.2f --- SEGURO APOSTADO: $%.2f --- SITUACION: %s --- DEALER PAGA: %.2f ", getMontoApostado(), getMontoSeguro(), situacion, getGanancias());
    }
}
