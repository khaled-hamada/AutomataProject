/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

/**
 *
 * @author khaled osman
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
    @Override
    public String toString(){
       return "<" + this.type + ", " + this.value + ", " + this.linen + ":" + this.columnn + ">";
    }
}
