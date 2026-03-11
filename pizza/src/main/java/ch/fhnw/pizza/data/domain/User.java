public class User{

    private String id;
    private String name;
    private String lastname;

    public User(String name, String lastname) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.lastname = lastname;
    }

}