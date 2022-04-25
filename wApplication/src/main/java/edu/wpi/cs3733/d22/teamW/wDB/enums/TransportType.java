package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.D22.teamZ.api.entity.TransportMethod;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoFlower;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoTransport;

import java.util.HashMap;
import java.util.Map;

public enum TransportType {

    Helicopter("Helicopter"),
    Ambulance("Ambulance"),
    PatientCar("Patient Car"),
    Plane("Plane");

    public final String string;

    private static Map map = new HashMap<>();

    private TransportType(String string) {
        this.string = string;
    }

    static {
        for (TransportType type : TransportType.values()) {
            System.out.println(type.string);
            map.put(type.string, type);
        }
    }

    public String getString() {
        return this.string;
    }

    public static TransportType getTransportType(String transportType) throws NoTransport {
        TransportType type = null;
        type = (TransportType) map.get(transportType);
        if (type == null) {
            throw new NoTransport();
        }
        return type;
    }

    public static TransportType toTransportType(TransportMethod t){
        if(t.toString().equals("HELICOPTER")){
            return TransportType.Helicopter;
        }else if(t.toString().equals("AMBULANCE")){
            return TransportType.Ambulance;
        }else if(t.toString().equals("PATIENTCAR")){
            return TransportType.PatientCar;
        }else{
            return TransportType.Plane;
        }
    }
}
