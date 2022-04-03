package edu.wpi.cs3733.d22.teamW.wDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedEquipRequestController implements RequestController {

  private MedEquipRequestDaoImpl merdi;
  private MedEquipDaoImpl medi;

  public MedEquipRequestController(MedEquipRequestDaoImpl merdi, MedEquipDaoImpl medi) {
    this.merdi = merdi;
    this.medi = medi;
  }

  @Override
  public String checkStart(Request request) throws SQLException {
    MedEquipRequest mER = (MedEquipRequest) request;
    return medi.checkTypeAvailable(mER.getItemType());
  }

  @Override
  //Should take in itemID to give to next request that needs item of that type (if there is one)
  public void checkFinish() {}

  @Override
  //Get the next request and return it
  public Request getNext() {
    return null;
  }

  @Override
  public Request getRequest() {
    return null;
  }

  @Override
  public Request addRequest(Integer num, ArrayList<String> fields) throws SQLException {
    // Set status to in queue if it is not already included (from CSVs)
    if(fields.size()==4){
      fields.add("0");
    }

    MedEquipRequest mER = new MedEquipRequest(num, fields);
    String itemID = checkStart(mER);
    if (itemID != null) {
      System.out.println("Starting Request " + mER.getRequestID());
      mER.start(itemID);
    }
    merdi.addMedEquipRequest(mER);
    return mER;
  }
}
