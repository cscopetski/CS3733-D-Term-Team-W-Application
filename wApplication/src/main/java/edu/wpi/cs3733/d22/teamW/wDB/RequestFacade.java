package edu.wpi.cs3733.d22.teamW.wDB;

public class RequestFacade {

    private static RequestFacade requestFacade = new RequestFacade();

    private RequestFacade(){

    }

    public RequestFacade getRequestFacade(){
        return this.requestFacade;
    }



}
