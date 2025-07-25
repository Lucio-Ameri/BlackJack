package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.acciones.Accion;
import ar.edu.unlu.poo.modelo.estados.EstadoDeLaMano;
import ar.edu.unlu.poo.modelo.estados.EstadoDeLaMesa;
import ar.edu.unlu.poo.modelo.eventos.Eventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mesa {
    private List<Jugador> inscriptos;
    private HashMap<Jugador, Boolean> confirmados;
    private EstadoDeLaMesa estado;
    private Dealer dealer;
    private Jugador turnoActual;
    private int lugaresDisponibles;

    public Mesa(){
        this.inscriptos = new ArrayList<Jugador>();
        this.confirmados = new HashMap<Jugador, Boolean>();
        this.estado = EstadoDeLaMesa.ACEPTANDO_INSCRIPCIONES;
        this.dealer = new Dealer();
        this.turnoActual = null;
        this.lugaresDisponibles = 7;
    }

    private void cambiarEstadoDeLaMesa(EstadoDeLaMesa en){
        estado = en;
    }

    public boolean hayLugaresDisponibles(){
        return lugaresDisponibles > 0;
    }

    private void reiniciarConfirmados(){
        if(!confirmados.isEmpty()) {
            for (Map.Entry<Jugador, Boolean> valoresContenidos : confirmados.entrySet()) {
                valoresContenidos.setValue(!valoresContenidos.getValue());
            }
        }
    }

    public void confirmarParticipacion(Jugador j){
        if(!confirmados.get(j)){
            confirmados.put(j, true);
            actualizarEstadoDeLaMesa();
        }
    }

    private boolean todosConfirmaron(){
        if(confirmados.isEmpty()){
            return false;
        }

        for(boolean valor : confirmados.values()){
            if(!valor){
                return false;
            }
        }

        return true;
    }

    private boolean esTurnoDeEsteJugador(Jugador j){
        if(turnoActual != null){
            return j == turnoActual;
        }

        return false;
    }

    private void actualizarEstadoDeLaMesa(){
        switch(estado){
            case ACEPTANDO_INSCRIPCIONES -> {
                if(!hayLugaresDisponibles() || todosConfirmaron()){
                    reiniciarConfirmados();
                    cambiarEstadoDeLaMesa(EstadoDeLaMesa.REPARTIENDO_CARTAS);
                    //notificar cambio de estado para actualizar vista.
                }

                break;
            }

            case REPARTIENDO_CARTAS -> {
                if(todosConfirmaron()){
                    repartirLasCartasIniciales();
                    turnoActual = inscriptos.get(0);
                    cambiarEstadoDeLaMesa(EstadoDeLaMesa.TURNO_JUGADOR);
                    //notificar cambio de estado para actualizar vista e indicar el turno del jugador actual.
                }

                break;
            }

            case TURNO_JUGADOR -> {
                if(turnoActual == null){
                    cambiarEstadoDeLaMesa(EstadoDeLaMesa.TURNO_DEALER);
                    reiniciarConfirmados();
                    //notificar cambio de estado para actualizar vista e indicar que empieza el turno del dealer.
                }

                else{
                    //notificar que cambio el turno al siguiente jugador.
                }

                break;
            }

            case TURNO_DEALER -> {
                empezarTurnoDelDealer();
                cambiarEstadoDeLaMesa(EstadoDeLaMesa.REPARTIENDO_GANANCIAS);

                reiniciarConfirmados();
                //notificar cambio de estado para actualizar vista e indicar que se repartieron las ganancias.

                break;
            }

            case REPARTIENDO_GANANCIAS -> {
                cambiarEstadoDeLaMesa(EstadoDeLaMesa.FINALIZANDO_RONDA);
                dealer.retirarManosJugadas(inscriptos);
                lugaresDisponibles = 7;
                //notificar cambio de estado para actualizar vista y preguntar si el jugador desea jugar otra ronda.

                break;
            }

            case FINALIZANDO_RONDA -> {
                if(todosConfirmaron()){
                    cambiarEstadoDeLaMesa(EstadoDeLaMesa.ACEPTANDO_INSCRIPCIONES);
                    reiniciarConfirmados();
                    //notificar cambio de estado para actualizar vista.
                }

                break;
            }
        }
    }

    private void repartirLasCartasIniciales(){
        ManoDealer manoD = dealer.getMano();

        for(int i = 0; i < 2; i++){

            for(Jugador j: inscriptos){
                List<ManoJugador> manos = j.getManos();

                for(ManoJugador m: manos){
                    m.recibirCarta(dealer.repartirCarta());
                }
            }

            manoD.recibirCarta(dealer.repartirCarta());
        }
    }

    private boolean jugaronTodosLosJugadores(){
        return inscriptos.size() == (inscriptos.indexOf(turnoActual) + 1);
    }

    private void pasarTurnoAlSiguienteJugador(){
        if(jugaronTodosLosJugadores()){
            turnoActual = null;
        }

        else{
            int index = inscriptos.indexOf(turnoActual) + 1;
            turnoActual = inscriptos.get(index);
        }

        actualizarEstadoDeLaMesa();
    }

    private void empezarTurnoDelDealer(){
        dealer.revelarMano();
        ManoDealer mano = dealer.getMano();

        while(mano.getEstado() == EstadoDeLaMano.EN_JUEGO){
            mano.recibirCarta(dealer.repartirCarta());
        }

        dealer.definirResultados(inscriptos);
    }

    public void confirmarNuevaParticipacion(Jugador j, double monto, boolean participo){
        if(participo){
            dealer.retirarDineroJugador(j, monto);
            j.agregarMano(new ManoJugador(monto));
            confirmarParticipacion(j);
            lugaresDisponibles --;
        }

        else{
            inscriptos.remove(j);
            confirmados.remove(j);

            //remover al jugador de observadores.
        }

        actualizarEstadoDeLaMesa();
    }

    public Eventos inscribirJugadorNuevo(Jugador j, double monto){
        if(!inscriptos.contains(j)){

            if(estado == EstadoDeLaMesa.ACEPTANDO_INSCRIPCIONES){

                if(hayLugaresDisponibles()){
                    lugaresDisponibles --;
                    inscriptos.add(j);
                    confirmados.put(j, false);

                    dealer.retirarDineroJugador(j, monto);
                    j.agregarMano(new ManoJugador(monto));

                    actualizarEstadoDeLaMesa();
                    return Eventos.ACCION_REALIZADA;
                }

                return Eventos.SIN_LUGARES_DISPONIBLES;
            }

            return Eventos.LA_MESA_YA_INICIO;
        }

        return Eventos.JUGADOR_YA_INSCRIPTO;
    }

    public Eventos apostarOtraMano(Jugador j, double monto){
        if(!confirmados.get(j)){

            if(estado == EstadoDeLaMesa.ACEPTANDO_INSCRIPCIONES){

                if(hayLugaresDisponibles()){
                    lugaresDisponibles --;
                    j.agregarMano(new ManoJugador(monto));
                    dealer.retirarDineroJugador(j, monto);

                    actualizarEstadoDeLaMesa();
                    return Eventos.ACCION_REALIZADA;
                }

                return Eventos.SIN_LUGARES_DISPONIBLES;
            }

            return Eventos.LA_MESA_YA_INICIO;
        }

        return Eventos.JUGADOR_CONFIRMADO;
    }

    public Eventos retirarUnaMano(Jugador j, ManoJugador mano){
        if(!confirmados.get(j)){

            if(estado == EstadoDeLaMesa.ACEPTANDO_INSCRIPCIONES){

                if(j.getManos().size() > 1){
                    dealer.devolverDineroMano(j, mano);
                    lugaresDisponibles ++;

                    return Eventos.ACCION_REALIZADA;
                }

                return Eventos.ULTIMA_MANO;
            }

            return Eventos.LA_MESA_YA_INICIO;
        }

        return Eventos.JUGADOR_CONFIRMADO;
    }

    public Eventos retirarmeDeLaMesa(Jugador j){
        if(!confirmados.get(j)){

            if(estado == EstadoDeLaMesa.ACEPTANDO_INSCRIPCIONES){
                lugaresDisponibles += j.getManos().size();
                dealer.eliminarJugador(j);
                inscriptos.remove(j);
                confirmados.remove(j);

                //remover jugador de observadores.
                actualizarEstadoDeLaMesa();
                return Eventos.ACCION_REALIZADA;
            }

            return Eventos.LA_MESA_YA_INICIO;
        }

        return Eventos.JUGADOR_CONFIRMADO;
    }

    public Eventos jugadorJuegaSuTurno(Accion a, Jugador j, ManoJugador mano){
        if(esTurnoDeEsteJugador(j)) {

            switch(a) {

                case PEDIR_CARTA -> {
                    mano.recibirCarta(dealer.repartirCarta());
                    //notificar vista del cambio en la mano del jugador.
                    return Eventos.ACCION_REALIZADA;
                }

                case QUEDARME -> {
                    mano.quedarme();
                    //notificar vista del cambio en la mano del jugador.
                    return Eventos.ACCION_REALIZADA;
                }

                case RENDIRME -> {
                    if(mano.turnoInicial()) {
                        mano.rendirme();
                        //notificar vista del cambio en la mano del jugador.
                        return Eventos.ACCION_REALIZADA;
                    }

                    return Eventos.NO_ES_TURNO_INICIAL;
                }

                case ASEGURARME -> {
                    if(mano.turnoInicial()){

                        if(dealer.condicionSeguro()){

                            double monto = mano.getMontoApostado() / 2.0;

                            if(j.transferenciaRealizable(monto)){
                                dealer.retirarDineroJugador(j, monto);
                                mano.asegurarme();
                                //notificar vista del cambio del saldo del jugador y la mano.
                                return Eventos.ACCION_REALIZADA;
                            }

                            return Eventos.SALDO_INSUFICIENTE;
                        }

                        return Eventos.DEALER_NO_CUMPLE_CONDICION;
                    }

                    return Eventos.NO_ES_TURNO_INICIAL;
                }

                case SEPARAR_MANO -> {
                    if(mano.turnoInicial()){

                        if(mano.condicionParaSepararMano()){

                            double monto = mano.getMontoApostado();

                            if(j.transferenciaRealizable(monto)){
                                dealer.retirarDineroJugador(j, monto);
                                ManoJugador nuevaMano = mano.separarMano();

                                mano.recibirCarta(dealer.repartirCarta());
                                nuevaMano.recibirCarta(dealer.repartirCarta());

                                j.agregarManoEnPosicion(j.getManos().indexOf(mano) + 1, nuevaMano);
                                //notificar vista del cambio en el saldo del jugador y la mano.
                                return Eventos.ACCION_REALIZADA;
                            }

                            return Eventos.SALDO_INSUFICIENTE;
                        }

                        return Eventos.MANO_NO_CUMPLE_CONDICION;
                    }

                    return Eventos.NO_ES_TURNO_INICIAL;
                }

                case DOBLAR_MANO -> {
                    if(mano.turnoInicial()){

                        double monto = mano.getMontoApostado();

                        if(j.transferenciaRealizable(monto)){
                            dealer.retirarDineroJugador(j, monto);
                            mano.doblarMano(dealer.repartirCarta());
                            //notificar vista del cambio en el saldo del jugador y la mano.
                            return Eventos.ACCION_REALIZADA;
                        }

                        return Eventos.SALDO_INSUFICIENTE;
                    }

                    return Eventos.NO_ES_TURNO_INICIAL;
                }

                case PASAR_TURNO -> {
                    pasarTurnoAlSiguienteJugador();
                    return Eventos.ACCION_REALIZADA;
                }
            }

            return Eventos.SITUACION_INNESPERADA;
        }

        return Eventos.NO_ES_SU_TURNO;
    }

    public Dealer getDealer(){
        return dealer;
    }

    public EstadoDeLaMesa getEstado(){
        return estado;
    }

    public String getJugadorTurnoActual(){
        if(turnoActual == null){
            return "";
        }

        return turnoActual.getNombre();
    }

    public List<Jugador> aquellosQueJuegan(){

        if(estado == EstadoDeLaMesa.ACEPTANDO_INSCRIPCIONES || estado == EstadoDeLaMesa.FINALIZANDO_RONDA){
            return new ArrayList<Jugador>();
        }

        return inscriptos;
    }
}
