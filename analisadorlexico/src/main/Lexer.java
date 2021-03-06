package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Lexer {

    private int tam;
    private ArrayList<String> arquivo;
    private Map<String, TabelaSimbolo> tabela;
    private int pos;
    private String token;
    private boolean erro;
    private String lexema;

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
        lexema = "";

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
                    lexema += "\n";
                } else {
                    //Adiciona mais um caracter ao lexema
                    lexema += arquivo.get(i).charAt(pos);
                }
                if (lexema.equals(" ") || lexema.equals("\b") || lexema.equals("\t")) {
                    pos++;
                    lexema = "";
                    continue;
                }
                //Testa se o lexema ainda casa com algum padrão
                erro = lex(lexema);

                if (!erro) {
                    lexema = lexema.trim();
                    //Verifica se o lexema já esta na tabela de simbolos
                    if (!tabela.containsKey(lexema)) {
                        //Verifica se o estado é de erro
                        if (!"erro".equals(token)) {
                            TabelaSimbolo t = new TabelaSimbolo();
                            t.setToken(token);
                            t.setEndereco(endereco);
                            if (!lex(lexema)) {
                                t.setDefinicao(lexema.substring(0, lexema.length() - 1));

                                if (!tabela.containsKey(t.getDefinicao())) {
                                    tabela.put(lexema.substring(0, lexema.length() - 1), t);
                                    System.out.println("<" + token + "," + endereco + ">");
                                    endereco++;
                                    token = "";

                                    lexema = "";
                                    continue;
                                } else {
                                    TabelaSimbolo ts = tabela.get(t.getDefinicao());
                                    System.out.println("<" + token + "," + ts.getEndereco() + ">");
                                    lexema = "";
                                    token = "";
                                    continue;
                                }
                            }
                            t.setDefinicao(lexema);

                            tabela.put(lexema, t);
                            System.out.println("<" + token + "," + endereco + ">");
                            endereco++;
                            token = "";
                        } else {
                            System.out.println("INVALIDO (" + lexema + ") LINHA " + (i+1));
                            token = "";
                        }
                    } else {
                        TabelaSimbolo ts = tabela.get(lexema);
                        System.out.println("<" + token + "," + ts.getEndereco() + ">");
                        token = "";
                    }
                    lexema = "";
                }
                pos++;
            }
            if (lexema.length() > 0) {
                lexema += "\n";
            }
            pos = 0;
        }
        if (!lexema.equals("")) {
            System.out.println("INVALIDO (" + lexema.trim() + ") LINHA " + (tam+1));
        }
        System.out.println("\nDump da Tabela de Simbolos");
        dumpTabela();
    }

    /**
     * Função responsável por verifcar se existe algum casamento de padrão
     *
     * @param lexema: lexema a ser lexdo
     * @return true casar, e falso caso contrário
     */
    public boolean lex(String lexema) {
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
        if (numerico(lexema)) {
            return true;
        }
        if (invalido(lexema)) {
            return true;
        }
        return false;
    }

    /**
     * Função responsável por fazer o dump da tabela de simbolo
     */
    public void dumpTabela() {
        Iterator<TabelaSimbolo> i = tabela.values().iterator();
        int t = tabela.size();
        TabelaSimbolo[] tab = new TabelaSimbolo[t];
        TabelaSimbolo item;

        for (int m = 0; m < t; m++) {
            item = i.next();
            tab[item.getEndereco()] = item;
        }

        for (int m = 0; m < t; m++) {
            System.out.println(tab[m].toString());
        }
    }


    /**
     * Função que verifica os seguintes lexemas: ">","<",">=" e "<="
     * @param lexema
     * @return 
     */
    public boolean comparacao(String lexema) {
        //Estado atual. O estado q0 é o estado inicial
        String estado = "q0";
        char caracter = ' ';
        int tam = lexema.length();
        int i = 0;
        while (i < tam) {
            /* Recebe o próximo caracter do lexema, pois a função verifica se 
             * parte do lexema, ou ele todo, é válido           
             */
            caracter = lexema.charAt(i);
            if (caracter == '>') {
                //Verfica o estado atual para que sejam feitas as transições de estado 
                switch (estado) {
                    case "q0":
                        //Estado q1
                        estado = "q1";
                        break;
                    default:
                        /* Caso o estado atual seja diferente de algum estado 
                         * válido para formar um lexema válido ou parte deste, 
                         * o estado é atualizado para o estado qErr (Estado de Erro),
                         * mas é preciso ler os outros caracteres do lexema para 
                         * que seja retornado 'false'
                         */
                        estado = "qErr";
                        break;
                }
            } else if (caracter == '=') {
                //Verfica o estado atual para que sejam feitas as transições de estado 
                switch (estado) {
                    case "q1":
                        //Estado q2
                        estado = "q2";
                        break;
                    case "q3":
                        //Estado q4
                        estado = "q4";
                        break;
                    default:
                        estado = "qErr";
                        break;
                }
            } else if (caracter == '<') {
                //Verfica o estado atual para que sejam feitas as transições de estado 
                switch (estado) {
                    case "q0":
                        //Estado q3
                        estado = "q3";
                        break;
                    default:
                        estado = "qErr";
                        break;
                }
            } else {
                /* Se algum caracter lido for diferente de um caracter válido,
                 * então o lexema é inválido para esta função
                 */
                estado = "qErr";
            }
            i++;
        }
        /* Se todos os caracteres forem lidos, e o estado atual não for um estado
         * final, entao é retornado 'false'
         */
        if ((estado.equals("q0")) || (estado.equals("qErr"))) {
            return false;
        } else {
            //Algum dos estados finais foi alcançado
            //Associa o estado atual a um token 
            switch (estado) {
                case "q1":
                    //Token recebe o valor "GT" representando o lexema ">"
                    token = "GT";
                    break;
                case "q2":
                    //Token recebe o valor "GE" representando o lexema ">="
                    token = "GE";
                    break;
                case "q3":
                    //Token recebe o valor "LT" representando o lexema "<"
                    token = "LT";
                    break;
                case "q4":
                    //Token recebe o valor "GE" representando o lexema "<="
                    token = "LE";
                    break;
            }
            //Retorna 'true' validando o lexema como válido para esta função
            return true;
        }

    }

    /**
     * Verifica os seguintes lexemas: "=" e "=="
     * @param lexema
     * @return 
     */
    public boolean operadorIgual(String lexema) {
        //Estado atual. O estado q0 é o estado inicial
        String estado = "q0";
        char caracter = ' ';
        int tam = lexema.length();
        int i = 0;
        while (i < tam) {
            /*Recebe o próximo caracter do lexema, pois a função verifica se 
             *parte do lexema ou ele todo é válido ou não           
             */
            caracter = lexema.charAt(i);
            if (caracter == '=') {
                //Verfica o estado atual para que sejam feitas as transições de estado 
                switch (estado) {
                    case "q0":
                        estado = "q1";
                        break;
                    case "q1":
                        estado = "q2";
                        break;
                    default:
                        /* Caso o estado atual seja diferente de algum estado 
                         * válido para formar um lexema válido ou parte deste, 
                         * o estado é atualizado para o estado qErr (Estado de Erro),
                         * mas é preciso ler os outros caracteres do lexema para 
                         * que seja retornado 'false'
                         */
                        estado = "qErr";
                        break;
                }
            } else {
                /* Se algum caracter lido for diferente de um caracter válido,
                 * então o lexema é inválido para esta função
                 */
                estado = "qErr";
            }
            i++;
        }
        /* Se todos os caracteres forem lidos, e o estado atual não for um estado
         * final, entao é retornado 'false'
         */
        if ((estado.equals("q0")) || (estado.equals("qErr"))) {
            return false;
        } else {
            //Algum dos estados finais foi alcançado
            //Associa o estado atual a um token 
            switch (estado) {
                case "q1":
                    //Token recebe o valor "ATR" representando o lexema "="
                    token = "ATR";
                    break;
                case "q2":
                    //Token recebe o valor "EQ" representando o lexema "=="
                    token = "EQ";
                    break;
            }
            //Retorna 'true' validando o lexema como válido para esta função
            return true;
        }

    }

    /**
     * Função que verifica os seguintes lexemas '+', '++','/','*','**'
     * @param palavra
     * @return 
     */
    public Boolean operador(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            //Recebe o caracter da posição atual
            caracter = palavra.charAt(p);
            //Verifica o caracter que está sendo passado
            switch (caracter) {
                //Verificação do caracter "+"
                case '+':
                    switch (estado) {
                        case "q0":
                            //Estado recebe q1 encontrando-se "+"
                            estado = "q1";
                            break;
                        case "q1":
                            //Estado recebe q2 encontrando-se outro "+"
                            estado = "q2";
                            break;
                        case "q2":
                            //Estado de erro caso outro caracter "+" seja encontrado
                            estado = "qerror";
                            break;
                    }
                    break;
                //Verificação do caracter "/"    
                case '/':
                    switch (estado) {
                        case "q0":
                            //Estado recebe q3 
                            estado = "q3";
                            break;
                        case "q3":
                            //Estado de erro caso outro caracter "/" seja encontrado
                            estado = "qerror";
                            break;
                    }
                    break;
                //Verificação do caracter "*"    
                case '*':
                    switch (estado) {
                        case "q0":
                            //Estado recebe q4
                            estado = "q4";
                            break;
                        case "q4":
                            //Estado recebe q5 caso outro "*" seja encontrado
                            estado = "q5";
                            break;
                        case "q5":
                            //Estado de erro caso outro caracter "*" seja encontrado
                            estado = "qerror";
                            break;
                    }
                    break;
                //Caso caracter não seja nenhum dos caracteres verificados estado de erro encontrado    
                default:
                    estado = "qerror";
                    break;
            }
            //Incrementa-se posição
            p++;
        }

        //Atribuição dos tokens
        switch (estado) {
            case "q1":
                //token recebe SUM retorna true
                token = "SUM";
                return true;
            case "q2":
                //token recebe INC retorna true
                token = "INC";
                return true;
            case "q3":
                //token recebe DIV retorna true
                token = "DIV";
                return true;
            case "q4":
                //token recebe MUL retorna true
                token = "MUL";
                return true;
            case "q5":
                //token recebe POW retorna true
                token = "POW";
                return true;
            default:
                //Retorna false por não ter casado nenhum padrão.
                return false;
        }
    }
    /**
     * Função que verifica se o lexema é um comentário de única linha
     * @param palavra
     * @return 
     */
    public Boolean comentarioLinha(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);

            switch (caracter) {
                case '/':
                    switch (estado) {
                        case "q0":
                            estado = "q1";
                            break;
                        case "q1":
                            estado = "q2";
                            break;
                    }
                    break;
                case '\n':
                    switch (estado) {
                        case "q2":
                            estado = "q3";
                            break;
                    }
                    break;
                default:
                    switch (estado) {
                        case "q1":
                            estado = "qerror";
                            break;
                        case "q2":
                            estado = "q2";
                            break;
                    }
                    break;
            }

            p++;
        }
        //Se o estado é o estado final q3
        if (estado.equals("q0") || estado.equals("q1") || estado.equals("q3") || estado.equals("qerror")) {
            //Token recebe COM retorna-se true
            return false;
        } else {
            //Caso contrário retorna se false por não ter casado padrão.
            token = "COM";
            return true;
        }
    }
    /**
     * Função que verifica se o lexema é um comentário de múltiplas linhas
     * @param palavra
     * @return 
     */
    public Boolean comentarioMultiplo(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);

            //Se caracter é / ou * que são importantes para comentário multiplo então...
            switch (caracter) {
                case '/':
                    switch (estado) {
                        case "q0":
                            estado = "q1";
                            break;
                        case "q3":
                            estado = "q4";
                            break;
                    }
                    break;
                case '*':
                    switch (estado) {
                        case "q1":
                            estado = "q2";
                            break;
                        case "q2":
                            estado = "q3";
                            break;
                    }
                    break;
                default:
                    switch (estado) {
                        case "q2":
                            estado = "q2";
                            break;
                        case "q4":
                            estado = "q5";
                    }
                    break;
            }
            p++;
        }
        //Se o estado é igual a q4 que é estado final para comentário multiplo
        if (estado.equals("q0") || estado.equals("q1") || estado.equals("q5")) {
            //Token recebe COM e retorna se true
            return false;
        } else {
            //Caso contrario retorna-se false por não ter casado padrão
            token = "COM";
            return true;
        }
    }

    /**
     * Função que verifica se o lexema é numérico
     * @param palavra
     * @return 
     */
    public Boolean numerico(String palavra) {

        String estado = "q0";
        char caracter = ' ';
        int t = palavra.length();
        int p = 0;

        while (p < t) {
            caracter = palavra.charAt(p);
            //Se o caracter é um digito ou um ponto o que interessa para analise

            if (Character.isDigit(caracter)) {
                switch (estado) {
                    case "q0":
                        //Estando em q0 vai-se para q1 
                        estado = "q1";
                        break;
                    case "q1":
                        //Estando em q1 permance-se em q1 até que um ponto se encontrado
                        estado = "q1";
                        break;
                    case "q2":
                        //Estando em q2 permanece-se em q2 pois podem haver mais digitos apos o ponto
                        estado = "q2";
                        break;
                }
            } else if (caracter == '.') {
                //Caso o ponto seja encontrado
                switch (estado) {
                    case "q1":
                        //Estando em q1 pois ja se encontrou um digito vai-se para q2 que é estado final para float
                        estado = "q2";
                        break;
                    case "q2":
                        estado = "qerror";
                        break;
                }
            } else {
                //Caso não seja digito ou ponto vai-se para estado de erro
                estado = "qerror";

            }
            p++;
        }
        switch (estado) {
            case "q1":
                //token recebe INT caso estado seja q1 estado final para int
                token = "INT";
                return true;
            case "q2":
                //Token recebe float caso em estado q2 estado final para float
                token = "FLOAT";
                return true;
        }
        //Retorna false caso nao case padrões
        return false;
    }
    
    /**
     * Função que verifica se o lexema é inválido
     * @param lexema
     * @return 
     */
    public Boolean invalido(String lexema) {

        String estado = "q0";
        char caracter = ' ';
        int t = lexema.length();
        int p = 0;

        if (!token.equals("") && !token.equals("erro")) {
            return false;
        }

        while (p < t) {
            caracter = lexema.charAt(p);

            if (!Character.isWhitespace(caracter) && !lexema.contains(" ")) {
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
        switch (estado) {
            case "q1":
                token = "erro";
                return true;
        }
        //Retorna false caso nao case padrões
        return false;
    }
}
