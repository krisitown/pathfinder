package bg.softuni.pathfinder.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class RouteCreationDto {
    private String name;
    private String description;
    private MultipartFile image;
    private String level;
    private String videoId;

    public RouteCreationDto() {
    }

    public RouteCreationDto(String name, String description, MultipartFile image, String level, String videoId) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.level = level;
        this.videoId = videoId;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
