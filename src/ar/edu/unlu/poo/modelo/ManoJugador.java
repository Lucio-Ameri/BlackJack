package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.estados.EstadoDeLaMano;

import java.util.List;

public class ManoJugador extends Mano{
    private Apuesta envite;

    public ManoJugador(double monto){
        super();
        this.envite = new Apuesta(monto);
    }


    //metodo: devuelve la apuesta.
    public Apuesta getEnvite(){
        return envite;
    }


    //metodo: devuelve el monto apostado hasta ahora de la mano.
    public double getMontoApostado(){
        return envite.getMontoApostado();
    }


    //metodo: informa si la mano ya se encuentra asegurada o no.
    public boolean manoAsegurada(){
        return envite.estaAsegurado();
    }


    //metodo: devuelve el monto del seguro apostado.
    public double getMontoSeguro(){
        if(!manoAsegurada()){
            return 0.0;
        }

        return envite.getSeguroApostado();
    }


    //metodo: informa si las ganancias fueron calculadas.
    public boolean gananciasCalculadas(){
        return envite.seCalcularonLasGanancias();
    }


    //metodo: devuelve el monto de dinero que se gano/devolvio de las apuestas realizadas en la mano.
    public double getGanancias(){
        if(!gananciasCalculadas()){
            return -1.0;
        }

        return envite.getGanancias();
    }


    //metodo: informa si la mano puede ser separada o no.
    public boolean condicionParaSepararMano(){
        List<Carta> cartas = getCartasDeLaMano();
        return cartas.get(0).cartasSimilares(cartas.get(cartas.size() - 1));
    }


    //metodo: cambia el estado de la mano a quedada.
    public void quedarme(){
        cambiarEstadoDeLaMano(EstadoDeLaMano.QUEDADA);
    }


    //metodo: cambia el estado de la mano a quedada.
    public void rendirme(){
        cambiarEstadoDeLaMano(EstadoDeLaMano.RENDIDA);
    }


    //metodo: asegura la mano.
    public void asegurarme(){
        envite.asegurarme();
    }


    //metodo: dobla la apuesta de la mano, recibiendo solo una carta mas. Si el estado de la misma sigue siendo En_Juego, lo cambia a quedada.
    public void doblarMano(Carta c){
        envite.doblar();
        recibirCarta(c);

        if(getEstado() == EstadoDeLaMano.EN_JUEGO){
            quedarme();
        }
    }


    //metodo: separa la mano en 2.
    public ManoJugador separarMano(){
        List<Carta> cartas = getCartasDeLaMano();

        ManoJugador nM = new ManoJugador(getMontoApostado());
        nM.recibirCarta(cartas.remove(cartas.size() - 1));

        this.cambiarEstadoDeLaMano(EstadoDeLaMano.TURNO_INICIAL);
        this.calcularTotal();

        return nM;
    }


    //metodo: cambia el estado de la mano segun el total que posee en el momento.
    @Override
    protected void actualizarEstadoDeLaMano(int total){
        if(total > 21){
            cambiarEstadoDeLaMano(EstadoDeLaMano.PASADA);
        }

        else if(total == 21){
            if(turnoInicial()){
                cambiarEstadoDeLaMano(EstadoDeLaMano.BLACKJACK);
            }

            else{
                cambiarEstadoDeLaMano(EstadoDeLaMano.QUEDADA);
            }
        }

        else{
            cambiarEstadoDeLaMano(getEstado());
        }
    }


    //metodo: recibie una carta, revelandola en el momento, y si el total de cartas ya obtenidas > 2, cambia el estado de la carta y calcula el total.
    @Override
    public void recibirCarta(Carta c){
        List<Carta> cartas = getCartasDeLaMano();

        c.revelarCarta();
        cartas.add(c);

        if(cartas.size() == 3){
            cambiarEstadoDeLaMano(EstadoDeLaMano.EN_JUEGO);
        }

        calcularTotal();
    }


    //metodo: devuelve un string personalizado que representa la información de la mano.
    @Override
    public String toString(){
        List<Carta> cartas = getCartasDeLaMano();
        String s = "";
        for(Carta c: cartas){
            s += c.toString();
        }

        return s + " --- TOTAL MANO: [ " + getTotalMano() + " ]  --- ESTADO DE LA MANO: " + getEstado() + envite.toString() + "\n";
    }
}
