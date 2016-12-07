/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ken
 */
class IdPatternValidator 
        implements ConstraintValidator<IdPattern, String>{
    
    private int from;
    private int to;

    @Override
    public void initialize(IdPattern pattern) {
        from = pattern.from();
        to = pattern.to();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //長さは8
        if(value == null || value.length() != 8) return false;
        
        //先頭文字(小文字に変換)
        //先頭文字はa,b,c,A,B,C
        char top = value.toLowerCase().charAt(0);
        if(top != 'a' && top != 'b' && top != 'c') return false;

        //2文字名以降は数字
        for(int i=1; i< 8; i++){
            if(!Character.isDigit(value.charAt(i))) return false;
        }
        
        //fromからtoの間の西暦かどうか
        if(Integer.parseInt(value.substring(1,5)) < from) return false;
        if(Integer.parseInt(value.substring(1,5)) > to) return false;
        
        return true;
    }
    
}
