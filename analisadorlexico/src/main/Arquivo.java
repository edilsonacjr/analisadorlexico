package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author edilson
 */
public class Arquivo {
    
    FileReader reader;
    BufferedReader leitor;
    
    /**
     * 
     * @param nomeEntrada
     * @throws IOException 
     */
    public Arquivo(String arqEntrada) throws IOException {
        reader = new FileReader(arqEntrada);
        leitor = new BufferedReader(reader);
    }
    /**
     * 
     * @return
     * @throws IOException 
     */
    public ArrayList<String> parseArquivo() throws IOException {
        String linha;
        ArrayList<String> matriz = new ArrayList<String>();
        while ((linha = leitor.readLine()) != null) {
            matriz.add(linha.trim());
        }
        reader.close();
        leitor.close();
        return matriz;
    }
}
