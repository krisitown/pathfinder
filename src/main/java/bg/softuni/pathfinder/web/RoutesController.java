package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.views.RouteDetailView;
import bg.softuni.pathfinder.model.views.RouteIndexView;
import bg.softuni.pathfinder.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/routes")
public class RoutesController {
    private RouteService routeService;

    public RoutesController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String getAllRoutes(Model model) {
        List<RouteIndexView> routes = routeService.getAllRoutes();
        model.addAttribute("routes", routes);

        return "routes";
    }

    @GetMapping("/details/{id}")
    public String getRouteDetails(@PathVariable("id") Long id, Model model) {
        RouteDetailView route = routeService.getRouteDetails(id);
        model.addAttribute("route", route);

        return "route-details";
    }
}
