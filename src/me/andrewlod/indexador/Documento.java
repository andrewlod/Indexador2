package me.andrewlod.indexador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Documento extends File{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Documento(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public String read() {
		File f = new File(this.getPath());
		String s = "";
		FileReader reader = null;
		BufferedReader bfr = null;
		
		try {
			reader = new FileReader(f);
			bfr = new BufferedReader(reader);
			String line;
			
			while((line=bfr.readLine())!= null) {
				s += line + " ";
			}
			bfr.close();
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public HashMap<String, Integer> getDicionario(){
		return Separador.fazerDicionario(Separador.separar(this.read()));
	}
	public HashMap<String, Integer> getDicionario(String[] stopwords){
		return Separador.fazerDicionario(Separador.separar(this.read(), stopwords));
	}
}
