/*
 * To change this license header); choose License Headers in Project Properties.
 * To change this template file); choose Tools | Templates
 * and open the template in the editor.
 */
package lexer ;

import java.util.*;
/**
 *
 * @author khaled osman
 */
public class Lexer {
    /*
    * dectionary containing tokens type 
    */
    final public Map<String, String> tokenType = new HashMap<String, String>(){{
    
    // Keywords
       
    put("Else", "else");
    put("False", "false");
    put("Func", "func");
    put("For", "for");
    put("If", "if");
    put("Null", "null");
    put("Return", "return");
    put("True", "true");

    // Dispatch operators
    put("Dot", ".");

    // Assignment operators
    put("LeftArrow", "<-");
    put("DivEqual", "/=");
    put("Equal", "=");
    put("MinusEqual", "-=");
    put("ModuloEqual", "%=");
    put("PlusEqual", "+=");
    put("RightArrow", "->");
    put("TimesEqual", "*=");

    // Arithmetic operators
    put("Div", "/");
    put("Modulo", "%");
    put("Minus", "-");
    put("Plus", "+");
    put("Times", "*");

    // Comparison operators
    put("DoubleEqual", "==");
    put("Greater", ">");
    put("GreaterOrEqual", ">=");
    put("Less", "<");
    put("LessOrEqual", "<=");
    put("NotEqual", "!=");

    // Boolean operators
    put("And", "&&");
    put("Not", "!");
    put("Or", "||");

    // Identifier and Literals
    put("Identifier", "identifier");
    put("Integer", "integer");
    put("Decimal", "decimal");
    put("String", "string");

    // Delimiters
    put("Colon", ",");
    put("Comma", ");");
    put("LeftBrace", "{");
    put("LeftBracket", "[");
    put("LeftParen", "(");
    put("Newline", "\n");
    put("RightBrace", "}");
    put("RightBracket", "]");
    put("RightParen", ")");

    // Special token types
    put("EndOfInput", "EndOfInput");
    put("Unrecognized", "Unrecognized");
    }};
    
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    // TODO code application logic here
    System.out.println("Hello Bn Osman here starting Automata Project right now ");
    }
    
}
