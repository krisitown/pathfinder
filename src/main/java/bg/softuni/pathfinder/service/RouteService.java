package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Picture;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.views.RouteDetailView;
import bg.softuni.pathfinder.model.views.RouteIndexView;
import bg.softuni.pathfinder.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getMostCommented() {
        return routeRepository.findMostCommented();
    }

    public List<RouteIndexView> getAllRoutes() {
        return routeRepository.findAll().stream()
            .map(route -> new RouteIndexView(
                    route.getId(),
                    route.getName(),
                    route.getDescription(),
                    route.getPictures().stream().findFirst().orElse(new Picture()).getUrl()
            )).collect(Collectors.toList());
    }

    public RouteDetailView getRouteDetails(Long id) {
        return routeRepository.findById(id)
                .map(route -> new RouteDetailView(
                        route.getGpxCoordinates(),
                        route.getLevel().name(),
                        route.getName(),
                        route.getDescription(),
                        route.getAuthor().getFullName(),
                        route.getVideoUrl(),
                        route.getPictures().stream().map(Picture::getUrl).collect(Collectors.toList())
                )).orElseThrow(RouteNotFoundException::new);
    }
}