package main;

import java.util.ArrayList;
import java.util.HashMap;
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
        tabela = new HashMap<String, TabelaSimbolo>();
        token = "";
    }
    /**
     * Função responsável por fazer a análise léxica do arquivo
     */
    public void parse() {
        int endereco = 0;
        String lexema = "";
        //boolean fechaLexema = false;

        //Percorre o ArrayList onde estão as linhas do arquivo.
        for (int i = 0; i < tam; i++) {
            //Define o número de caracteres na linha
            int tamLinha = arquivo.get(i).length();
            //Testa se a linha está fazia
            if (tamLinha == 0) {
                continue;
            }
            //Percorre cada caracter de uma determinada linha
            while (pos <= tamLinha) {
                if (pos == tamLinha) {
                    lexema += " ";
                } else {
                    //Adiciona mais um caracter ao lexema
                    lexema += arquivo.get(i).charAt(pos);
                }
                if(lexema.equals(" ")||lexema.equals("\b")||lexema.equals("\t")){
                    pos++;
                    lexema="";
                    continue;
                }
                //Testa se o lexema ainda casa com algum padrão
                erro = testa(lexema);

                if (!erro) {
                    //Evita a variável receber um número negativo
                    //pos = (pos == 0) ? 0 : pos - 1;


                    if (erro) {
                        erro = testa(lexema);
                    } else {
                        //coloca na tabela
                        //if (lexema.charAt(lexema.length() - 1) == ' ') {
                        //    pos++;
                        //}
                        //if (lexema.length() != 1) {
                        //    lexema = lexema.substring(-1);
                        //}
                        lexema = lexema.trim();
                        //Verifica se o lexema já esta na tabela de simbolos
                        if (!tabela.containsKey(lexema)) {
                            //Verifica se o estado é de erro
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

        System.out.println("\nDump da Tabela de Simbolos");
        dumpTabela();
    }
    
    /**
     * Função responsável por verifcar se existe algum casamento de padrão
     * @param lexema: lexema a ser testado
     * @return true casar, e falso caso contrário
     */
    public boolean testa(String lexema) {
        if (comparacao(lexema)) {
            return true;
        }
        if (operadorIgual(lexema)) {
            return true;
        }
        if (operador(lexema)) {
            return true;
        }
        if (comentarioLinha(lexema)) {
            return true;
        }
        if (comentarioMultiplo(lexema)) {
            return true;
        }
        if (ehDigit(lexema)) {
            return true;
        }
        if (ehFloat(lexema)) {
            return true;
        }
        return false;
    }
    /**
     * Função responsável por fazer o dump da tabela de simbolo
     */
    public void dumpTabela() {
        Iterator<TabelaSimbolo> i = tabela.values().iterator();
        while (i.hasNext()) {
            System.out.println(i.next().toString());
        }
    }

    //Insira as funções aqui.
    public boolean comparacao(String lexema) {
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
            switch (estado) {
                case "q1":
                    token = "GT";
                    break;
                case "q2":
                    token = "GE";
                    break;
                case "q3":
                    token = "LT";
                    break;
                case "q4":
                    token = "LE";
                    break;
            }
            return true;
        }

    }

    public boolean operadorIgual(String lexema) {
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
            switch (estado) {
                case "q1":
                    token = "ATR";
                    break;
                case "q2":
                    token = "EQ";
                    break;
            }
            return true;
        }

    }

    public Boolean operador(String palavra) {

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
                            estado = "q3";
                            break;
                        case "q1":
                            estado = "qerror";
                            break;
                    }
                }

                if (caracter == '*') {
                    switch (estado) {
                        case "q0":
                            estado = "q4";
                            break;
                        case "q4":
                            estado = "q5";
                            break;
                        case "q5":
                            estado = "qerror";
                            break;
                    }
                }
            }
            p++;
        }

        switch (estado){
            case "q1":
                token = "SUM";
                return true;
            case "q2":
                token = "INC";
                return true;
            case "q3":
                token = "DIV";
                return true;
            case "q4":
                token = "MUL";
                return true;
            case "q5": 
                token = "POW";
                return true;
        }
        return false;
    }

    public Boolean comentarioLinha(String palavra) {

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
            token = "COM";
            return true;
        } else {
            return false;
        }
    }

    public Boolean comentarioMultiplo(String palavra) {

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
            token = "COM";
            return true;
        } else {
            return false;
        }
    }

    public Boolean ehDigit(String palavra) {

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
            
            if (!Character.isDigit(caracter)){
                switch (estado) {
                    case "q0":
                        estado = "qerror";
                        break;
                    case "q1":
                        estado = "qerror";
                        break;
                }
            }
            p++;
        }
        if (estado.equals("q1")) {
            token = "INT";
            return true;
        } else {
            return false;
        }
    }

    public Boolean ehFloat(String palavra) {

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
            token = "FLOAT";
            return true;
        } else {
            return false;
        }
    }
}
