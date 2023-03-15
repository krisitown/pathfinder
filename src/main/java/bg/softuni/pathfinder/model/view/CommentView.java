package bg.softuni.pathfinder.model.view;

public class CommentView {
    private Long id;
    private String text;
    private String authorName;
    private String dateOfCreation;

    public CommentView(Long id, String text, String authorName, String dateOfCreation) {
        this.id = id;
        this.text = text;
        this.authorName = authorName;
        this.dateOfCreation = dateOfCreation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
