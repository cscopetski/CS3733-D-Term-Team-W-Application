package edu.wpi.cs3733.d22.teamW.wDB;

public class MedRequest {
    Integer requestID;
    String medicine;
    String nodeID;
    String employeeName;
    Integer emergency;
    Integer status;

    public MedRequest(Integer requestID, String medicine, String nodeID, String employeeName, Integer emergency, Integer status){
        this.requestID = requestID;
        this.medicine = medicine;
        this.nodeID = nodeID;
        this.employeeName = employeeName;
        this.emergency = emergency;
        this.status = status;
    }

}
