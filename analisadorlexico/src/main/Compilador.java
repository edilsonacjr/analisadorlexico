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
    public static boolean comparacao(String lexema) {
        String estado = "q0";
        char caracter = ' ';
        int tam = lexema.length();
        int i = 0;
        while (i < tam) {
            caracter = lexema.charAt(i);
            if (caracter == ' ') {
                estado = "qErr";
            } else if (caracter == '>') {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    default:
                        estado = "qErr";
                        break;
                }
            } else if (caracter == '=') {
                switch (estado) {
                    case "q1":
                        estado = "q2";
                        break;
                    case "q3":
                        estado = "q4";
                        break;
                    default:
                        estado = "qErr";
                        break;
                }
            } else if (caracter == '<') {
                switch (estado) {
                    case "q0":
                        estado = "q3";
                        break;
                    default:
                        estado = "qErr";
                        break;
                }
            } else {
                estado = "qErr";
            }
            i++;
        }
        if ((estado.equals("q0")) || (estado.equals("qErr"))) {
            return false;
        } else {
            switch(estado){
                case "q1":
                    //token = GT;
                    break;
                case "q2":
                    //token = GE;
                    break;
                case "q3":
                    //token = LT;
                    break;
                case "q4":
                    //token = LE;
                    break;
            }
            return true;
        }

    }

    public static boolean operadorIgual(String lexema) {
        String estado = "q0";
        char caracter = ' ';
        int tam = lexema.length();
        int i = 0;
        while (i < tam) {
            caracter = lexema.charAt(i);
            if (caracter == ' ') {
                estado = "qErr";
            } else if (caracter == '=') {
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "q2";
                        break;
                    default:
                        estado = "qErr";
                        break;
                }
            } else {
                estado = "qErr";
            }
            i++;
        }
        if ((estado.equals("q0")) || (estado.equals("qErr"))) {
            return false;
        } else {
            switch(estado){
                case "q1":
                    //token = ATR;
                    break;
                case "q2":
                    //token = EQ;
                    break;
            }
            return true;
        }

    }
    
    public static Boolean operador(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);

            if (caracter != '+' && caracter != '/' && caracter != '*') {
                estado = "qerror";
            } else {

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

            if (caracter != '\n') {

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
            } else {
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
            
            if (caracter == '/' || caracter == '*') {

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

            } else {
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

            if (Character.isDigit(caracter) || caracter == '.') {

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
                } else {
                    switch (estado) {
                        case "q1":
                            estado = "q2";
                            break;
                        case "q2":
                            estado = "qerror";
                            break;

                    }
                }
            } else {
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
