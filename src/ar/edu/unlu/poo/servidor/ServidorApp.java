/*
package ar.edu.unlu.poo.servidor;

import ar.edu.unlu.poo.rmimvc.RMIMVCException;
import ar.edu.unlu.poo.rmimvc.Util;
import ar.edu.unlu.poo.rmimvc.servidor.Servidor;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServidorApp {

    public static void main(String[] args) {
        ArrayList<String> ips = Util.getIpDisponibles();

        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchar� peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );

        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchar� peticiones el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );

        Chat modelo = new Chat();
        Servidor servidor = new Servidor(ip, Integer.parseInt(port));

        try {
            servidor.iniciar(modelo);
        }

        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}


 */