package hash;

/**
 *
 * @author Adrián Villanueva Martínez
 * @param <Valor>
 */
public class Celda<Valor> {

    private int clave;
    private Valor valor;
    private int estado;

    /**
     * Constructor por defecto
     */
    public Celda() {

    }

    /**
     * Constructor al que se le pasa un entero clave
     *
     * @param clave
     */
    public Celda(int clave) {
        this.clave = clave;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public int getEstado() {
        return this.estado;
    }

    /**
     *
     * @param valor
     */
    public void setValor(Valor valor) {
        this.valor = valor;
    }

    /**
     *
     * @return
     */
    public Valor getValor() {
        return valor;
    }

    /**
     *
     * @param celda
     * @return
     */
    /*
    public boolean equals(Celda celda) {
        return true;
    }
     */
}
