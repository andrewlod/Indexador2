package me.andrewlod.indexador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dicionario {

	private ArrayList<HashMap<String, Integer>> dicts;
	
	public Dicionario(ArrayList<HashMap<String, Integer>> dcts) {
		dicts = dcts;
	}
	
	public double[] getSimilaridades(String[] _consulta){
		ArrayList<String> chaves = new ArrayList<String>();
		
		for(int i = 0; i < dicts.size(); i++) {
			String[] l = new String[dicts.get(i).size()]; 
			l = dicts.get(i).keySet().toArray(l);
			for(String s : l) {
				if(!chaves.contains(s)) {
					chaves.add(s);
				}
			}
		}
		
		double[][] matriz = new double[dicts.size()][chaves.size()];
		int[] df = new int[chaves.size()];
		
		for(int i = 0; i < dicts.size(); i++) {
			for(int j = 0; j < chaves.size(); j++) {
				if(dicts.get(i).containsKey(chaves.get(j))) {
					matriz[i][j] = dicts.get(i).get(chaves.get(j));
					df[j]++;
				}else {
					matriz[i][j] = 0;
				}
			}
		}
		
		double[] idf = new double[chaves.size()];
		for(int i = 0; i < idf.length; i++) {
			idf[i] = Util.log((double)dicts.size() / (double)df[i], 2);
		}
		
		double[][] u = new double[dicts.size()][chaves.size()];
		System.out.println(Arrays.toString(chaves.toArray()));
		for(int i = 0; i < dicts.size(); i++) {
			for(int j = 0; j < chaves.size(); j++) {
				u[i][j] = matriz[i][j] * idf[j];
			}
		}
		
		double[] consulta = new double[chaves.size()];
		double[] v = new double[chaves.size()];
		
		for(int i = 0; i < chaves.size(); i++) {
			for(int j = 0; j < _consulta.length; j++) {
				if(chaves.get(i).equals(_consulta[j])) {
					consulta[i] = 1;
				}
			}
			v[i] = consulta[i] * idf[i];
		}
		
		double[] similaridades = new double[dicts.size()];
		
		for(int i = 0; i < dicts.size(); i++) {
			double uv = Util.sum(Util.multiEsc(u[i], v));
			double ruv = Math.sqrt(Util.sum(Util.multiEsc(v, v))) * Math.sqrt(Util.sum(Util.multiEsc(u[i],u[i])));
			similaridades[i] = uv / ruv;
		}
		
		
		return similaridades;
		
	}
	
}
