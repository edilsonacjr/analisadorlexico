package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Edilson Anselmo Corrêa Júnior
 */
public class Arquivo {

    FileReader reader;
    BufferedReader leitor;
    FileWriter writer;
    PrintWriter saida;

    public Arquivo(String nomeEntrada, String nomeSaida) throws IOException {
        writer = new FileWriter(new File(nomeSaida));
        saida = new PrintWriter(writer, true);
        reader = new FileReader(nomeEntrada);
        leitor = new BufferedReader(reader);
    }

    public void parseArquivo() throws IOException {
        int i = 1;
        String result = "";
        String linha;
        while ((linha = leitor.readLine()) != null) {
            String[] str = linha.split(" ");
            if(str.length < 3)
                return;
            
            saida.println("Case #" + i + ": " + result);
            i++;
        }
        reader.close();
        leitor.close();
        saida.close();
        writer.close();
    }
}
