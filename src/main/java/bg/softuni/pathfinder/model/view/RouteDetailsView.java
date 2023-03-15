package bg.softuni.pathfinder.model.view;

import java.util.List;

public class RouteDetailsView {
    private Long id;
    private String name;
    private String description;
    private String authorName;
    private String videoId;
    private String difficulty;
    private List<String> pictureUrls;

    public RouteDetailsView(Long id, String name, String description, String authorName,
                            String videoId, String difficulty, List<String> pictureUrls) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authorName = authorName;
        this.videoId = videoId;
        this.difficulty = difficulty;
        this.pictureUrls = pictureUrls;
    }

    public RouteDetailsView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }
}
