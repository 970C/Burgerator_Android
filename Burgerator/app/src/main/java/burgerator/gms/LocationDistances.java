package burgerator.gms;

import java.util.ArrayList;

/**
 * Created by alec on 2/25/16.*/


public class LocationDistances {

    private double distance;
    private double coordinates;

    private double east;
    private double west;
    private double north;
    private double south;
    private ArrayList<Double> a = new ArrayList<Double>();
    private ArrayList<Double> b = new ArrayList<Double>();

    //LatLngBounds bounds = new LatLngBounds();

    LocationMarker locMark = new LocationMarker();

    public LocationDistances(){}
    public LocationDistances(ArrayList<Double> Vertex) {
        a = Vertex;
    }


    public double[] farthestLatSort(int latOrLon) {
        for (int i = 0; i < a.size(); i++) {
            for (int j = i+1; j < a.size(); j++) {
                if (a.get(i) > a.get(j)) {

                    b.add(a.get(i));
                    a.set(i, a.get(j));
                    a.set(j, b.get(0));
                    b.clear();
                System.out.println(a.get(i));
                }
            }
        }

        if (latOrLon == 0) {
            east = a.get(0);
            west = a.get(a.size()-1);
            System.out.println("East and West: " + east + ", "+ west);
            double[] EW = {east, west};
            return EW;

        } else {
            north = a.get(0);
            south = a.get(a.size()-1);
            System.out.println("North and South: " + north + ", "+ south);
            double[] NS = {north, south};
            return NS;
        }

    }

    public double northGet(){return north;}
    public double southGet(){return south;}
    public double eastGet(){return east;}
    public double westGet(){return west;}


    public double distanceBetweenTwoPoints(double xpoint1, double xpoint2, double ypoint1, double ypoint2){

        double lat1;
        double lat2;

        int R = 6371000; // metres

        lat1 = Math.toRadians(xpoint1);
        lat2 = Math.toRadians(xpoint2);

        double φ1 = Math.toRadians(xpoint1 - xpoint2);
        double φ2 = Math.toRadians(ypoint1 - ypoint2);

        double a = Math.sin(φ1/2) * Math.sin(φ1/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(φ2/2) * Math.sin(φ2/2);


        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;

        //miles conversion
        double m = d * 0.00062137;

        return m;
    }



    public double distanceOfView(double east, double west, double north, double south){

        double lat1;
        double lat2;

        int R = 6371000; // metres

        lat1 = Math.toRadians(east);
        lat2 = Math.toRadians(west);
        System.out.println("East: " + east + "West: "+ west);
        System.out.println("North: " + north + "South: "+ south);

        double φ1 = Math.toRadians(west - east);
        double φ2 = Math.toRadians(north - south);


        double a = Math.sin(φ1/2) * Math.sin(φ1/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(2/2) * Math.sin(φ2/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;


        return d;

    }


    public int setZoom(double distance){

       // String disChar = ""+distance;

        if(distance < 0.1493){
            return 20;
        }else if(distance < 2256.994440){
            return 19;
        }else if(distance < 4513.988880){
            return 18;
        }else if(distance < 9027.977761){
            return 17;
        }else if(distance < 18055.955520){
            return 16;
        }else if(distance < 36111.911040){
            return 15;
        }else if(distance < 72223.822090){
            return 14;
        }else if(distance < 144447.644200){
            return 13;


        }else if(distance < 19.1093){return 21;
        }else if(distance < 38.2185){return 20;
        }else if(distance < 76.4370){return 19;
        }else if(distance < 152.8741){return 18;
        }else if(distance < 305.7481){return 17;
        }else if(distance < 611.4962){return 16;
        }else if(distance < 1029.9849){return 15;
        }else if(distance < 2445.9849){return 14;
        }else if(distance < 4891.9698){return 13;
        }else if(distance < 19567.8792){return 12;
        }else if(distance < 39135.7585){return 11;
        }else if(distance < 78271.5170){return 10;
        }else if(distance < 150234.2345){return 9;
        }else if(distance < 304256.3236){return 8;}

        return 1;

    }

}

