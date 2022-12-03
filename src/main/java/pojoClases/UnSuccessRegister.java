package pojoClases;

public class UnSuccessRegister {
    private String error = "Missing password";


    UnSuccessRegister(){}

    UnSuccessRegister(String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
