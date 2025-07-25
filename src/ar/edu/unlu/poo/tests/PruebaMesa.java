package ar.edu.unlu.poo.tests;

import ar.edu.unlu.poo.modelo.Jugador;
import ar.edu.unlu.poo.modelo.Mesa;
import ar.edu.unlu.poo.modelo.acciones.Accion;

import java.util.List;

public class PruebaMesa {
    public static void main(String[] args) {

        //creo los jugadores.
        Jugador j1 = new Jugador("Lucio", 1000.0);
        Jugador j2 = new Jugador("Franco", 1000.0);

        //creo la mesa e imprimo su estado actual.
        Mesa ronda = new Mesa();
        System.out.println("ESTADO DE LA MESA ANTES DE INSCRIBIR JUGADORES: " + ronda.getEstado());
        System.out.println();

        //inscribo a los jugadores, imprimiendo su contenido y verificando si el estado de la mesa cambio.
        System.out.println("VALOR OBTENIDO AL INSCRIBIR A LUCIO: " + ronda.inscribirJugadorNuevo(j1, 300.0));
        System.out.println("VALOR OBTENIDO AL INSCRIBIR A FRANCO: " + ronda.inscribirJugadorNuevo(j2, 200.0));
        System.out.println();

        System.out.println(j1.toString());
        System.out.println(j2.toString());

        System.out.println("ESTADO DE LA MESA LUEGO DE INSCRIBIR JUGADORES: " + ronda.getEstado());
        System.out.println();

        //apuesto y elimino otras manos de los jugadores, verificando que las funciones andan bien.
        ronda.apostarOtraMano(j1, 100.0);
        ronda.retirarUnaMano(j1, j1.getManos().get(1));
        ronda.confirmarParticipacion(j1);

        ronda.apostarOtraMano(j2, 100.0);
        ronda.confirmarParticipacion(j2);

        //verifico que luego de confirmar ambos jugadores, sus valores estan bien y el estado de la mesa cambia.
        System.out.println("ESTADO DE LA MESA LUEGO DE CONFIRMAR JUGADORES: " + ronda.getEstado());
        System.out.println();

        System.out.println(j1.toString());
        System.out.println(j2.toString());
        System.out.println();

        //reparto las cartas y verifico si estan fueron bien repartidas.
        ronda.confirmarParticipacion(j1);
        ronda.confirmarParticipacion(j2);

        System.out.println(j1.toString());
        System.out.println(j2.toString());
        System.out.println(ronda.getDealer().toString());

        System.out.println("ESTADO DE LA MESA LUEGO DE REPARTIR LAS CARTAS INICIALES: " + ronda.getEstado());
        System.out.println(ronda.getJugadorTurnoActual());
        System.out.println();

        //jugador 2 intenta jugar antes que jugador 1.
        System.out.println("RESULTADO DE QUERER JUGAR CUANDO NO ES SU TURNO: " + ronda.jugadorJuegaSuTurno(Accion.DOBLAR_MANO, j2, j2.getManos().get(0)));
        System.out.println();

        //jugador 1 juega su turno.
        System.out.println("RESULTADO CUANDO EL JUGADOR CORRECTO JUEGA SU TURNO: " + ronda.jugadorJuegaSuTurno(Accion.PEDIR_CARTA, j1, j1.getManos().get(0)));
        System.out.println(j1.toString());

        //jugador 1 pasa turno.
        ronda.jugadorJuegaSuTurno(Accion.QUEDARME, j1, j1.getManos().get(0));
        ronda.jugadorJuegaSuTurno(Accion.PASAR_TURNO, j1, j1.getManos().get(0));

        System.out.println(ronda.getJugadorTurnoActual());
        System.out.println();

        //jugador 2 juega su turno.
        System.out.println(ronda.jugadorJuegaSuTurno(Accion.DOBLAR_MANO, j2, j2.getManos().get(0)));
        System.out.println();

        System.out.println(ronda.jugadorJuegaSuTurno(Accion.DOBLAR_MANO, j2, j2.getManos().get(1)));
        ronda.jugadorJuegaSuTurno(Accion.PASAR_TURNO, j2, j2.getManos().get(1));

        System.out.println("ESTADO DE LA MESA LUEGO DE JUGAR TODOS LOS TURNOS: " + ronda.getEstado());
        System.out.println();

        System.out.println(j1.toString());
        System.out.println(j2.toString());

        //empieza el turno del dealer y reparte las ganancias.
        ronda.confirmarParticipacion(j1);
        ronda.confirmarParticipacion(j2);

        System.out.println(j1.toString());
        System.out.println(j2.toString());
        System.out.println(ronda.getDealer().toString());

        System.out.println("ESTADO DE LA MESA LUEGO DE JUGAR TURNO DEALER: " + ronda.getEstado());
        System.out.println();

        //jugadores confirman para poder eliminar las manos y poder reinscribirse.
        ronda.confirmarParticipacion(j1);
        ronda.confirmarParticipacion(j2);

        System.out.println("ESTADO DE LA MESA LUEGO DE CONFIRMAR PARA PODER REINSCRIBIRSE: " + ronda.getEstado());
        System.out.println();

        System.out.println(j1.toString());
        System.out.println(j2.toString());

        //jugadores se reinscriben o se dan de baja.
        ronda.confirmarNuevaParticipacion(j1, 200.0, true);
        ronda.confirmarNuevaParticipacion(j2, 0.0, false);

        System.out.println("ESTADO DE LA MESA LUEGO DE CONFIRMAR SI SIGUEN O NO: " + ronda.getEstado());
        System.out.println();

        //nuevo jugador se inscribe.
        Jugador j3 = new Jugador("Melina", 1000.0);
        ronda.inscribirJugadorNuevo(j3, 500);

         /*
        List<Jugador> jugadores = ronda.aquellosQueJuegan();                      // Esta parte sirve para ver si efectivamente se dio de baja al j2 y j3 fue ingresado.
        for(Jugador j: jugadores){                                                // Hay que comentar una parte de aquellosQueJuegan para poder verlo.
            System.out.println(j.toString());
        }
         */

        //ver si retirarme de la ronda funciona.
        System.out.println(ronda.retirarmeDeLaMesa(j1));

    }
}
