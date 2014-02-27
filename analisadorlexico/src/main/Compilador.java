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
        Arquivo arq = new Arquivo("teste.txt");
        arq.parseArquivo();
        //boolean i = "\n".equals("\n");
        //System.out.println(i);
    }
    
    public static Boolean operador(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);
            if (caracter == ' ' || Character.isDigit(caracter)) {
                estado = "qerror";
            }

            if (!Character.isDigit(caracter)) {
                
                if (caracter != '+' && caracter != '/' && caracter != '*' ){
                    estado = "qerror";
                }
                
            }

            if (caracter == '+') {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "q2";
                        break;
                    case "q2":
                        estado = "qerror";
                        break;
                }
            }

            if (caracter == '/') {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "qerror";
                        break;
                }
            }

            if (caracter == '*') {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "q2";
                        break;
                    case "q2":
                        estado = "qerror";
                        break;
                }
            }
            p++;
        }

        if (estado.equals("q1") || estado.equals("q2")) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean comentarioLinha(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);

            if (caracter == '/') {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "q2";
                        break;
                }
            }

            if (caracter != '\n') {
                switch (estado) {
                    case "q0":
                        estado = "q0";
                        break;
                    case "q1":
                        estado = "q1";
                        break;
                    case "q2":
                        estado = "q2";
                        break;
                }
            }

            if (caracter == '\n') {
                switch (estado) {
                    case "q2":
                        estado = "q3";
                        break;
                }
            }
            p++;
        }
        if (estado == "q3") {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean comentarioMultiplo(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);

            if (caracter == '/') {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q3":
                        estado = "q4";
                        break;
                }
            }

            if (caracter == '*') {

                switch (estado) {
                    case "q1":
                        estado = "q2";
                        break;
                    case "q2":
                        estado = "q3";
                        break;
                }
            }

            if (caracter != '/' && caracter != '*') {
                switch (estado) {
                    case "q2":
                        estado = "q2";
                        break;
                }
            }

            p++;
        }
        if (estado.equals("q4")) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean ehDigit(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);

            if (Character.isDigit(caracter)) {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "q1";
                        break;
                }
            }

            if (!Character.isDigit(caracter)) {
                estado = "qerror";
            }

            if (caracter == ' ') {
                estado = "qerror";
            }

            p++;
        }
        if (estado.equals("q1")) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean ehFloat(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);

            if (Character.isDigit(caracter)) {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "q1";
                        break;
                    case "q2":
                        estado = "q3";
                        break;
                    case "q3":
                        estado = "q3";
                        break;
                }
            }

            if (caracter == '.') {
                switch (estado) {
                    case "q1":
                        estado = "q2";
                        break;
                    case "q2":
                        estado = "qerror";
                        break;
                }
            }

            if (!Character.isDigit(caracter) && caracter != '.') {
                estado = "qerror";
            }

            if (caracter == ' ') {
                estado = "qerror";
            }
            p++;
        }
        if (estado.equals("q3")) {
            return true;
        } else {
            return false;
        }
    }
    
}
