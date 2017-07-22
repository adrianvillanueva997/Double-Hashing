package hash;

/**
 *
 * @author Adrián Villanueva Martínez
 * @param <Valor>
 */
public class Hash<Valor> {

    private Celda<Valor>[] contenedor;
    private int numElementos;
    private float alfaMaximo;
    private int capacidad;

    /**
     * Constructor por defecto
     */
    public Hash() {
        this.alfaMaximo = 0.8f;
        this.numElementos = 0;
        contenedor = new Celda[11];
    }

    /**
     * Constructor al que se le asigna la capacidad
     *
     * @param capacidad
     */
    public Hash(int capacidad) {
        if (esPrimo(capacidad) == true) {
            contenedor = new Celda[capacidad];
        } else {
            while (esPrimo(capacidad) == false) {
                capacidad = siguientePrimo(capacidad);
            }
            contenedor = new Celda[capacidad];
        }
        this.numElementos = 0;
        this.alfaMaximo = 0.8f;
    }

    /**
     *
     * @param capacidad
     * @param alfaMaximo
     */
    public Hash(int capacidad, float alfaMaximo) {
        if (esPrimo(capacidad) == true) {
            contenedor = new Celda[capacidad];
        } else {
            while (esPrimo(capacidad) == false) {
                capacidad = siguientePrimo(capacidad);
            }
            contenedor = new Celda[capacidad];
        }
        this.numElementos = 0;
        this.alfaMaximo = alfaMaximo;
    }

    /**
     *
     * @param clave
     * @param v
     */
    public void insertar(int clave, Valor v) {
        this.numElementos++;
        if (factorCarga() > alfaMaximo) {
            redimensionar();
        }
        int posicion;
        Celda celda = new Celda(clave); //estado 0 = ocupado
        posicion = funcionHash(clave, 0); // k mod n
        try {
            if (hayColision(posicion) == false) {   //No hay colision
                contenedor[posicion] = celda;
                contenedor[posicion].setClave(clave);
                contenedor[posicion].setValor(v);
                contenedor[posicion].setEstado(1);
            } else { // hay colision
                int contador = 1;
                while (hayColision(posicion) == true) {
                    posicion = funcionHash(clave, contador); //colision * (7 - clave mod 7)
                    hayColision(posicion);
                    contador++;
                }
                contenedor[posicion] = celda;
                contenedor[posicion].setClave(clave);
                contenedor[posicion].setValor(v);
                contenedor[posicion].setEstado(1);
            }
        }catch(Exception e){
            System.err.print(e);
        }

    }

    /**
     *
     * @param clave
     */
    public void borrar(int clave) {
        int colision = 0;
        int posicion = funcionHash(clave, colision);
        try {
            while (contenedor[posicion].getClave() != clave && colision != contenedor.length) {
                posicion = funcionHash(clave, colision);
                colision++;
            }
            contenedor[posicion].setEstado(-1);
            contenedor[posicion].setValor(null);
            this.numElementos = this.numElementos - 1;

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     *
     * @param clave
     * @return
     */
    public Valor get(int clave) {
        int colision = 0;
        int posicion = funcionHash(clave, colision);
        try {
            if (contenedor[posicion].getClave() == clave) {
                if (contenedor[posicion].getEstado() == 1) {
                    return contenedor[posicion].getValor();
                } else {
                    return null;
                }
            } else {
                while (contenedor[posicion].getClave() != clave) {
                    colision++;
                    posicion = funcionHash(clave, colision);
                }
                if (contenedor[posicion].getEstado() == 1) {
                    return contenedor[posicion].getValor();
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public boolean esVacia() {
        boolean comprobar = true;
        if (this.numElementos == 0) {
            return comprobar;
        } else {
            comprobar = false;
        }
        return comprobar;
    }

    /**
     *
     * @return
     */
    public float getAlfa() {
        return alfaMaximo;
    }

    /**
     *
     * @param alfa
     */
    public void setAlfa(float alfa) {
        this.alfaMaximo = alfa;
    }

    /**
     *
     * @return
     */
    public int getNumElementos() {
        return this.numElementos;
    }

    /**
     *
     * @return
     */
    public float factorCarga() {
        return (float) this.numElementos / contenedor.length;
    }

    private boolean hayColision(int posicion) {
        boolean colision = true;
        if (this.contenedor[posicion] == null) {
            colision = false;
        }
        return colision;
    }

    private int funcionHash(int clave, int colisiones) {
        int numero = hash1(clave) + hash2(clave, colisiones);
        return numero;
    }

    private int hash1(int clave) {
        int numero = clave % contenedor.length;
        return numero;
    }

    private int hash2(int clave, int colisiones) {
        int numero = colisiones * (7 - (clave % 7));
        return numero;
    }

    private void redimensionar() {
        Celda[] copia = this.contenedor;
        int tamano = contenedor.length * 2;
        int tamanoAumentado = 0;
        this.numElementos = 0;
        try {
            if (esPrimo(tamano) == false) {
                tamanoAumentado = siguientePrimo(tamano);
            }
            this.contenedor = new Celda[tamanoAumentado];
            int clave;
            Valor valor;
            for (Celda copia1 : copia) {
                if (copia1 != null) {
                    clave = copia1.getClave();
                    valor = (Valor) copia1.getValor();
                    insertar(clave, valor);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private boolean esPrimo(int numero) {
        boolean primo = true;
        int contador = 2;
        while (contador != numero && primo) {
            if (numero % contador == 0) {
                primo = false;
            }
            contador++;
        }
        return primo;
    }

    private int siguientePrimo(int numero) {
        if (esPrimo(numero) == true) {
            return numero;
        } else {
            while (esPrimo(numero) == false) {
                numero++;
            }
        }
        return numero;
    }
}
