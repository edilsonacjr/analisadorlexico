/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author Claudemir
 */
public class Lexer {

    private int tam;
    private ArrayList<String> arquivo;
    private int pos;
    private String token;
    private boolean erro;

    public Lexer(ArrayList<String> arquivo) {
        this.arquivo = arquivo;
        tam = arquivo.size();
        pos = 0;
    }

    public void parse() {
        String lexema = "";
        boolean fechaLexema = false;

        for (int i = 0; i < tam; i++) {
            int tamLinha = arquivo.get(i).length();
            while (pos < tamLinha) {
                lexema += arquivo.get(i).charAt(pos);
                fechaLexema = testa(lexema);
                if (!fechaLexema) {
                    pos = (pos == 0) ? 0 : pos - 1;
                    
                    if(erro){
                        
                    }else{
                        //coloca na tabela
                        System.out.println("<" + token + "," + 12 + ">");
                    }
                    
                    lexema = "";
                }
                pos++;
            }
            pos = 0;
        }


    }

    public boolean testa(String lexema) {

        return true;
    }
}
