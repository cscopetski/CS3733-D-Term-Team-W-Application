package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Medicine;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedRequest extends Request {
  Medicine medicine;

  public MedRequest(
      Integer requestID,
      String medicine,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws NoMedicine {
    this.requestID = requestID;
    this.medicine = Medicine.getMedicine(medicine);
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public MedRequest(
      Integer requestID,
      Medicine medicine,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws NoMedicine {
    this.requestID = requestID;
    this.medicine = medicine;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public MedRequest(ArrayList<String> fields) throws NoMedicine {
    this.requestID = Integer.parseInt(fields.get(0));
    this.medicine = Medicine.getMedicine(fields.get(1));
    this.nodeID = fields.get(2);
    this.employeeID = Integer.parseInt(fields.get(3));
    this.emergency = Integer.parseInt(fields.get(4));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(5)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(6));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(7));
  }

  public MedRequest(Integer index, ArrayList<String> fields) throws NoMedicine {
    this.requestID = index;
    this.medicine = Medicine.getMedicine(fields.get(0));
    this.nodeID = fields.get(1);
    this.employeeID = Integer.parseInt(fields.get(2));
    this.emergency = Integer.parseInt(fields.get(3));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(4)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(5));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(6));
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%d,%d,%d,%s,%s",
        requestID,
        medicine,
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s', '%s', %d, %d, %d, '%s', '%s'",
        requestID,
        medicine,
        nodeID,
        employeeID,
        emergency,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public RequestType getRequestType() {
    return RequestType.MedicineDelivery;
  }
}
