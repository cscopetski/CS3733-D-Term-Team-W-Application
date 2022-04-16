package edu.wpi.cs3733.d22.teamW.wDB.enums;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.InValidRequestType;
import java.util.HashMap;
import java.util.Map;

public enum RequestType {
  MedicalEquipmentRequest(0, "Medical Equipment Request"),
  LabServiceRequest(1, "Lab Service Request"),
  MedicineDelivery(2, "Medicine Delivery"),
  LanguageInterpreter(3, "Language Interpreter"),
  SanitationService(4, "Sanitation Service"),
  LaundryService(5, "Laundry Service"),
  MealDelivery(6, "Meal Delivery"),
  GiftDelivery(7, "Gift Delivery"),
  ReligiousService(7, "Religious Service"),
  SecurityService(8, "Security Service"),
  MaintenanceRequest(9, "Maintenance Request"),
  ComputerServiceRequest(10, "Computer Service Request"),
  AudioVisualRequest(11, "Audio Visual Request"),
  CleaningRequest(12, "Cleaning Request"),
  FlowerRequest(13, "Flower Request");
  private final Integer value;
  private final String string;

  private static Map map = new HashMap<Integer, RequestType>();
  private static Map map2 = new HashMap<String, RequestType>();

  static {
    for (RequestType type : RequestType.values()) {
      map.put(type.value, type);
      map2.put(type.string, type);
    }
  }

  private RequestType(Integer value, String string) {
    this.value = value;
    this.string = string;
  }

  public int getValue() {
    return this.value;
  }

  public String getString() {
    return this.string;
  }

  public static RequestType getRequestType(Integer type) throws InValidRequestType {
    RequestType output = (RequestType) map.get(type);
    if (output == null) {
      throw new InValidRequestType();
    }
    return output;
  }

  public static RequestType getRequestType(String type) throws InValidRequestType {

    type = type.trim();
    RequestType output = (RequestType) map2.get(type);
    if (output == null) {
      throw new InValidRequestType();
    }
    return output;
  }
}
