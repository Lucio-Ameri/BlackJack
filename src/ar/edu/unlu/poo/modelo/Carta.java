package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.modelo.estados.PaloCarta;
import ar.edu.unlu.poo.modelo.estados.ValorCarta;
import ar.edu.unlu.poo.modelo.estados.VisibilidadCarta;

public class Carta {
    private final PaloCarta palo;
    private final ValorCarta valor;
    private VisibilidadCarta estado;

    public Carta(PaloCarta p, ValorCarta v){
        this.palo = p;
        this.valor = v;
        this.estado = VisibilidadCarta.OCULTA;
    }


    //metodo: devuelve el tipo de palo que posee la carta.
    public PaloCarta getTipoPalo(){
        return palo;
    }


    //metodo: devuelve el tipo de valor que posee la carta.
    public ValorCarta getTipoValor(){
        return valor;
    }


    //metodo: devuelve el valor numerico que posee la carta.
    public int getValorCarta(){
        return valor.getValorNumerico();
    }


    //metodo: informa si la carta se encuentra oculta o visible.
    public boolean cartaOculta(){
        return estado == VisibilidadCarta.OCULTA;
    }


    //metodo: cambia el estado de la carta, de oculta -> visible.
    public void revelarCarta(){
        estado = VisibilidadCarta.VISIBLE;
    }


    //metodo: informa si la carta tiene el tipo de valor AS.
    public boolean esAs(){
        return valor.tipoAS();
    }


    //metodo: informa si la carta actual es similar a la carta pasada por parametro.
    public boolean cartasSimilares(Carta c){
        return this.valor == c.getTipoValor();
    }


    //metodo: devuelve un string personalizado que representa la información de la carta.
    @Override
    public String toString(){
        String s;

        if(cartaOculta()){
            s = " [ ?? ] ";
        }

        else{

            s = " [ " + valor.getSimboloValor() + palo.getPaloIcono() + " ] ";
        }

        return s;
    }
}
