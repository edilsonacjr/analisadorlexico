package main;

import java.io.IOException;

public class Compilador {
    
    
    public static void main(String[] args) throws IOException{
        System.out.println("Executando Analisador Léxico");
        
        Arquivo arq = new Arquivo("teste2.txt");
        Lexer lex = new Lexer(arq.parseArquivo());
        lex.parse();
    }   
}
