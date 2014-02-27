/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Claudemir
 */
public class Lexer {

    private int tam;
    private ArrayList<String> arquivo;
    private Map<String, TabelaSimbolo> tabela;
    private int pos;
    private String token;
    private boolean erro;

    public Lexer(ArrayList<String> arquivo) {
        this.arquivo = arquivo;
        tam = arquivo.size();
        pos = 0;
    }

    public void parse() {
        int endereco = 0;
        String lexema = "";
        boolean fechaLexema = false;

        for (int i = 0; i < tam; i++) {
            int tamLinha = arquivo.get(i).length();
            while (pos < tamLinha) {
                lexema += arquivo.get(i).charAt(pos);
                erro = testa(lexema);
                if (!fechaLexema) {
                    pos = (pos == 0) ? 0 : pos - 1;

                    if (erro) {
                        erro = testa(lexema);
                    } else {
                        //coloca na tabela
                        if(lexema.charAt(-1) == ' ')
                            pos++;
                        lexema = lexema.substring(0, lexema.length() - 2);
                        if (!tabela.containsKey(lexema)) {
                            if (token != "erro") {
                                TabelaSimbolo t = new TabelaSimbolo();
                                t.setDefinicao(lexema);
                                t.setToken(token);
                                t.setEndereco(endereco);
                                
                                tabela.put(lexema, t);
                                System.out.println("<" + token + "," + endereco + ">");
                                endereco++;
                            } else {
                                System.out.println("INVALIDO (" + lexema + ") LINHA " + i);
                            }
                        } else {
                            TabelaSimbolo ts = tabela.get(lexema);
                            System.out.println("<" + token + "," + ts.getEndereco() + ">");
                        }

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
    
    public void dumpTabela(){
        Iterator<TabelaSimbolo> i = tabela.values().iterator();
        while(i.hasNext()){
            System.out.println(i.next().toString());
        }
    }
    
    //Insira as funções aqui.
}
