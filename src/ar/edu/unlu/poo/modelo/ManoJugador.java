package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.IApuesta;
import ar.edu.unlu.poo.interfaz.ICarta;
import ar.edu.unlu.poo.interfaz.IManoJugador;
import ar.edu.unlu.poo.modelo.estado.EstadoDeLaMano;

import java.util.List;

public class ManoJugador extends Mano implements IManoJugador {
    private Apuesta envite;

    public ManoJugador(double monto){
        super();
        this.envite = new Apuesta(monto);
    }


    //metodo: devuelve la apuesta.
    public Apuesta getApuesta(){
        return envite;
    }


    //metodo: devuelve una interfaz de la apuesta.
    @Override
    public IApuesta getInterfazApuesta(){
        return envite;
    }


    //metodo: informa si se cumple o no la condicion para poder separar la mano.
    public boolean condicionParaSepararMano(){
        List<Carta> cartas = getCartas();
        return cartas.get(0).cartasSimilares(cartas.get(cartas.size() - 1));
    }


    //metodo: cambia el estado de la mano a quedada.
    public void quedarme(){
        cambiarEstadoDeLaMano(EstadoDeLaMano.QUEDADA);
    }


    //metodo: cambia el estado de la mano a rendida.
    public void rendirme(){
        cambiarEstadoDeLaMano(EstadoDeLaMano.RENDIDA);
    }


    //metodo: asegura la mano.
    public void asegurarme(){
        envite.asegurarse();
    }


    //metodo: dobla la mano y cambia su estado a quedada si la misma no se pasa u obtiene blackjack.
    public void doblarMano(Carta c){
        envite.doblarApuesta();
        recibirCarta(c);

        if(getEstado() == EstadoDeLaMano.EN_JUEGO){
            quedarme();
        }
    }



    public ManoJugador separarMano(){
        List<Carta> cartas = getCartas();

        ManoJugador nM = new ManoJugador(envite.getMontoApostado());
        nM.recibirCarta(cartas.remove(cartas.size() - 1));

        this.cambiarEstadoDeLaMano(EstadoDeLaMano.TURNO_INICIAL);
        this.calcularTotal();

        return nM;
    }


    //metodo: actualiza el estado de la mano segun el total que este posee.
    @Override
    public void actualizarEstadoDeLaMano(int total){
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


    //metodo: recibe una carta y la revela, para luego poder agregarla a la lista de cartas, obteniendo su nuevo total y estado.
    @Override
    public void recibirCarta(Carta c){
        List<Carta> cartas = getCartas();

        c.revelarCarta();
        cartas.add(c);

        if(cartas.size() == 3){
            cambiarEstadoDeLaMano(EstadoDeLaMano.EN_JUEGO);
        }

        calcularTotal();
    }


    //metodo: devuelve un String personalizado que posee la información de la mano actual.
    @Override
    public String toString(){
        List<Carta> cartas = getCartas();
        String s = "";

        for(Carta c: cartas){
            s += c.toString();
        }

        return s + String.format("    TOTAL MANO: [ %d ] --- ESTADO DE LA MANO: %s %s.\n", getTotalMano(), getEstado(), envite.toString());
    }
}
