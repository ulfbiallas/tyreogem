package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.File;

import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Material;

// for the specification see:
// http://paulbourke.net/dataformats/mtl/
// https://people.sc.fsu.edu/~jburkardt/data/mtl/mtl.html
// http://cgkit.sourceforge.net/doc2/objmaterial.html
public class ObjMaterial {

    public static final String NO_MATERIAL = "__no_material__";

    private String directory; // directory of textures
    private String name;      // name of the material

    private int illum;        // illumination model
    private Vec3d Ka;         // ambient reflectivity (Default 0.2 0.2 0.2)
    private Vec3d Kd;         // diffuse reflectivity (Default 0.8 0.8 0.8)
    private Vec3d Ks;         // specular reflectivity
    private Vec3d Ke;         // emission
    private Vec3d Tf;         // transmission filter
    private double Tr;        // transparency (Tr = 1.0 - d) (Default: 1.0 = not transparent)
    private double d;         // dissolve (d = 1.0 - Tr) (Default: 0.0 = not transparent)
    private double Ns;        // specular exponent (Default 0.0)
    private double Ni;        // optical density
    private double sharpness; // sharpness of the reflections
    private String map_Ka;    // ambient texture
    private String map_Kd;    // diffuse texture
    private String map_d;     // dissolve texture
    private String map_bump;  // bump map texture

    public ObjMaterial() {
    }

    public ObjMaterial(Material material) {
        this.name = material.getName();
        this.Ka = material.getKa();
        this.Kd = material.getKd();
        this.Ks = material.getKs();
        this.sharpness = material.getShininess();
        this.map_Ka = material.getMap_Ka() != null ? material.getMap_Ka().getAbsolutePath() : null;
        this.map_Kd = material.getMap_Kd() != null ? material.getMap_Kd().getAbsolutePath() : null;
        this.map_d = material.getMap_d() != null ? material.getMap_d().getAbsolutePath() : null;
    }

    public String getDirectory() {
        return directory;
    }
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIllum() {
        return illum;
    }
    public void setIllum(int illum) {
        this.illum = illum;
    }
    public Vec3d getKa() {
        return Ka;
    }
    public void setKa(Vec3d ka) {
        Ka = ka;
    }
    public Vec3d getKd() {
        return Kd;
    }
    public void setKd(Vec3d kd) {
        Kd = kd;
    }
    public Vec3d getKs() {
        return Ks;
    }
    public void setKs(Vec3d ks) {
        Ks = ks;
    }
    public Vec3d getKe() {
        return Ke;
    }
    public void setKe(Vec3d ke) {
        Ke = ke;
    }
    public Vec3d getTf() {
        return Tf;
    }
    public void setTf(Vec3d tf) {
        Tf = tf;
    }
    public double getTr() {
        return Tr;
    }
    public void setTr(double tr) {
        Tr = tr;
    }
    public double getD() {
        return d;
    }
    public void setD(double d) {
        this.d = d;
    }
    public double getNs() {
        return Ns;
    }
    public void setNs(double ns) {
        Ns = ns;
    }
    public double getNi() {
        return Ni;
    }
    public void setNi(double ni) {
        Ni = ni;
    }
    public double getSharpness() {
        return sharpness;
    }
    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }
    public String getMap_Ka() {
        return map_Ka;
    }
    public void setMap_Ka(String map_Ka) {
        this.map_Ka = map_Ka;
    }
    public String getMap_Kd() {
        return map_Kd;
    }
    public void setMap_Kd(String map_Kd) {
        this.map_Kd = map_Kd;
    }
    public String getMap_d() {
        return map_d;
    }
    public void setMap_d(String map_d) {
        this.map_d = map_d;
    }
    public String getMap_bump() {
        return map_bump;
    }
    public void setMap_bump(String map_bump) {
        this.map_bump = map_bump;
    }

    @Override
    public String toString() {
        return "ObjMaterial [name=" + name + ", illum=" + illum + ", Ka=" + Ka + ", Kd=" + Kd + ", Ks=" + Ks + ", Ke="
                + Ke + ", Tf=" + Tf + ", Tr=" + Tr + ", d=" + d + ", Ns=" + Ns + ", Ni=" + Ni + ", sharpness="
                + sharpness + ", map_Ka=" + map_Ka + ", map_Kd=" + map_Kd + ", map_d=" + map_d + ", map_bump="
                + map_bump + "]";
    }

    public Material toMaterial() {
        final Material material = new Material();
        material.setName(name);
        material.setKa(Ka);
        material.setKd(Kd);
        material.setKs(Ks);
        material.setShininess(sharpness);
        material.setMap_Ka(map_Ka != null ? new File(directory, map_Ka) : null);
        material.setMap_Kd(map_Kd != null ? new File(directory, map_Kd) : null);
        material.setMap_d(map_d != null ? new File(directory, map_d) : null);
        return material;
    }

}
