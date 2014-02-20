/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Claudemir
 */
public class Lexer {
    private int tam;
    private String arquivo;
    private int pos;
    private String token;
    
    public Lexer(String arquivo){
        this.arquivo = arquivo;
        tam = arquivo.length();
        pos = 0;
    }
    
    public void parse(){
        String lexema = "";
        boolean fechaLexema = false;
        
        
        while(pos < tam){
            lexema += arquivo.charAt(pos);
            fechaLexema = testa(lexema);
            if(!fechaLexema){
                pos--;
                //coloca na tabela
                System.out.println("<"+token+","+12+">");
                lexema = "";
            }
            pos++;            
        }
    }
    
    public boolean testa(String lexema){
     
        return true;
    }
    
}
