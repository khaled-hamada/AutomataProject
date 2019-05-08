/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

/**
 *
 * @author khaled osman
 * @email khaledosman737@gmail.com
 */
public class Token {
    private String type;
    private String value ;
    private int linen ;
    private int columnn;
    public Token(String type , String value , int linen, int columnn){
        this.type = type ;
        this.value = value;
        this.linen = linen ;
        this.columnn = columnn ;
    }
    public String getType(){
        return this.type;
    }
    public String getValue(){
        return this.value;
    }
    public int getLine(){
        return this.linen;
    }
    public int getcolumn(){
        return this.columnn;
    }
    @Override
    public String toString(){
       return  String.format("%-30s %-30s %-6d %-6d",this.type, this.value ,this.linen , this.columnn);
    
    }
}
