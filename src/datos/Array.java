package datos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Array {

	 public static int[] generarArrayOrdenado(int tam) {
		 	int[] array = new int[tam];

	        for (int i = 0; i < tam; i++) {
	            array[i] = i+1; 
	            }
	        
	        return array;
	    }
	 
	 public static int[] generarArrayOrdenadoAleatorio(int tam) {
		 Random rand = new Random();
		 
		 // Genero numeros aleatorios sin repetir en un rango
		 Set<Integer> set = new HashSet<>();
		 while(set.size() < tam) {
			 int n = rand.nextInt(tam * 10);
			 set.add(n);
		 }
		 
		 // Convierto el set en lista para ordenarlo
		 List<Integer> list = new ArrayList<>(set);
		 Collections.sort(list);
		 
		 // Convierto la lista en array
		 int[] array = new int[tam];
		 for(int i = 0; i < tam; i++) {
			 array[i] = list.get(i);
		 }
		 
		 return array;
	 }
	 
	 public static int[] generarArrayAleatorio(int tam) {
		 Random rand = new Random();
		 int[] array = new int[tam];
		 
		 for(int i=0; i<tam; i++) {
			 array[i] = rand.nextInt(tam*10);
		 }
		 return array;
	 }
	
	 public static void main(String[] args) {
	        int tam = 100; // Tamaño del array a generar
	        int[] arrayOrdenado = generarArrayOrdenadoAleatorio(tam);

	        // Imprimir el array ordenado
	        for (int numero : arrayOrdenado) {
	            System.out.print(" " + numero + ",");
	        }
	    }
	
}
