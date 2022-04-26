package edu.wpi.cs3733.d22.teamW.wDB.DAO;

import edu.wpi.cs3733.d22.teamW.wDB.Managers.*;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFactory;
import edu.wpi.cs3733.d22.teamW.wDB.enums.DBConnectionMode;
import java.sql.*;

public class DBController {

  private String dbName = "myDB";
  private Statement statement;
  private Connection connection;

  //  private LocationDao locationDao;
  //  private MedEquipDao medEquipDao;
  //  private MedEquipRequestDao medEquipRequestDao;
  //  private LabServiceRequestDao labServiceRequestDao;
  //  private EmployeeDao employeeDao;
  //  private MedRequestDao medRequestDao;

  //  private LocationManager locationManager;
  //  private MedEquipManager medEquipManager;
  //  private MedEquipRequestManager medEquipRequestManager;
  //  private LabServiceRequestManager labServiceRequestManager;
  //  private EmployeeManager employeeManager;
  //  private MedRequestManager medRequestManager;

  private static DBController dbController = new DBController();

  public static DBController getDBController() {
    return dbController;
  }

  private DBController() {
    try {
      startConnection();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void startConnection() throws SQLException, ClassNotFoundException {
    RequestFactory.getRequestFactory().resetTreeSet();
    EmployeeManager.getEmployeeManager().resetEmpIDSet();
    String connectionStringEmbedded = String.format("jdbc:derby:%s;create=true", this.dbName);
    String connectionStringServer =
        String.format("jdbc:derby://localhost:1527/%s;create=true", this.dbName);

    try {
      if (DBConnectionMode.INSTANCE.getConnectionType()) {
        this.connectEmbedded(connectionStringEmbedded);
      } else {
        this.connectServer(connectionStringServer);
      }

      // Create Daos (tables are dropped automatically when daos are created)
      // *ORDER MATTERS BECAUSE OF FOREIGN KEYS*
      ExternalTransportRequestDao externalTransportRequestDao = new ExternalTransportRequestDaoImpl(statement);
      InternalPatientTransportationRequestDao internalPatientTransportationRequestDao = new InternalPatientTransportationRequestDaoImpl(statement);
      HighScoreDao highScoreDao = new HighScoreDaoImpl(statement);
      UnreadMessageDao unreadMessageDao = new UnreadMessageDaoImpl(statement);
      EmployeeMessageDao employeeMessageDao = new EmployeeMessageDaoImpl(statement);
      ChatDao chatDao = new ChatDaoImpl(statement);
      LanguageRequestDao languageRequestDao = new LanguageRequestDaoImpl(statement);
      SecurityRequestDao securityRequestDao = new SecurityRequestDaoImpl(statement);
      MealRequestDao mealRequestDao = new MealRequestDaoImpl(statement);
      FlowerRequestDao flowerRequestDao = new FlowerRequestDaoImpl(statement);
      GiftDeliveryRequestDao giftDeliveryRequestDao = new GiftDeliveryRequestDaoImpl(statement);
      ComputerServiceRequestDao csrDao = new ComputerServiceRequestDaoImpl(statement);
      SanitationRequestDao sanitationRequestDao = new SanitationRequestDaoImpl(statement);
      MedRequestDao medRequestDao = new MedRequestDaoImpl(statement);
      CleaningRequestDao cleaningRequestDao = new CleaningRequestDaoImpl(statement);
      LabServiceRequestDao labServiceRequestDao = new LabServiceRequestDaoImpl(statement);
      MedEquipRequestDao medEquipRequestDao = new MedEquipRequestDaoImpl(statement);
      MedEquipDao medEquipDao = new MedEquipDaoImpl(statement);
      LanguageInterpreterDao languageInterpreterDao = new LanguageInterpreterDaoImpl(statement);
      EmployeeDao employeeDao = new EmployeeDaoSecureImpl(statement);
      LocationDao locationDao = new LocationDaoImpl(statement);
      LanguageDao languageDao = new LanguageDaoImpl(statement);

      // Assign Daos to Managers
      HighScoreManager.getHighScoreManager().setHighScoreDao(highScoreDao);
      EmployeeManager.getEmployeeManager().setEmployeeDao(employeeDao);
      LocationManager.getLocationManager().setLocationDao(locationDao);
      MedEquipManager.getMedEquipManager().setMedEquipDao(medEquipDao);
      MedEquipRequestManager.getMedEquipRequestManager().setMedEquipRequestDao(medEquipRequestDao);
      MedRequestManager.getMedRequestManager().setMedRequestDao(medRequestDao);
      LabServiceRequestManager.getLabServiceRequestManager()
          .setLabServiceRequestDao(labServiceRequestDao);
      CleaningRequestManager.getCleaningRequestManager().setCleaningRequestDao(cleaningRequestDao);
      FlowerRequestManager.getFlowerRequestManager().setFlowerRequestDao(flowerRequestDao);
      ComputerServiceRequestManager.getComputerServiceRequestManager()
          .setComputerServiceRequestDao(csrDao);
      SanitationRequestManager.getSanitationRequestManager()
          .setLabServiceRequestDao(sanitationRequestDao);
      LanguageManager.getLanguageManager().setLanguageDao(languageDao);
      LanguageInterpreterManager.getLanguageInterpreterManager()
          .setLanguageInterpreterDao(languageInterpreterDao);
      GiftDeliveryRequestManager.getGiftDeliveryRequestManager()
          .setGiftDeliveryRequestDao(giftDeliveryRequestDao);
      EmployeeMessageManager.getEmployeeMessageManager().setEmployeeMessageDao(employeeMessageDao);
      MealRequestManager.getMealRequestManager().setMealRequestDao(mealRequestDao);
      SecurityRequestManager.getSecurityRequestManager().setSecurityRequestDao(securityRequestDao);
      LanguageRequestManager.getLanguageRequestManager().setLanguageRequestDao(languageRequestDao);
      ExternalTransportManager.getRequestManager().setExternalTransportManagerDao(externalTransportRequestDao);
      InternalPatientTransportationRequestManager.getInternalPatientTransportationRequestManager().setIptrd(internalPatientTransportationRequestDao);
      ChatManager.getChatManager().setChatDao(chatDao);
      UnreadMessageManager.getUnreadMessageManager().setUnreadMessageDao(unreadMessageDao);

      // *ORDER MATTERS BECAUSE OF FOREIGN KEYS*
      ((EmployeeDaoSecureImpl) employeeDao).createTable();
      ((LocationDaoImpl) locationDao).createTable();
      ((MedEquipDaoImpl) medEquipDao).createTable();
      ((LanguageDaoImpl) languageDao).createTable();
      ((LanguageInterpreterDaoImpl) languageInterpreterDao).createTable();
      ((LabServiceRequestDaoImpl) labServiceRequestDao).createTable();
      ((MedEquipRequestDaoImpl) medEquipRequestDao).createTable();
      ((MedRequestDaoImpl) medRequestDao).createTable();
      ((CleaningRequestDaoImpl) cleaningRequestDao).createTable();
      ((FlowerRequestDaoImpl) flowerRequestDao).createTable();
      ((ComputerServiceRequestDaoImpl) csrDao).createTable();
      ((SanitationRequestDaoImpl) sanitationRequestDao).createTable();
      ((GiftDeliveryRequestDaoImpl) giftDeliveryRequestDao).createTable();
      chatDao.createTable();
      employeeMessageDao.createTable();
      unreadMessageDao.createTable();
      ((MealRequestDaoImpl) mealRequestDao).createTable();
      ((SecurityRequestDaoImpl) securityRequestDao).createTable();
      ((LanguageRequestDaoImpl) languageRequestDao).createTable();
      ((ExternalTransportRequestDaoImpl) externalTransportRequestDao).createTable();
      ((InternalPatientTransportationRequestDaoImpl)internalPatientTransportationRequestDao).createTable();
      ((HighScoreDaoImpl) highScoreDao).createTable();

    } catch (SQLException e) {
      System.out.println("Table Creation Failed");
      throw (e);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw (e);
    }
  }

  public Statement getStatement() {
    return this.statement;
  }

  /**
   * Establishes connection with embedded Apache Derby Database
   *
   * @return Returns a new connection to an embedded Apache Derby Database
   * @throws SQLException if unable to connect to database
   * @throws ClassNotFoundException if Apache Derby installation not found
   */
  private void connectEmbedded(String connectionString)
      throws SQLException, ClassNotFoundException {
    System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      throw (e);
    }
    System.out.println("Apache Derby driver registered!");

    try {
      connection = DriverManager.getConnection(connectionString);
      statement = connection.createStatement();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      throw (e);
    }

    System.out.println("Apache Derby connection established!");
  }

  private void connectServer(String connectionString) throws SQLException, ClassNotFoundException {
    System.out.println("-------Client-Server Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      throw (e);
    }
    System.out.println("Apache Derby driver registered!");

    try {
      connection = DriverManager.getConnection(connectionString);
      statement = connection.createStatement();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      throw (e);
    }

    System.out.println("Apache Derby connection established!");
  }

  /**
   * closes the connection to the embedded database
   *
   * @throws SQLException
   */
  public void closeConnection() throws SQLException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw (e);
    }
  }
}
