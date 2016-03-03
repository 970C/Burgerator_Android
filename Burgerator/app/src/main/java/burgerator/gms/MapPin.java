package burgerator.gms;

/**
 * Created by alec on 2/23/16.
 */
public class MapPin {

    private String name;
    private double lat;
    private double lon;

    public MapPin(){}
    public MapPin(String _name, double _lat, double _lon){
        name = _name;
        lat = _lat;
        lon = _lon;
    }

    public String getName(){return name;}

    public double getLat(){return lat;}

    public double getLon(){return lon;}
}
