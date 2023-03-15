package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.Picture;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.dto.RouteCreationDto;
import bg.softuni.pathfinder.model.enums.Level;
import bg.softuni.pathfinder.model.view.RouteDetailsView;
import bg.softuni.pathfinder.model.view.RouteIndexView;
import bg.softuni.pathfinder.service.AuthService;
import bg.softuni.pathfinder.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/routes")
public class RouteController {
    private final RouteService routeService;
    private final AuthService authService;

    @Autowired
    public RouteController(RouteService routeService, AuthService authService) {
        this.routeService = routeService;
        this.authService = authService;
    }

    @GetMapping
    public String getAllRoutes(Model model) {
        var routes = routeService.getAllRoutes()
                .stream().map(r -> new RouteIndexView(r.getId(),
                        r.getName(), findFirstPictureUrl(r.getPictures()),
                        r.getDescription()))
                .collect(Collectors.toList());
        model.addAttribute("routes", routes);

        return "routes";
    }

    @GetMapping("{id}")
    public String getRoute(@PathVariable("id") Long id, Model model) {
        Route route = routeService.getRouteById(id);
        RouteDetailsView routeDetailsView = new RouteDetailsView(
                route.getId(),
                route.getName(),
                route.getDescription(),
                route.getAuthor().getFullName(),
                route.getVideoUrl(),
                route.getLevel().name(),
                route.getPictures().stream().map(Picture::getUrl).collect(Collectors.toList())
        );

        model.addAttribute("route", routeDetailsView);

        return "route-details";
    }

    @GetMapping("/new")
    public String createNewRouteForm() {
        return "add-route";
    }

    @PostMapping("/new")
    public String createNewRoute(Principal principal, @Valid RouteCreationDto routeCreationDto) {
        //create Route object from Dto
        //upload image to cloudinary
        //save url of image
        //redirect user

        Route route = new Route();
        route.setAuthor(authService.getUserByUsername(principal.getName()));
        route.setName(routeCreationDto.getName());
        route.setDescription(routeCreationDto.getDescription());
        route.setVideoUrl(routeCreationDto.getVideoId());
        route.setLevel(getLevel(routeCreationDto.getLevel()));

        routeService.createRoute(route, routeCreationDto.getImage());

        return "redirect:/routes/" + route.getId();
    }

    private String findFirstPictureUrl(Set<Picture> pictures) {
        return pictures.stream().findFirst().map(p -> p.getUrl()).orElse("N/A");
    }

    private Level getLevel(String key) {
        if(key == null) {
            return Level.BEGINNER;
        }

        var level = Level.levelMap.get(key);
        if(level == null) {
            return Level.BEGINNER;
        }
        return level;
    }
}
