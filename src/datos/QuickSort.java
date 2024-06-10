package datos;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSort {

    // Implementación secuencial de QuickSort
    public static void quickSortSequential(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSortSequential(arr, low, pi - 1);
            quickSortSequential(arr, pi + 1, high);
        }
    }

    // Partición del array para QuickSort secuencial
    private static int partition(int[] arr, int low, int high) {
    	// Posición que voy a comparar
        int pivot = arr[high];
        // Iterador que voy a utilizar para colocar al pivote en su posicion correcta
        int i = (low - 1);

        // En caso del elemento ser menor al pivote, lo muevo a su izquierda
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Una vez movido los elementos menores a la izquierda,
        // y mayores a la derecha, coloco el pivote en el centro y retorno su pos
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // Implementación concurrente de QuickSort
    public static void quickSortConcurrent(int[] arr, int low, int high) {
    	if (low < high) {
    		// Se crea un pool de hilos
    		ForkJoinPool pool = new ForkJoinPool(12);
    		// Se invoca la tarea raíz
    		pool.invoke(new QuickSortTask(arr, low, high));
    	}
    }
    
    // Clase interna para QuickSort concurrente
    static class QuickSortTask extends RecursiveAction {
        int[] arr;
        int low, high;

        public QuickSortTask(int[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
            	// Particiona el array y obtiene el índice del pivote
                int pi = partition(arr, low, high);

                // Crea y ejecuta tareas para los subarrays izquierdo y derecho
                QuickSortTask leftTask = new QuickSortTask(arr, low, pi - 1);
                QuickSortTask rightTask = new QuickSortTask(arr, pi + 1, high);

                // Se ejecutan las tareas del subarray izquierdo y derecho en paralelo
                leftTask.fork();
                rightTask.fork();
                // Espera a que las tareas terminen
                leftTask.join();
                rightTask.join();
            }
        }
    }

    // Método para imprimir un array
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Método principal para pruebas
    public static void main(String[] args) {
        int[] arr = Array.generarArrayAleatorio(10000000);
        
        int[] arrAux = arr.clone();
        int n = arrAux.length;

        //System.out.println("Array original:");
        //printArray(arr);

        long startTime = System.nanoTime();
        quickSortSequential(arrAux, 0, n - 1);
        long endTime = System.nanoTime();
        long durationSequential = (endTime - startTime);

        //System.out.println("\nArray ordenado secuencialmente:");
        //printArray(arrAux);

        System.out.println("\nTiempo de ejecución secuencial: " + durationSequential + " nanosegundos\n");

        // Reinicializamos el array
        arrAux = arr.clone();

        startTime = System.nanoTime();
        quickSortConcurrent(arrAux, 0, n - 1);
        endTime = System.nanoTime();
        long durationConcurrent = (endTime - startTime);

        //System.out.println("\nArray ordenado concurrentemente:");
        //printArray(arrAux);

        System.out.println("\nTiempo de ejecución concurrente: " + durationConcurrent + " nanosegundos");
    }
}
