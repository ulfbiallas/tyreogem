package de.ulfbiallas.tyreogem.core.planar;

import java.util.List;

public class MultiIntersection<T extends Intersection> {

    private final List<T> intersections;

    public MultiIntersection(List<T> intersections) {
        this.intersections = intersections;
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
