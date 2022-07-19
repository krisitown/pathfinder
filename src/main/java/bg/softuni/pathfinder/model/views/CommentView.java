package bg.softuni.pathfinder.model.views;

public class CommentView {
    private String message;
    private String authorName;

    public CommentView(String message, String authorName) {
        this.message = message;
        this.authorName = authorName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
