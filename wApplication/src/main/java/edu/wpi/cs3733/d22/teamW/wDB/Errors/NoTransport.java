package edu.wpi.cs3733.d22.teamW.wDB.Errors;

public class NoTransport extends Exception {
    public NoTransport() {
        super("Transport type does not exist in our list of transport options");
    }
}
