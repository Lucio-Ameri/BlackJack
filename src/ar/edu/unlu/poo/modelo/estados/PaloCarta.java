package ar.edu.unlu.poo.modelo.estados;

public enum PaloCarta {
    DIAMANTES("♦"), PICAS("♠"), TREBOLES("♣"), CORAZONES("♥");

    private final String icono;

    PaloCarta(String icono){
        this.icono = icono;
    }

    //metodo: funcion que devuelve el icono del palo de la carta;
    public String getPaloIcono(){
        return icono;
    }
}
