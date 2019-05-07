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
 * @email khaledosman737@gmail.com
 * 
 */
public class Lexer {
    
    /*
    * dectionary containing tokens type 
    */
    final private Map<String, String> tokenType = new HashMap<String, String>(){{
    
    // Keywords
    
    put("double", "keyword");  put("else", "keyword");put("false", "keyword");
    put("func", "keyword");put("for", "keyword");
    put("if", "keyword");put("int", "keyword");  put("null", "keyword");  put("return", "keyword");
    put("true", "keyword");  put("string", "keyword");put("char","keyword");put("elseif","keyword");
    put("print","keyword"); put("printf","keyword");put("main","keyword");put("boolean","keyword");
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
    put("++","Prefix/postfix increment");
    put("--","Prefix/postfix decrement");
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
    put("Identifier", "var - Identifier");
    put("Integer", "var - Integer");
    put("Decimal", "var - Decimal");
    put("String", "var - String");
    put("Char","var - Char");
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
    put("?", "Delimiter - question if ");
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
        add(':');add(';');add('?');
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
        add("^=");add("++");add("--");
    }};
    //  recognize language keywords 
    private final ArrayList<String> keyword =  new ArrayList<String>()
    {{
        add("double"); add("else");add("false");add("func");add("for");add("float");add("if");
        add("int");add("elseif");add("null");add("return");add("true");add("string");add("char");
        add("print");add("printf");add("main");add("boolean");
    }};
   
   
    // ------------------------------------------------------------
    // Contructor 
    public Lexer(String input ){
        this.input = input ;
        this.column = 0;
        this.line = 0;
        this.position = 0;
        this.paren = new Stack<>();
        this.allTokens = new ArrayList<>();
        
        
    }
    // ----------------------------------------------------------------------
    // ----------------------------------------------------------------------
    public List<Token> tokenize(){
        
        Token token = nextToken();
        
        while(! token.getType().equals( tokenType.get("EndOfInput"))){
            if(token.getType().equals( tokenType.get("Error")) ||
                   token.getType().equals( tokenType.get("Unrecognized")) ){
                System.out.println(token.toString());
                printErrorLine(token.getLine());
                this.allTokens = null ;
                return this.allTokens ;
                
            }
            
            this.allTokens.add(token);
            token = nextToken();
        }
        //check for parenthesis matching 
        if(this.paren.size() > 0){
            System.out.println("Unbalanced parenthesis due to these "+this.paren.toString());
            printErrorLine(token.getLine());
            this.allTokens = null ;
            return this.allTokens  ;
        }
            
       
        return this.allTokens ;
    }
    
    // ----------------------------------------------------------------
    // ----------------------------------------------------------------------
    private Token nextToken(){
        char c ,lastc = '\0' ;
        while(this.position < this.input.length()){
            // get next char in input 
            c = this.input.charAt(this.position);
           // System.out.println("char c = "+c);
            //get out of infinte loops 
            if(c == lastc)
                 return new Token(this.tokenType.get("Error"),c+" "+lastc,this.line,this.column);
            //update last c 
            lastc = c;
            //check white spaces 
            if(Character.isWhitespace(c))
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
               return  recognizeDelimiter();
            }
            // check for operators 
            // 1- check for double operators 
            if (this.position +1 < this.input.length()){
                char cLookA = this.input.charAt(this.position+1);
                if(this.operatorDC.contains(c+""+cLookA)){
                  //  System.out.println("String double operator = "+(c+""+cLookA));
                    this.position += 2;
                    this.column += 2;
                    return new Token(this.tokenType.get(c+""+cLookA),c+""+cLookA,this.line,
                                     this.column -2);
                
               }
              
            }
            // check numbers before single operators 
            // 2- check for single operator  define recognize number especailly negative 
            // and positive preceded by a + sign before it 
            //check prevsious token if its = then this in a number not an operator 
            if(isStartOfNumber(c))
                return recognizeNumber();
            if(this.operatorSC.contains(c)){
                   this.position += 1;
                   this.column += 1;
                   return new Token(this.tokenType.get(c+""),c+"",this.line,
                                     this.column -1);
              }
             // recognize identifiers  and keywords 
            if(Character.isAlphabetic(c) || c =='_')
                return recognizeKW_ID();
             
            // recognize string varialbes 
            if(c == '\"')
                return recognizeStringVar();
            // recognize char var 
            if(c == '\''){
               // System.out.println("Stuck here ");
                return recognizeCharVar();
            }
              
        }
       
