package edu.wpi.cs3733.d22.teamW.wDB.Managers;

import edu.wpi.cs3733.d22.teamW.wDB.DAO.MealRequestDao;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.entity.MealRequest;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestStatus;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MealRequestManager implements RequestManager {

  private static MealRequestManager mealRequestManager = new MealRequestManager();
  private MealRequestDao mealRequestDao;

  private MealRequestManager() {}

  public static MealRequestManager getMealRequestManager() {
    return mealRequestManager;
  }

  public void setMealRequestDao(MealRequestDao mealRequestDao) {
    this.mealRequestDao = mealRequestDao;
  }

  @Override
  public void start(Integer requestID) throws Exception {
    MealRequest request =
        (MealRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MealDelivery);
    if (request.getStatus() == RequestStatus.InQueue) {
      request.setStatus(RequestStatus.InProgress);
      this.mealRequestDao.changeMealServiceRequest(request);
    }
  }

  @Override
  public void complete(Integer requestID) throws Exception {
    MealRequest request =
        (MealRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MealDelivery);
    if (request.getStatus() == RequestStatus.InProgress) {
      request.setStatus(RequestStatus.Completed);
      this.mealRequestDao.changeMealServiceRequest(request);
    }
  }

  @Override
  public void cancel(Integer requestID) throws Exception {
    MealRequest request =
        (MealRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MealDelivery);
    if (request.getStatus() == RequestStatus.InProgress
        || request.getStatus() == RequestStatus.InQueue) {
      request.setStatus(RequestStatus.Cancelled);
      this.mealRequestDao.changeMealServiceRequest(request);
    }
  }

  @Override
  public void reQueue(Integer requestID) throws Exception {
    MealRequest request =
        (MealRequest)
            RequestFacade.getRequestFacade().findRequest(requestID, RequestType.MealDelivery);
    if (request.getStatus() == RequestStatus.Cancelled) {
      request.setStatus(RequestStatus.InQueue);
      this.mealRequestDao.changeMealServiceRequest(request);
    }
  }

  @Override
  public Request getRequest(Integer ID) throws Exception {
    return this.mealRequestDao.getMealRequest(ID);
  }

  @Override
  public ArrayList<Request> getAllRequests() throws Exception {
    return this.mealRequestDao.getAllMealServiceRequests();
  }

  @Override
  public void changeRequest(Request request) throws Exception {
    this.mealRequestDao.changeMealServiceRequest((MealRequest) request);
  }

  @Override
  public Request addNewRequest(Integer i, ArrayList<String> fields) throws Exception {
    MealRequest fr;
    fields.add(Integer.toString(RequestStatus.InQueue.getValue()));
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fields.add(new Timestamp(System.currentTimeMillis()).toString());
    fr = new MealRequest(i, fields);
    if (RequestFactory.getRequestFactory().getReqIDList().add(fr.getRequestID())) {
      this.mealRequestDao.addMealServiceRequest(fr);
    } else {
      fr = null;
    }
    return fr;
  }

  @Override
  public Request addExistingRequest(ArrayList<String> fields) throws Exception {
    MealRequest fr;
    fr = new MealRequest(fields);
    if (RequestFactory.getRequestFactory().getReqIDList().add(fr.getRequestID())) {
      this.mealRequestDao.addMealServiceRequest(fr);
    } else {
      fr = null;
    }
    return fr;
  }

  @Override
  public ArrayList<Request> getEmployeeRequests(Integer employeeID) {
    return this.mealRequestDao.getEmployeeRequests(employeeID);
  }

  @Override
  public void exportReqCSV(String filename) throws Exception {
    this.mealRequestDao.exportMealServiceReqCSV(filename);
  }

  @Override
  public void updateReqAtLocation(String nodeID) throws Exception {
    this.mealRequestDao.updateMealServiceRequestsAtLocation(nodeID);
  }

  @Override
  public void updateReqWithEmployee(Integer employeeID) throws Exception {
    this.mealRequestDao.updateMealServiceRequestsWithEmployee(employeeID);
  }
}
