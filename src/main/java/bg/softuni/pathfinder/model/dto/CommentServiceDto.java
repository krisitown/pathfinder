package bg.softuni.pathfinder.model.dto;

public class CommentServiceDto {
    private String message;
    private String userName;
    private Long routeId;

    public CommentServiceDto(String message, String userName, Long routeId) {
        this.message = message;
        this.userName = userName;
        this.routeId = routeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
