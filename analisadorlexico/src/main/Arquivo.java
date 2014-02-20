package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    public String parseArquivo() throws IOException {
        String linha;
        String arquivo = "";
        while ((linha = leitor.readLine()) != null) {
            arquivo += (" "+linha);
        }
        reader.close();
        leitor.close();
        return arquivo;
    }
}
