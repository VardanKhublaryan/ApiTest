package pojoClases;

public class SuccessRegister {
    private Integer id;
    private String token;

    SuccessRegister(){}

    SuccessRegister(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Integer getId() {
        return id;
    }
}
