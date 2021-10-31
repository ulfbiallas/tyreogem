package de.ulfbiallas.tyreogem.core.spatial;

import java.util.List;
import java.util.stream.Collectors;

public class MultiIntersection<T extends Intersection> {

    private final List<T> intersections;

    public MultiIntersection(List<T> intersections) {
        this.intersections = intersections.stream().filter(i -> i.isIntersecting()).collect(Collectors.toList());
    }

    public List<T> getIntersections() {
        return this.intersections;
    }

    public boolean isIntersecting() {
        return !this.intersections.isEmpty();
    }

    public int getNumberOfIntersections() {
        return this.intersections.size();
    }
}
