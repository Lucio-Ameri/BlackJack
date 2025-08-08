package ar.edu.unlu.poo.modelo;

import ar.edu.unlu.poo.interfaz.ICarta;
import ar.edu.unlu.poo.modelo.estado.PaloCarta;
import ar.edu.unlu.poo.modelo.estado.ValorCarta;
import ar.edu.unlu.poo.modelo.estado.VisibilidadCarta;

public class Carta implements ICarta {
    private final PaloCarta palo;
    private final ValorCarta valor;
    private VisibilidadCarta estado;

    public Carta(PaloCarta p, ValorCarta v){
        this.palo = p;
        this.valor = v;
        this.estado = VisibilidadCarta.OCULTA;
    }


    //metodo: informa si la carta esta oculta o visible.
    public boolean cartaOculta(){
        return estado == VisibilidadCarta.OCULTA;
    }


    //metodo: devuelve el enumerado PaloCarta dependiendo de la visibilidad de la carta.
    @Override
    public PaloCarta getTipoDePalo(){
        return cartaOculta() ? PaloCarta.OCULTO : palo;
    }


    //metodo: devuelve el enumerado ValorCarta dependiendo de la visibilidad de la carta.
    @Override
    public ValorCarta getTipoDeValor(){
        return cartaOculta() ? ValorCarta.OCULTO : valor;
    }


    //metodo: devuelve el valor de la carta dependiendo de la visibilidad de la carta.
    public int getValorNumericoCarta(){
        return cartaOculta() ? 0 : valor.getValorNumerico();
    }


    //metodo: cambia el estado de la carta de Oculta -> Visible.
    public void revelarCarta(){
        estado = VisibilidadCarta.VISIBLE;
    }


    //metodo: informa si la carta es un AS dependiendo de la visibilidad de la carta.
    public boolean esAs(){
        return cartaOculta() ? false : valor.tipoAS();
    }


    //metodo: informa si las cartas son similares en caso de que ambas esten visibles.
    public boolean cartasSimilares(Carta c){
        if(cartaOculta() || c.cartaOculta()){
            return false;
        }

        return this.valor == c.getTipoDeValor();
    }


    //metodo: devuelve un String personalizado que posee la información de la carta actual.
    @Override
    public String toString(){
        return cartaOculta() ? "[ ?? ] " : "[ " + valor.getSimboloValor() + palo.getPaloIcono() + " ] ";
    }
}
