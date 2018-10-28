package me.andrewlod.indexador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class SIRD {
	
	private static final String FILES_PATH = "src\\me\\andrewlod\\indexador\\files\\";
	private static final String STOPWORDS_FILE = "src\\me\\andrewlod\\indexador\\stopwords\\pt_br.txt";
	private static Scanner in;
	
	public static void main(String[] args) {
		Diretorio d = new Diretorio(FILES_PATH);
		Documento doc = new Documento(STOPWORDS_FILE);
		String[] stopwords = Separador.separar(doc.read());
		
		ArrayList<HashMap<String, Integer>> listWordsFile = new ArrayList<>();
		
		//System.out.println("Entre com a sua pesquisa: ");
		//in = new Scanner(System.in);
		//String phrase = in.nextLine();
		
		//ArrayList<String> words = Separador.fazerDicionarioString(Separador.separar(phrase));
		
		for(int i=0;i<d.getSizeFiles();i++)
			listWordsFile.add(d.getDictAtIndex(i));
		
		Dicionario dict = new Dicionario(listWordsFile);
		double[] similaridades = dict.getSimilaridades(new String[] {"aa", "cc"});
		System.out.println(Arrays.toString(similaridades));
		
		//for(int i=0;i<d.getSizeFiles();i++)
		//	similaridades.add(similaridade(cauculaV(weightSearch(phrase,words),cauculaIdfSearch(words,listWordsFile)),cauculaU(words,listWordsFile,cauculaIdfSearch(words,listWordsFile)),i));
		
		//System.out.println(similaridades);
	}
	public static double log(double x, int base)
	{
	    return  (Math.log(x) / Math.log(base));
	}
	public static ArrayList<Double> weightSearch(String phrase,ArrayList<String> words) {
		ArrayList<Double> w = new ArrayList<>();
		double soma = 0;
		HashMap<String, Integer> value = Separador.fazerDicionario(Separador.separar(phrase));
		for(String i : words) 
			if(value.get(i) > 1) 
				soma+=1;
		for(String i : words)
			w.add(value.get(i)/(value.size()+soma));
		return w;
	}
	public static ArrayList<Double> cauculaIdfSearch(ArrayList<String> words,ArrayList<HashMap<String, Integer>> listWordsFile) {
		ArrayList<Double> idf = new ArrayList<>();
		ArrayList<Double> dF = new ArrayList<>();
		double value = 0.0;
		for(String i : words) {
			value = 0;
			for(int a=0;a<listWordsFile.size();a++)
				if(listWordsFile.get(a).containsKey(i)) 
					value++;
			dF.add(value);
		}
		for(double i: dF)
			idf.add(log(listWordsFile.size()/i,2));	
		return idf;
	}
	public static double [][] cauculaU(ArrayList<String> words,ArrayList<HashMap<String, Integer>> listWordsFile,ArrayList<Double> idf){
		ArrayList<Double> u = new ArrayList<>();
		double [][] U = new double[words.size()][listWordsFile.size()];
		int j = 0;
		for(String i : words) { 
			u.clear();
			for(int a=0;a<listWordsFile.size();a++) 
				if(listWordsFile.get(a).containsKey(i))
					U[j][a] = listWordsFile.get(a).get(i)*idf.get(j);
			j++;
		}
		return U;
	}
	public static ArrayList<Double> cauculaV(ArrayList<Double> weightSearch,ArrayList<Double> cauculaIdf){
		 ArrayList<Double> v = new  ArrayList<>();
		 for(int i =0;i<weightSearch.size();i++)
			 v.add(weightSearch.get(i)*cauculaIdf.get(i));
		return v;
	}
	public static double  similaridade(ArrayList<Double> v,double [][] u,int indexDocument) {
		double  valueVU = 0, value = 0;
		int i = 0;
		for(int j =0;j<u.length;j++)
		{
					valueVU += v.get(i)*u[j][indexDocument];
					i++;
		}			
		for(Double in: v)
			value += Math.pow(in,2);
		value = Math.sqrt(value);
		return valueVU/value;
	}
	public boolean insereArqTexto(String path, String nome) throws IOException {
		String fullPath = path + "\\" + nome;
		try {
			FileOutputStream fos = new FileOutputStream(fullPath);
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean removeArqTexto(String path, String nome) {
		String fullPath = path + "\\" + nome;
		File file = new File(fullPath);
		return file.delete();
	}
}
