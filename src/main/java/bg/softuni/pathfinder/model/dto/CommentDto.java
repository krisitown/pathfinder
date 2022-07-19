package bg.softuni.pathfinder.model.dto;

public class CommentDto {
    private String message;

    public CommentDto() {
    }

    public CommentDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
