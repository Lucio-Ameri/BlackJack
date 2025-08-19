package ar.edu.unlu.poo.interfaz;

import ar.edu.unlu.poo.modelo.Accion.Accion;
import ar.edu.unlu.poo.modelo.Jugador;
import ar.edu.unlu.poo.modelo.ManoJugador;
import ar.edu.unlu.poo.modelo.estado.EstadoDeLaMesa;
import ar.edu.unlu.poo.modelo.evento.Eventos;

import java.util.List;

public interface IMesa {
    void confirmarParticipacion(Jugador j);

    void confirmarNuevaParticipacion(Jugador j, double monto, boolean participacion);

    Eventos apostarOtraMano(Jugador j, double monto);

    Eventos retirarUnaMano(Jugador j, ManoJugador mano);

    Eventos retirarmeDeLaMesa(Jugador j);

    Eventos jugadorJuegaSuTurno(Accion a, Jugador j, ManoJugador m);

    IDealer getDealer();

    EstadoDeLaMesa getEstado();

    String getJugadorTurnoActual();

    List<IJugador> getInscriptos();
}
