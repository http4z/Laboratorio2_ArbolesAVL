package com.mycompany.arbolavl;

import java.util.Scanner;

class Nodo {
    int valor;
    Nodo izquierda, derecha;
    int altura;

    Nodo(int valor) {
        this.valor = valor;
        izquierda = null;
        derecha = null;
    }
}

class ArbolAVL {
    Nodo raiz;

    int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    int maximo(int a, int b) {
        return Math.max(a, b);
    }

    Nodo rotacionDerecha(Nodo nodo) {
        Nodo nodoIzquierda = nodo.izquierda;
        Nodo nodoDerecha = nodoIzquierda.derecha;

        nodoIzquierda.derecha = nodo;
        nodo.izquierda = nodoDerecha;

        nodo.altura = maximo(altura(nodo.izquierda), altura(nodo.derecha)) + 1;
        nodoIzquierda.altura = maximo(altura(nodoIzquierda.izquierda), altura(nodoIzquierda.derecha)) + 1;

        return nodoIzquierda;
    }

    Nodo rotacionIzquierda(Nodo nodo) {
        Nodo nodoDerecha = nodo.derecha;
        Nodo nodoIzquierda = nodoDerecha.izquierda;

        nodoDerecha.izquierda = nodo;
        nodo.derecha = nodoIzquierda;

        nodo.altura = maximo(altura(nodo.izquierda), altura(nodo.derecha)) + 1;
        nodoDerecha.altura = maximo(altura(nodoDerecha.izquierda), altura(nodoDerecha.derecha)) + 1;

        return nodoDerecha;
    }

    int factorEquilibrio(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierda) - altura(nodo.derecha);
    }

    Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierda = insertar(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertar(nodo.derecha, valor);
        } else {
            return nodo;
        }

        nodo.altura = maximo(altura(nodo.izquierda), altura(nodo.derecha)) + 1;

        int factorEquilibrio = factorEquilibrio(nodo);

        if (factorEquilibrio > 1 && valor < nodo.izquierda.valor) {
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio < -1 && valor > nodo.derecha.valor) {
            return rotacionIzquierda(nodo);
        }

        if (factorEquilibrio > 1 && valor > nodo.izquierda.valor) {
            nodo.izquierda = rotacionIzquierda(nodo.izquierda);
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio < -1 && valor < nodo.derecha.valor) {
            nodo.derecha = rotacionDerecha(nodo.derecha);
            return rotacionDerecha(nodo);
    }

    return nodo;
}

void recorridoPreorden(Nodo nodo) {
    if (nodo != null) {
        System.out.print(nodo.valor + " ");
        recorridoPreorden(nodo.izquierda);
        recorridoPreorden(nodo.derecha);
    }
}

void recorridoInorden(Nodo nodo) {
    if (nodo != null) {
        recorridoInorden(nodo.izquierda);
        System.out.print(nodo.valor + " ");
        recorridoInorden(nodo.derecha);
    }
}

void recorridoPostorden(Nodo nodo) {
    if (nodo != null) {
        recorridoPostorden(nodo.izquierda);
        recorridoPostorden(nodo.derecha);
        System.out.print(nodo.valor + " ");
    }
}

void recorridoArbol(int opcion) {
    switch (opcion) {
        case 1 -> {
            System.out.print("Recorrido Preorden: ");
            recorridoPreorden(raiz);
            System.out.println();
            }
        case 2 -> {
            System.out.print("Recorrido Inorden: ");
            recorridoInorden(raiz);
            System.out.println();
            }
        case 3 -> {
            System.out.print("Recorrido Postorden: ");
            recorridoPostorden(raiz);
            System.out.println();
            }
        default -> System.out.println("Opción inválida");
    }
}

int obtenerAltura() {
    return altura(raiz);
}

void imprimirNodosHoja(Nodo nodo) {
    if (nodo == null) {
        return;
    }

    if (nodo.izquierda == null && nodo.derecha == null) {
        System.out.print(nodo.valor + " ");
    }

    if (nodo.izquierda != null) {
        imprimirNodosHoja(nodo.izquierda);
    }

    if (nodo.derecha != null) {
        imprimirNodosHoja(nodo.derecha);
    }
}

void menu() {
    Scanner scanner = new Scanner(System.in);
    int opcion;
    int valor;

    do {
        System.out.println("1. Agregar número al árbol");
        System.out.println("2. Verificar si el árbol está balanceado");
        System.out.println("3. Recorrido del árbol");
        System.out.println("4. Altura del árbol");
        System.out.println("5. Nodos hoja del árbol");
        System.out.println("6. Salir");
        System.out.print("Ingrese una opción: ");
        opcion = scanner.nextInt();

        switch (opcion) {
            case 1 -> {
                System.out.print("Ingrese un valor: ");
                valor = scanner.nextInt();
                raiz = insertar(raiz, valor);
            }
            case 2 -> {
                if (factorEquilibrio(raiz) == 0) {
                    System.out.println("El árbol está balanceado");
                } else {
                    System.out.println("El árbol no está balanceado");
                }
            }
            case 3 -> {
                System.out.println("1. Recorrido Preorden");
                System.out.println("2. Recorrido Inorden");
                System.out.println("3. Recorrido Postorden");
                System.out.print("Ingrese una opción: ");
                int recorrido = scanner.nextInt();
                recorridoArbol(recorrido);
            }
            case 4 -> {
                System.out.println("La altura del árbol es");
                        System.out.println(obtenerAltura());
            }
            case 5 -> {
                System.out.print("Nodos hoja: ");
                imprimirNodosHoja(raiz);
                System.out.println();
            }
            case 6 -> System.out.println("Saliendo del programa...");
            default -> System.out.println("Opción inválida");
        }

        System.out.println();
    } while (opcion != 6);
}

public static void main(String[] args) {
    ArbolAVL arbol = new ArbolAVL();
    arbol.menu();
}

}
