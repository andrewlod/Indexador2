package me.andrewlod.indexador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Separador {
	
	public static String[] separar(String texto){
		return texto.replaceAll(";", " ").replaceAll(",", " ").replaceAll("\"", " ").replaceAll("  ", " ").split(" ");
	}
	public static String[] separar(String texto, String[] stopwords){
		ArrayList<String> separado = new ArrayList<>(Arrays.asList(texto.replaceAll(";", " ").replaceAll(",", " ").replaceAll("\"", " ").replaceAll("  ", " ").split(" ")));
		separado.removeAll(Arrays.asList(stopwords));
		return separado.toArray(new String[separado.size()]);
	}
	public static HashMap<String, Integer> fazerDicionario(String[] palavras){
		HashMap<String, Integer> dic = new HashMap<String, Integer>();
		
		for(int i = 0; i < palavras.length; i++) {
			if(dic.containsKey(palavras[i])) {
				dic.replace(palavras[i], dic.get(palavras[i])+1);
			}else {
				dic.put(palavras[i], 1);
			}
		}
		return dic;
	}
	public static ArrayList<String> fazerDicionarioString(String[] palavras){
		ArrayList<String> dic = new ArrayList<>();
		
		for(int i = 0; i < palavras.length; i++) {
			if(!dic.contains(palavras[i]))
				dic.add(palavras[i]);
			
		}
		return dic;
	}
}
