package pojoClases;

public class CreateResponse {
    private String name;
    private String job;
    private Integer id;
    private String createdAt;

    CreateResponse(){}

    public CreateResponse(String name, String job, Integer id, String createdAt) {
        this.name = name;
        this.job = job;
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public Integer getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
