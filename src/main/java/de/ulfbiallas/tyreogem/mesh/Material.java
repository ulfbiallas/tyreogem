package de.ulfbiallas.tyreogem.mesh;

import java.io.File;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Material {

    private String name;

    private Vec3d Ka;         // ambient reflectivity
    private File map_Ka;      // ambient texture

    private Vec3d Kd;         // diffuse reflectivity
    private File map_Kd;      // diffuse texture

    private Vec3d Ks;         // specular reflectivity
    private double shininess; // sharpness of the reflections

    private File map_d;       // alpha texture

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vec3d getKa() {
        return Ka;
    }

    public void setKa(Vec3d ka) {
        Ka = ka;
    }

    public File getMap_Ka() {
        return map_Ka;
    }

    public void setMap_Ka(File map_Ka) {
        this.map_Ka = map_Ka;
    }

    public Vec3d getKd() {
        return Kd;
    }

    public void setKd(Vec3d kd) {
        Kd = kd;
    }

    public File getMap_Kd() {
        return map_Kd;
    }

    public void setMap_Kd(File map_Kd) {
        this.map_Kd = map_Kd;
    }

    public Vec3d getKs() {
        return Ks;
    }

    public void setKs(Vec3d ks) {
        Ks = ks;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    public File getMap_d() {
        return map_d;
    }

    public void setMap_d(File map_d) {
        this.map_d = map_d;
    }

    public boolean isTextured() {
        return map_Ka != null || map_Kd != null || map_d != null;
    }

    public Material clone() {
        final Material material = new Material();
        material.setName(name);
        material.setKa(Ka);
        material.setKd(Kd);
        material.setKs(Ks);
        material.setMap_Ka(map_Ka);
        material.setMap_Kd(map_Kd);
        material.setMap_d(map_d);
        material.setShininess(shininess);
        return material;
    }
}
