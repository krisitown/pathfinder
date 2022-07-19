package bg.softuni.pathfinder.model.views;

import java.util.List;
import java.util.Set;

public class RouteDetailView {

    private Long routeId;
    private String gpxCoordinates;

    private String level;

    private String name;

    private String description;

    private String authorName;

    private String videoUrl;

    private List<String> pictures;

    public RouteDetailView(Long routeId, String gpxCoordinates, String level, String name, String description,
                           String authorName, String videoUrl, List<String> pictures) {
        this.routeId = routeId;
        this.gpxCoordinates = gpxCoordinates;
        this.level = level;
        this.name = name;
        this.description = description;
        this.authorName = authorName;
        this.videoUrl = videoUrl;
        this.pictures = pictures;
    }

    public String getGpxCoordinates() {
        return gpxCoordinates;
    }

    public void setGpxCoordinates(String gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
