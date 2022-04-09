package edu.wpi.cs3733.d22.teamW.wDB;

public enum RequestStatus {
    InQueue(0),
    InProgress(1),
    Completed(2),
    Cancelled(3);

    private final int value;

    private RequestStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
