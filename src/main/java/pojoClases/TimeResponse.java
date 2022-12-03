package pojoClases;

public class TimeResponse extends Create {
    private String updatedAt;

    TimeResponse() {
    }
    public TimeResponse(String name, String job) {
        super(name, job);
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
