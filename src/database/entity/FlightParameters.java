package database.entity;

/**
 * This class is entity for Virtual Black Box
 * It contains main flight parameters such as
 * Location:
 * @param paramLat is a Latitude
 * @param paramLon is a Longitude
 * Velocities:
 * @param paramVg  is a Groundspeed
 * @param paramVt  is a Truespeed
 * @param paramVi  is an Indicatedspeed
 * Rotation:
 * @param paramR   is a Roll angle (deg)
 * @param paramP   is a Pitch angle (deg)
 * @param paramH   is a Heading angle (deg)
 * Altitudes:
 * @param paramA   is an Altitude
 *
 * This class interacts with self-titled table of database FlightParameters.hbm.xml
 */
public class FlightParameters {

    private Long id;
    private Double paramT;
    private Double paramA;
    private Double paramVg;
    private Double paramVt;
    private Double paramVi;
    private Double paramLon;
    private Double paramLat;
    private Double paramH;
    private Double paramP;
    private Double paramR;

    public FlightParameters(){}

    public FlightParameters(Double paramT, Double paramA, Double paramVg,
                            Double paramVt, Double paramVi, Double paramLon,
                            Double paramLat, Double paramH, Double paramP, Double paramR) {
        this.paramT = paramT;
        this.paramA = paramA;
        this.paramVg = paramVg;
        this.paramVt = paramVt;
        this.paramVi = paramVi;
        this.paramLon = paramLon;
        this.paramLat = paramLat;
        this.paramH = paramH;
        this.paramP = paramP;
        this.paramR = paramR;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getParamT() {
        return paramT;
    }

    public void setParamT(Double paramT) {
        this.paramT = paramT;
    }

    public Double getParamA() {
        return paramA;
    }

    public void setParamA(Double paramA) {
        this.paramA = paramA;
    }

    public Double getParamVg() {
        return paramVg;
    }

    public void setParamVg(Double paramVg) {
        this.paramVg = paramVg;
    }

    public Double getParamVt() {
        return paramVt;
    }

    public void setParamVt(Double paramVt) {
        this.paramVt = paramVt;
    }

    public Double getParamVi() {
        return paramVi;
    }

    public void setParamVi(Double paramVi) {
        this.paramVi = paramVi;
    }

    public Double getParamLon() {
        return paramLon;
    }

    public void setParamLon(Double paramLon) {
        this.paramLon = paramLon;
    }

    public Double getParamLat() {
        return paramLat;
    }

    public void setParamLat(Double paramLat) {
        this.paramLat = paramLat;
    }

    public Double getParamH() {
        return paramH;
    }

    public void setParamH(Double paramH) {
        this.paramH = paramH;
    }

    public Double getParamP() {
        return paramP;
    }

    public void setParamP(Double paramP) {
        this.paramP = paramP;
    }

    public Double getParamR() {
        return paramR;
    }

    public void setParamR(Double paramR) {
        this.paramR = paramR;
    }

    @Override
    public String toString() {
        return "FlightParameters{" +
                "id=" + id +
                ", paramT=" + paramT +
                ", paramA=" + paramA +
                ", paramVg=" + paramVg +
                ", paramVt=" + paramVt +
                ", paramVi=" + paramVi +
                ", paramLon=" + paramLon +
                ", paramLat=" + paramLat +
                ", paramH=" + paramH +
                ", paramP=" + paramP +
                ", paramR=" + paramR +
                '}';
    }
}
