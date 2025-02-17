public class User {
    private int id;
    private String name;
    private String crypted_password;
    private String profile;

    public User(int id, String name, String crypted_password, String profile) {
        this.id = id;
        this.name = name;
        this.crypted_password = crypted_password;
        this.profile = profile;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getCryptedPassword() { return this.crypted_password; }
    public String getProfile() { return this.profile; }
}
