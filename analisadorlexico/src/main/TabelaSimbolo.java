/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author Claudemir
 */
public class TabelaSimbolo {
    
    private String definicao;
    private String token;
    private int endereco;

    public String getDefinicao() {
        return definicao;
    }

    public void setDefinicao(String definicao) {
        this.definicao = definicao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getEndereco() {
        return endereco;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return endereco + " " + token + " " + definicao;
    }
    
}
