package model;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.List;
import java.util.Map;

public class RouteFinder {
    public static List<Location> findShortestPath(Location start, Location end, Map<Location, List<Route>> routes) {
        Graph<Location, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        routes.forEach((loc, routeList) -> {
            routeList.forEach(route -> {
                graph.addVertex(route.getFrom());
                graph.addVertex(route.getTo());
                graph.addEdge(route.getFrom(), route.getTo());
            });
        });

        DijkstraShortestPath<Location, DefaultEdge> dijkstra = new DijkstraShortestPath<>(graph);

        if (dijkstra.getPath(start, end) == null) {
            throw new IllegalArgumentException("No path exists between " + start.getName() + " and " + end.getName());
        }
        return dijkstra.getPath(start, end).getVertexList();
    }

}
