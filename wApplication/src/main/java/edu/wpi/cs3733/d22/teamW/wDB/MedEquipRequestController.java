package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestController implements RequestController{

    private MedEquipRequestDaoImpl merdi;
    private MedEquipDaoImpl medi;


    @Override
    public String checkStart(Request request) {
        MedEquipRequest mER = (MedEquipRequest) request;
        return medi.checkTypeAvailable(mER.getItemType());
    }

    @Override
    public void checkFinish() {

    }

    @Override
    public Request getNext() {
        return null;
    }

    @Override
    public Request getRequest() {
        return null;
    }

    @Override
    public Request addRequest(Integer num, ArrayList<String> fields) throws SQLException {
        MedEquipRequest mER = new MedEquipRequest(num, fields);
        String itemID = checkStart(mER);
        if(itemID != null){
            mER.start(itemID);
            merdi.changeMedEquipRequest(mER);
        }
        merdi.addMedEquipRequest(mER);
        return mER;
    }


}