        // end of code file     
        return new Token(this.tokenType.get("EndOfInput")," ",this.line,this.column);
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
    private Token  recognizeDelimiter(){
        char c = this.input.charAt(this.position);
        //update lexer var 
        this.position += 1;
        this.column +=1 ;
        
        if(c == '[' || c== '{' || c == '('){
            this.paren.push(c);
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column-1);
        }
        else if(c == ']' || c== '}' || c == ')'){
            //check for parenthesis matching
            if(this.paren.size() > 0){
                switch (c) {
                    case '}':
                         if(this.paren.pop() == '{')
                             return new Token(this.tokenType.get(c+""),c+"",this.line, this.column-1);
                         else 
                             break ;
                    case ']':
                        if(this.paren.pop() == '[')
                             return new Token(this.tokenType.get(c+""),c+"",this.line, this.column-1);
                         else 
                             break ;
                    case ')':
                        if(this.paren.pop() == '(')
                             return new Token(this.tokenType.get(c+""),c+"",this.line, this.column-1);
                         else 
                             break ;

                    default :
                }
                // unmatched prentheses 
                return new Token(this.tokenType.get("Error"),c+" unmatched parenthesis at ",this.line, this.column-1);
            
            }
            else
                // unmatched prentheses 
                return new Token(this.tokenType.get("Error"),c+" unmatched parenthesis at ",this.line, this.column-1);
            
        }
        else if(c == ':' )
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column-1);
        else if(c == ',')
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column-1);
        else 
            return new Token(this.tokenType.get(c+""),c+"",this.line, this.column-1);
           
    }
    
    // ----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    
    /**
     * recognize keywords and identifiers 
     */
    private Token recognizeKW_ID(){
        String tokenVal = "";
        int templ = this.line ;
        int tempc = this.column ;
        while(this.position < this.input.length() ){
            char c = this.input.charAt(this.position);
            if((Character.isLetterOrDigit(c) ||c =='_' )){
                tokenVal += c;
                this.position +=1 ;
                this.column += 1;
            }
            else
                break ;
        }
        //check for  keywords
        if(this.keyword.contains(tokenVal))
            return new Token(this.tokenType.get(tokenVal),tokenVal, templ,tempc);
        else
            return new Token(this.tokenType.get("Identifier"),tokenVal, templ,tempc);
    }
    
    
    // ----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    /**
     * 
     */
    private Token recognizeStringVar(){
        String tokenVal = "";
        char c =this.input.charAt(this.position) ;
        // proceed position by one to skip first "   
        this.position +=1 ;
        int templ = this.line ;
        int tempcol = this.column+1;
        while(this.position < this.input.length() && 
               (c = this.input.charAt(this.position))!='\"' ){
                tokenVal += c;
                this.position +=1 ;
                this.column += 1;
                if(c == '\n'){
                    this.line += 1;
                    this.column = 0;
                }
        }
        if(c == '\"'){
            // skip second " 
            this.position += 1;
            this.column +=1;

            return new Token(this.tokenType.get("String"),tokenVal,templ,tempcol);
        }
        else 
            return new Token(this.tokenType.get("Unrecognized"),tokenVal,templ,tempcol);
    }
    
    // ----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    
    /**
     * 
     */
    private Token recognizeCharVar(){
        
         char c =this.input.charAt(this.position);
         // check ascci chars 
        if(this.position + 2 < this.input.length() ){
                c = this.input.charAt(this.position +1);
                if(c >= 0 && c <= 127){
                   // System.out.println("char c inside = "+c);
                    this.position +=1 ;
                    this.column += 1;
                    if(this.input.charAt(this.position + 1) == '\''){
                        // skip second single quotation and return a new char token 
                   //    System.out.println("char c inside = "+c); 
                       this.position +=2 ;
                       this.column += 2;
                       return new Token(this.tokenType.get("Char"),c+"",this.line,this.column - 2);
                    }
                }
                else
                     return new Token(this.tokenType.get("Unrecognized"),c+"",this.line,this.column);
            }
     
        return new Token(this.tokenType.get("Unrecognized"),c+"",this.line,this.column);
    }
    
    
    // ----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    /**
     * recognize all kinds of numbers including integers , floats, fractions , positive , negative ....
     * using FSM 
     */
    private Token recognizeNumber(){
        NumberFSM fsm = new NumberFSM(this.input.substring(this.position), 
                                        this.line, this.column);
        
        Token token = fsm.runFSM() ; 
        this.line = token.getLine() ; 
        this.position += token.getValue().length();
        this.column += token.getValue().length();
       
       return token ;
    }
    
    ////////////////////////////////////////////////
    // helper methods for recognize numbers 
    //recognize the start state of our Number FSM 
    private boolean isStartOfNumber(char c){
        if(Character.isDigit(c))
            return true ; 
        //detect positvie / negative / decimal numbers 
        if(c == '.' || c =='+' || c =='-'){
            // check previous token if its an operator  then we are correct 
            //else return false 
            if(this.allTokens.size() >0){
                 Token prev = this.allTokens.get(this.allTokens.size() - 1);
                 String tokenVal = prev.getValue();
                 if(tokenVal.length() <= 2 && (this.operatorDC.contains(tokenVal)
                         || this.operatorSC.contains(tokenVal.charAt(0)))){
                     
                     return true ;
                 }
                     
                 
            }
            return true ; 
        }
            
        return false ;
    }
    
    
    
    
    
    
   
    // ----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void printTokens(){
        if(this.allTokens != null){
            if(this.allTokens.size() >0 ){
                System.out.printf("%-30s %-30s %-5s %-5s \n","type","value","line#","col#");
                System.out.printf("-----------------------------------------------------------------------------------------------\n");
                for(int i = 0 ; i<this.allTokens.size() ; i++){
                    System.out.println(this.allTokens.get(i).toString());
                    System.out.printf("-------------------------------------------------------------------------------------------\n");
                }
            }
            else 
                System.out.println("There is no tokens in the input just white spaces ");
        }
       
    }
    // ----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    private void printErrorLine(int linen){
       System.out.println("error occurs in line # "+linen +" in the code file"
               + " which is : \n"+ this.input.split("\n")[linen]+"\n");
        
    }
    
}
