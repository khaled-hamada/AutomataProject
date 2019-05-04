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
    final private Map<String, String> tokenType = new HashMap<String, String>(){{
    
    // Keywords
    
    put("double", "keyword"); 
    put("else", "keyword");
    put("false", "keyword");
    put("func", "keyword");
    put("for", "keyword");
    put("float", "keyword");
    put("if", "keyword");
    put("int", "keyword");
    put("null", "keyword");
    put("return", "keyword");
    put("true", "keyword");
    put("String", "keyword");
    
    // Dispatch operators
    put(".", "Dispatch operator");

    // Assignment operators
    put( "<-", "Assignment operators - LeftArrow");
    put( "/=","Assignment operators - DivEqual");
    put( "=", "Assignment operators - Equal");
    put("-=", "Assignment operators - MinusEqual");
    put( "%=", "Assignment operators - ModuloEqual");
    put( "+=", "Assignment operators - PlusEqual");
    put( "->", "Assignment operators - RightArrow");
    put( "*=","Assignment operators - TimesEqual");

    // Arithmetic operators
    put( "/","Arithmetic operators- Div");
    put( "%","Arithmetic operators- Modulo");
    put( "-", "Arithmetic operators- Minus");
    put( "+", "Arithmetic operators- Plus");
    put( "*", "Arithmetic operators- multiplication");

    // Comparison operators
    put( "==", "Comparison operators - DoubleEqual");
    put( ">", "Comparison operators - Greater");
    put( ">=", " Comparison operators -  GreaterOrEqual");
    put( "<", "Comparison operators -Less");
    put( "<=", "Comparison operators - LessOrEqual");
    put("!=","Comparison operators - NotEqual");

    // Boolean operators
    put("&&", "Boolean operator - And");
    put( "!", "Boolean operator - Not");
    put( "||", "Boolean operator - Or");

    // Identifier and Literals
    put("Identifier", "identifier");
    put("Integer", "integer");
    put("Decimal", "decimal");
    put("String", "string");

    // Delimiters
    put(":", "Delimiter - Colon");
    put( ",", "Delimiter - Comma");
    put("{","Delimiter - LeftBrace");
    put("[", "Delimiter - LeftBracket");
    put("(", "Delimiter - LeftParen");
   // put("\n","Newline");
    put("}", "Delimiter - RightBrace");
    put("]", "Delimiter - RightBracket");
    put(")","Delimiter - RightParen");
    put(";","Delimiter - semiColon" );
    // Special token types
    put("EndOfInput", "EndOfInput");
    put("Unrecognized", "Unrecognized");
    put("Error","Syntax Error");
    }};
    
    //////////////////////////////////////////////////
    // -------------------------------------------------------------------
    // private var 
    private String input ;
    private int line ;
    private int column ;
    private int position ;
    private List<Token> allTokens;
    // stack to check parenthesis 
    private Stack<Character> paren ;
    private final ArrayList<Character> delimiter =  new ArrayList<Character>()
    {{
        add('('); add(')');add('[');add(']');add('{');add('}');add(',');
        add(':');add(';');
    }};
    // recognize single char operators 
   private final ArrayList<Character> operatorSC =  new ArrayList<Character>()
    {{
        add('.'); add('=');add('/');add('%');add('-');add('+');add('*');
        add('>');add('<');add('!');add('$');add('^');add('~');add('&');add('|');
        
    }};
   //recoognize double chars operators 
   private final ArrayList<String> operatorDC =  new ArrayList<String>()
    {{
        add("<-"); add("/=");add("-=");add("%=");add("+=");add("->");add("*=");
        add("==");add(">=");add("<=");add("!=");add("&&");add("||");add("~=");add("$=");
        add("^=");
    }};
    
   
   
    // ------------------------------------------------------------
    // Contructor 
    public Lexer(String input ){
        this.input = input ;
        this.column = 0;
        this.line = 0;
        this.position = 0;
        this.paren = new Stack<>();
        
        
    }
    // ----------------------------------------------------------------------
    // ----------------------------------------------------------------------
    public List<Token> tokenize(){
        List<Token> tokens = new ArrayList<>();
        Token token = nextToken();
        
        while(! token.getType().equals( tokenType.get("EndOfInput"))){
            if(token.getType().equals( tokenType.get("Error")) ||
                   token.getType().equals( tokenType.get("Unrecognized")) ){
                System.out.println(token.toString());
                this.allTokens = null ;
                return null ;
                
            }
            
            tokens.add(token);
            token = nextToken();
        }
        //check for parenthesis matching 
        if(this.paren.size() > 0){
            System.out.println("Unbalanced parenthesis due to these "+this.paren.toString());
            this.allTokens = null ;
            return null ;
        }
            
        this.allTokens = tokens ;
        return tokens ;
    }
    
    // ----------------------------------------------------------------
    // ----------------------------------------------------------------------
    private Token nextToken(){
        char c ;
        while(this.position < this.input.length()){
            // get next char in input 
            c = this.input.charAt(this.position);
            //System.out.println("char c = "+c);
            //check white spaces 
            if(c ==' ' || c == '\t' || c == '\n')
                removeWhiteSpaces();
            
            //remove comments 
            if(c == '/'){
                if((this.position + 1) <this.input.length() &&
                        this.input.charAt(this.position + 1) == '/'){
                    this.position += 2; 
                    this.column +=2;
                    removeComments();
                }
                // make remove comments one ant two for /* */ 
            }
            
            //recongnize delimiters 
            if(this.delimiter.contains(c)){
               // System.out.println("inside paren test");
               return  reconizeDelimiter();
            }
            // check for operators 
            // 1- check for double operators 
            if (this.position +1 < this.input.length()){
                char cLookA = this.input.charAt(this.position+1);
                if(this.operatorDC.contains(c+""+cLookA)){
                    this.position += 2;
                    this.column += 2;
                    return new Token(this.tokenType.get(c+""+cLookA),c+""+cLookA,this.line,
                                     this.column -2);
                
               }
              
            }
            // 2- check for single operator 
            if(this.operatorSC.contains(c)){
                   this.position += 1;
                   this.column += 1;
                   return new Token(this.tokenType.get(c+""),c+"",this.line,
                                     this.column -1);
              }
              
            
            
//            else{
//                System.out.println("Hey am here get a token ");
//                this.column +=2; 
//                this.position +=2; 
//                return new Token("mm","mm",this.line,this.column);
//                
//            } 
               
        }
        if(this.position >= this.input.length())
            return new Token(this.tokenType.get("EndOfInput")," ",0,0);
        
        return new Token("mm","mm",this.line,this.column);
    }
    
    
    // ----------------------------------------------------------------------
    // ----------------------------------------------------------------------
    // remove white spaces from input so 
    //that 1+2, 1 + 2 or 1+ 2, for example, all yield the same sequence of tokens
    private void  removeWhiteSpaces(){
       
        while(this.position <this.input.length()){
            char c = this.input.charAt(this.position);
            this.position +=1;
            if(c == ' ' || c == '\t')
                this.column += 1 ;
            else if(c == '\n'){
                this.line +=1;
                this.column = 0;
            }
            else {
                this.position -=1;
                break ;
            }
        }
        
    }
    // ----------------------------------------------------------------------
    //---------------------------------------------
    /**
     * this function removes comments 
    */
   
    private void  removeComments(){
        while(this.position <this.input.length() && 
                this.input.charAt(this.position) != '\n')
        {
            this.position += 1;  
            
        }
        //new line 
        this.line += 1;
        this.column = 0;
        this.position += 1;
    }
    
    //------------------------------------------------------------------
    // ----------------------------------------------------------------------
    private Token  reconizeDelimiter(){
        char c = this.input.charAt(this.position);
        //update lexer var 
        this.position += 1;
        this.column +=1 ;
        
        if(c == '[' || c== '{' || c == '('){
            this.paren.push(c);
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column);
        }
        else if(c == ']' || c== '}' || c == ')'){
            //check for parenthesis matching
            if(this.paren.size() > 0){
                switch (c) {
                    case '}':
                         if(this.paren.pop() == '{')
                             return new Token(this.tokenType.get(c+""),c+"",this.line, this.column);
                         else 
                             break ;
                    case ']':
                        if(this.paren.pop() == '[')
                             return new Token(this.tokenType.get(c+""),c+"",this.line, this.column);
                         else 
                             break ;
                    case ')':
                        if(this.paren.pop() == '(')
                             return new Token(this.tokenType.get(c+""),c+"",this.line, this.column);
                         else 
                             break ;

                    default :
                }
                // unmatched prentheses 
                return new Token(this.tokenType.get("Error"),c+" unmatched parenthesis at ",this.line, this.column);
            
            }
            else
                // unmatched prentheses 
                return new Token(this.tokenType.get("Error"),c+" unmatched parenthesis at ",this.line, this.column);
            
        }
        else if(c == ':' )
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column);
        else if(c == ',')
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column);
        else 
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column);
           
    }
    
    
    // ----------------------------------------------------------------------
    //----------------------------------------------------
    public void printTokens(){
        if(this.allTokens != null){
            if(this.allTokens.size() >0 ){
                System.out.println("Type\t\t\t\t\t\t" +"value\t\t\t" + "line#\t" + "column#\t");
                for(int i = 0 ; i<this.allTokens.size() ; i++){
                    System.out.println(this.allTokens.get(i).toString());
                }
            }
            else 
                System.out.println("There is no tokens in the input just white spaces ");
        }
       
    }
    
}
