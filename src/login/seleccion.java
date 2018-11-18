package login;

public enum seleccion {

    Admin,      // for administrator
    User;       // for users

    private seleccion(){}

    private String value(){
        return name();
    }

    public static seleccion fromvalue(String val){
        return valueOf(val);
    }
}
