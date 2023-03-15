package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Picture;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class RouteService {
    private RouteRepository routeRepository;
    private ImageCloudService imageCloudService;

    @Autowired
    public RouteService(RouteRepository routeRepository, ImageCloudService imageCloudService) {
        this.routeRepository = routeRepository;
        this.imageCloudService = imageCloudService;
    }

    public List<Route> getMostCommented() {
        return routeRepository.findMostCommented();
    }
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unable to find route!"));
    }

    public Route createRoute(Route route, MultipartFile imageFile) {
        String pictureUrl = imageCloudService.saveImage(imageFile);

        Picture picture = new Picture();
        picture.setRoute(route);
        picture.setAuthor(route.getAuthor());
        picture.setTitle(imageFile.getOriginalFilename());
        picture.setUrl(pictureUrl);

        route.setPictures(Collections.singleton(picture));

        routeRepository.save(route);

        return route;
    }
}
