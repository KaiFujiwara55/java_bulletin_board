public class Post {
    private int id;
    private String user_name;
    private String content;
    private int like_count;

    public Post(int id, String user_name, String content, int like_count) {
        this.id = id;
        this.user_name = user_name;
        this.content = content;
        this.like_count = like_count;
    }

    public int getId() { return this.id; }
    public String getUserName() { return this.user_name; }
    public String getContent() { return this.content; }
    public int getLikeCount() { return this.like_count; }
}
