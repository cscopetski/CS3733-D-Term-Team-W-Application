package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.InvalidUnit;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.StatusError;
import edu.wpi.cs3733.d22.teamW.wDB.enums.MedicineType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Units;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedRequest extends Request {
  MedicineType medicineType;
  Double quantity;
  Units unit;

  public MedRequest(
      Integer requestID,
      String medicine,
      Double quantity,
      Units unit,
      String nodeID,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws NoMedicine {
    this.requestID = requestID;
    this.medicineType = MedicineType.getMedicine(medicine);
    this.quantity = quantity;
    this.unit = unit;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public MedRequest(
      Integer requestID,
      MedicineType medicineType,
      Double quantity,
      Units unit,
      String nodeID,
      Integer bedNumber,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp) {
    this.requestID = requestID;
    this.medicineType = medicineType;
    this.quantity = quantity;
    this.unit = unit;
    this.nodeID = nodeID;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public MedRequest(ArrayList<String> fields) throws NoMedicine, StatusError, InvalidUnit {
    this.requestID = Integer.parseInt(fields.get(0));
    this.medicineType = MedicineType.getMedicine(fields.get(1));
    this.quantity = Double.parseDouble(fields.get(2));
    this.unit = Units.getUnitFromAbb(fields.get(3));
    this.nodeID = fields.get(4);
    this.employeeID = Integer.parseInt(fields.get(5));
    this.emergency = Integer.parseInt(fields.get(6));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(7)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(8));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(9));
  }

  public MedRequest(Integer index, ArrayList<String> fields)
      throws NoMedicine, StatusError, InvalidUnit {
    this.requestID = index;
    this.medicineType = MedicineType.getMedicine(fields.get(0));
    this.quantity = Double.parseDouble(fields.get(1));
    this.unit = Units.getUnitFromAbb(fields.get(2));
    this.nodeID = fields.get(3);
    this.employeeID = Integer.parseInt(fields.get(4));
    this.emergency = Integer.parseInt(fields.get(5));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(6)));
    this.createdTimestamp = new Timestamp(System.currentTimeMillis());
    this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%.2f,%s,%s,%d,%d,%d,%d,%s,%s",
        requestID,
        medicineType.getString(),
        quantity,
        unit.getUnits(),
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
        "%d, '%s', %f, '%s', '%s', %d, %d, %d, '%s', '%s'",
        requestID,
        medicineType.getString(),
        quantity,
        unit.getUnits(),
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
