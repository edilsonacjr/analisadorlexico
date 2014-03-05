/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.IOException;

/**
 *
 * @author Claudemir
 */
public class Compilador {
    
    
    public static void main(String[] args) throws IOException{
        System.out.println("Executando Analisador Léxico");
        
        Arquivo arq = new Arquivo("teste2.txt");
        Lexer lex = new Lexer(arq.parseArquivo());
        lex.parse();
    }   
}
