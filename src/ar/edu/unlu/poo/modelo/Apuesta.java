package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.estados.EstadoDeLaApuesta;
import ar.edu.unlu.poo.modelo.estados.EstadoDeLaMano;

public class Apuesta {
    private Dinero apostado;
    private Dinero seguro;
    private Dinero ganancias;
    private EstadoDeLaApuesta estado;

    public Apuesta(double cantidad){
        this.apostado = new Dinero(cantidad);
        this.seguro = null;
        this.ganancias = null;
        this.estado = EstadoDeLaApuesta.JUGANDO;
    }


    //metodo: devuelve el monto que el jugador aposto en la mano.
    public double getMontoApostado(){
        return apostado.getMonto();
    }


    //metodo: informa si la mano esta asegurado.
    public boolean estaAsegurado(){
        return seguro != null;
    }


    //metodo: devuelve el monto del seguro que aposto el jugador.
    public double getSeguroApostado(){
        return seguro.getMonto();
    }


    //metodo: informa si ya se calcularon las ganancias de la mano.
    public boolean seCalcularonLasGanancias(){
        return estado != EstadoDeLaApuesta.JUGANDO;
    }


    //metodo: devuelve el monto de las ganancias que la mano obtuvo.
    public double getGanancias(){
        return ganancias.getMonto();
    }


    //metodo: asegura la mano, asignando la mitad del monto apostado como seguro.
    public void asegurarme(){
        this.seguro = new Dinero(getMontoApostado() / 2.0);
    }


    //metodo: dobla la apuesta actual que posee la mano.
    public void doblar(){
        apostado.actualizarMonto(apostado.getMonto());
    }


    //metodo: funcion que obtiene el resultado de calcular las ganancias de la mano, segun el resultado de la misma contra la del dealer.
    public void calcularGanancias(EstadoDeLaMano estadoD, EstadoDeLaMano estadoJ, int totalD, int totalJ){
        this.ganancias = new Dinero( 0.0);

        if(estadoJ == EstadoDeLaMano.RENDIDA){
            ganancias.actualizarMonto(getMontoApostado() / 2.0);
            estado = EstadoDeLaApuesta.PERDIO;
        }

        else{

            switch(estadoD){
                case BLACKJACK -> {
                    calcularGananciasDealerBlackJack(estadoJ);

                    if(estaAsegurado()){
                        ganancias.actualizarMonto(getMontoApostado() / 2.0);
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
            ganancias.actualizarMonto(getMontoApostado());
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
            ganancias.actualizarMonto(monto + (monto * 1.5));
        }

        else if(estadoJ == EstadoDeLaMano.QUEDADA){
            if(totalD == totalJ){
                estado = EstadoDeLaApuesta.EMPATO;
                ganancias.actualizarMonto(monto);
            }

            else if(totalJ > totalD){
                estado = EstadoDeLaApuesta.GANO;
                ganancias.actualizarMonto(monto * 2.0);
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
                ganancias.actualizarMonto(monto + (monto * 1.5));
            }

            else{
                ganancias.actualizarMonto(monto * 2.0);
            }
        }
    }


    //metodo: devuelve un string personalizado que representa la información de la carta.
    @Override
    public String toString(){
        String situacion = "";

        switch(estado){
            case GANO -> situacion = "GANO!";
            case EMPATO -> situacion = "EMPATO!";
            case PERDIO -> situacion = "PERDIO!";
            case JUGANDO -> situacion = "JUGANDO!";
        }

        if(estado != EstadoDeLaApuesta.JUGANDO) {
            return String.format(" --- MONTO APOSTADO: $.2f --- SEGURO APOSTADO: $.2f --- SITUACION: %s", getMontoApostado(),
                    (estaAsegurado()) ? getSeguroApostado() : 0.0, situacion);
        }

        return String.format(" --- MONTO APOSTADO: $.2f --- SEGURO APOSTADO: $.2f --- SITUACION: %s --- DEALER PAGA: %.2f", getMontoApostado(),
                (estaAsegurado()) ? getSeguroApostado() : 0.0, situacion, (ganancias != null) ? getGanancias() : 0.0);
    }
}
