import java.util.*;

/**
 * Clase genérica del Árbol Binario de Búsqueda con todas las operaciones
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> {
    private Nodo<T> raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    // 1. Verificar si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }

    // 2. Agregar dato al árbol
    public void agregar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser nulo");
        }
        raiz = agregarRecursivo(raiz, dato);
    }

    private Nodo<T> agregarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return new Nodo<>(dato);
        }

        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            nodo.izquierdo = agregarRecursivo(nodo.izquierdo, dato);
        } else if (comparacion > 0) {
            nodo.derecho = agregarRecursivo(nodo.derecho, dato);
        }
        // Si comparacion == 0, no agregamos duplicados

        return nodo;
    }

    // 3. Verificar si existe un dato
    public boolean existe(T dato) {
        return existeRecursivo(raiz, dato);
    }

    private boolean existeRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return false;
        }

        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            return existeRecursivo(nodo.izquierdo, dato);
        } else if (comparacion > 0) {
            return existeRecursivo(nodo.derecho, dato);
        }
        return true;
    }

    // 4. Obtener el peso (número de nodos)
    public int obtenerPeso() {
        return obtenerPesoRecursivo(raiz);
    }

    private int obtenerPesoRecursivo(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + obtenerPesoRecursivo(nodo.izquierdo) + obtenerPesoRecursivo(nodo.derecho);
    }

    // 5. Obtener la altura del árbol
    public int obtenerAltura() {
        return obtenerAlturaRecursivo(raiz);
    }

    private int obtenerAlturaRecursivo(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        int alturaIzq = obtenerAlturaRecursivo(nodo.izquierdo);
        int alturaDer = obtenerAlturaRecursivo(nodo.derecho);
        return 1 + Math.max(alturaIzq, alturaDer);
    }

    // 6. Obtener el nivel de un dato
    public int obtenerNivel(T dato) {
        return obtenerNivelRecursivo(raiz, dato, 0);
    }

    private int obtenerNivelRecursivo(Nodo<T> nodo, T dato, int nivel) {
        if (nodo == null) {
            return -1;
        }

        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            return obtenerNivelRecursivo(nodo.izquierdo, dato, nivel + 1);
        } else if (comparacion > 0) {
            return obtenerNivelRecursivo(nodo.derecho, dato, nivel + 1);
        }
        return nivel;
    }

    // 7. Contar el número de hojas
    public int contarHojas() {
        return contarHojasRecursivo(raiz);
    }

    private int contarHojasRecursivo(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return 1;
        }
        return contarHojasRecursivo(nodo.izquierdo) + contarHojasRecursivo(nodo.derecho);
    }

    // 8. Obtener el nodo menor (valor mínimo)
    public T obtenerMenor() {
        if (estaVacio()) {
            return null;
        }
        return obtenerNodoMenor(raiz).dato;
    }

    public Nodo<T> obtenerNodoMenor(Nodo<T> nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    // 9. Obtener el nodo mayor (valor máximo)
    public T obtenerMayor() {
        if (estaVacio()) {
            return null;
        }
        return obtenerNodoMayor(raiz).dato;
    }

    public Nodo<T> obtenerNodoMayor(Nodo<T> nodo) {
        while (nodo.derecho != null) {
            nodo = nodo.derecho;
        }
        return nodo;
    }

    // 10. Recorrido Inorden (izquierdo - raiz - derecho)
    public List<T> recorridoInorden() {
        List<T> resultado = new ArrayList<>();
        recorridoInordenRecursivo(raiz, resultado);
        return resultado;
    }

    private void recorridoInordenRecursivo(Nodo<T> nodo, List<T> resultado) {
        if (nodo != null) {
            recorridoInordenRecursivo(nodo.izquierdo, resultado);
            resultado.add(nodo.dato);
            recorridoInordenRecursivo(nodo.derecho, resultado);
        }
    }

    // 11. Recorrido Preorden (raiz - izquierdo - derecho)
    public List<T> recorridoPreorden() {
        List<T> resultado = new ArrayList<>();
        recorridoPreordenRecursivo(raiz, resultado);
        return resultado;
    }

    private void recorridoPreordenRecursivo(Nodo<T> nodo, List<T> resultado) {
        if (nodo != null) {
            resultado.add(nodo.dato);
            recorridoPreordenRecursivo(nodo.izquierdo, resultado);
            recorridoPreordenRecursivo(nodo.derecho, resultado);
        }
    }

    // 12. Recorrido Postorden (izquierdo - derecho - raiz)
    public List<T> recorridoPostorden() {
        List<T> resultado = new ArrayList<>();
        recorridoPostordenRecursivo(raiz, resultado);
        return resultado;
    }

    private void recorridoPostordenRecursivo(Nodo<T> nodo, List<T> resultado) {
        if (nodo != null) {
            recorridoPostordenRecursivo(nodo.izquierdo, resultado);
            recorridoPostordenRecursivo(nodo.derecho, resultado);
            resultado.add(nodo.dato);
        }
    }

    // 13. Imprimir por amplitud (recorrido por niveles)
    public List<List<T>> imprimirAmplitud() {
        List<List<T>> resultado = new ArrayList<>();
        if (estaVacio()) {
            return resultado;
        }

        Queue<Nodo<T>> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            int nivelSize = cola.size();
            List<T> nivel = new ArrayList<>();

            for (int i = 0; i < nivelSize; i++) {
                Nodo<T> nodo = cola.poll();
                nivel.add(nodo.dato);

                if (nodo.izquierdo != null) {
                    cola.add(nodo.izquierdo);
                }
                if (nodo.derecho != null) {
                    cola.add(nodo.derecho);
                }
            }
            resultado.add(nivel);
        }

        return resultado;
    }

    // 14. Eliminar un dato del árbol
    public void eliminar(T dato) {
        raiz = eliminarRecursivo(raiz, dato);
    }

    private Nodo<T> eliminarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, dato);
        } else if (comparacion > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, dato);
        } else {
            // Nodo a eliminar encontrado

            // Caso 1: Nodo sin hijos (hoja)
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }

            // Caso 2: Nodo con un hijo
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Caso 3: Nodo con dos hijos
            // Encontrar el sucesor (menor en el subárbol derecho)
            Nodo<T> sucesor = obtenerNodoMenor(nodo.derecho);
            nodo.dato = sucesor.dato;
            nodo.derecho = eliminarRecursivo(nodo.derecho, sucesor.dato);
        }

        return nodo;
    }

    // 15. Borrar el árbol completo
    public void borrarArbol() {
        raiz = null;
    }

    // Getter para la raíz (usado por la GUI)
    public Nodo<T> getRaiz() {
        return raiz;
    }

    // Método para obtener una representación del árbol
    @Override
    public String toString() {
        return "Peso: " + obtenerPeso() + ", Altura: " + obtenerAltura() + 
               ", Hojas: " + contarHojas() + ", Vacío: " + estaVacio();
    }
}
