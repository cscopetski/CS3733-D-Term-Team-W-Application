package edu.wpi.cs3733.d22.teamW.wDB.entity;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.NoMedicine;
import edu.wpi.cs3733.d22.teamW.wDB.enums.Medicine;
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
  Medicine medicine;
  Double quantity;
  Units unit;
  String patientFirst;
  String patientLast;
  Integer bedNumber;

  public MedRequest(
      Integer requestID,
      String patientLast,
      String patientFirst,
      String medicine,
      Double quantity,
      Units unit,
      String nodeID,
      Integer bedNumber,
      Integer employeeID,
      Integer emergency,
      RequestStatus status,
      Timestamp createdTimestamp,
      Timestamp updatedTimestamp)
      throws NoMedicine {
    this.requestID = requestID;
    this.patientLast = patientLast;
    this.patientFirst = patientFirst;
    this.medicine = Medicine.getMedicine(medicine);
    this.quantity = quantity;
    this.unit = unit;
    this.nodeID = nodeID;
    this.bedNumber = bedNumber;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public MedRequest(
      Integer requestID,
      String patientLast,
      String patientFirst,
      Medicine medicine,
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
    this.patientLast = patientLast;
    this.patientFirst = patientFirst;
    this.medicine = medicine;
    this.quantity = quantity;
    this.unit = unit;
    this.nodeID = nodeID;
    this.bedNumber = bedNumber;
    this.employeeID = employeeID;
    this.emergency = emergency;
    this.status = status;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public MedRequest(ArrayList<String> fields) throws NoMedicine {
    this.requestID = Integer.parseInt(fields.get(0));
    this.patientLast = fields.get(1);
    this.patientFirst = fields.get(2);
    this.medicine = Medicine.getMedicine(fields.get(3));
    this.quantity = Double.parseDouble(fields.get(4));
    this.unit = Units.getUnitFromAbb(fields.get(5));
    this.nodeID = fields.get(6);
    this.bedNumber = Integer.parseInt(fields.get(7));
    this.employeeID = Integer.parseInt(fields.get(8));
    this.emergency = Integer.parseInt(fields.get(9));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(10)));
    this.createdTimestamp = Timestamp.valueOf(fields.get(11));
    this.updatedTimestamp = Timestamp.valueOf(fields.get(12));
  }

  public MedRequest(Integer index, ArrayList<String> fields) throws NoMedicine {
    this.requestID = index;
    this.patientLast = fields.get(0);
    this.patientFirst = fields.get(1);
    this.medicine = Medicine.getMedicine(fields.get(0));
    this.quantity = Double.parseDouble(fields.get(1));
    this.unit = Units.getUnitFromAbb(fields.get(2));
    this.nodeID = fields.get(3);
    this.bedNumber = Integer.parseInt(fields.get(7));
    this.employeeID = Integer.parseInt(fields.get(4));
    this.emergency = Integer.parseInt(fields.get(5));
    this.status = RequestStatus.getRequestStatus(Integer.parseInt(fields.get(6)));
    this.createdTimestamp = new Timestamp(System.currentTimeMillis());
    this.updatedTimestamp = new Timestamp(System.currentTimeMillis());
  }

  @Override
  public String toCSVString() {
    return String.format(
        "%d,%s,%s,%s,%.2f,%s,%s,%d,%d,%d,%d,%s,%s",
        requestID,
        patientLast,
        patientFirst,
        medicine.getString(),
        quantity,
        unit.getUnits(),
        nodeID,
        bedNumber,
        employeeID,
        emergency,
        status.getValue(),
        createdTimestamp.toString(),
        updatedTimestamp.toString());
  }

  @Override
  public String toValuesString() {
    return String.format(
        "%d, '%s', '%s', '%s', %f, '%s', '%s', %d, %d, %d, %d, '%s', '%s'",
        requestID,
        patientLast,
        patientFirst,
        medicine.getString(),
        quantity,
        unit.getUnits(),
        nodeID,
        bedNumber,
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
