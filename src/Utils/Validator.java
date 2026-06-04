package utils;

import java.util.regex.Pattern;

public class Validator {
    
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()){
            return false;
        }
        if (nombre.length() > 100){ 
            return false;
        }
        return Pattern.matches("^[a-zA-ZáéíóúñÁÉÍÓÚÑ\\s]+$", nombre);
    }
    
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()){ 
            return false;
        }
        if (email.length() > 100) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    
    public static boolean validarPassword(String password) {
        if (password == null) {
            return false;
        }
        if (password.length() < 8 || password.length() > 15) return false;
        
        boolean hasUpper = false;
        boolean hasNumber = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)){ 
                hasUpper = true;
            }
            if (Character.isDigit(c)){ 
                hasNumber = true;
            }
        }
        
        return hasUpper && hasNumber;
    }
    
    public static boolean validarCelular(String celular) {
        if (celular == null) {
            return false;
        }
        return Pattern.matches("^\\d{10}$", celular);
    }
    
    public static boolean validarCedula(String cedula) {
        if (cedula == null) {
            return false;
        }
        return Pattern.matches("^\\d{10}$", cedula);
    }
    
    
}