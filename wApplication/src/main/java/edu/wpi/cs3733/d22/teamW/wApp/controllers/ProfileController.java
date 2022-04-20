package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;
import edu.wpi.cs3733.d22.teamW.wMid.Account;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProfileController extends LoadableController {
  public RequestTable rt;
  @FXML ImageView profile;
  @FXML Label name;
  @FXML Label id;
  @FXML Label type;
  @FXML Label email;
  @FXML Label phoneNumber;
  @FXML Label address;

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Profile;
  }

  @Override
  public void onLoad() {
    Employee employee = Account.getInstance().getEmployee();
    name.setText(employee.getFirstName() + " " + employee.getLastName());
    id.setText(employee.getEmployeeID().toString());
    type.setText(employee.getType().getString());
    email.setText(employee.getEmail());
    phoneNumber.setText(employee.getPhoneNumber());
    address.setText(employee.getAddress());
    System.out.println("Set all employee items like name and contact info");

    rt.distributeColumnWidths();


    System.out.println("Trying to load table values now, have already loaded columns");
    try {
      rt.setItems(
          RequestFacade.getRequestFacade()
              .getAllEmployeeRequests(Account.getInstance().getEmployee().getEmployeeID()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    rt.setEditable(false);
  }

  @Override
  public void onUnload() {}

  /*
  @Override
  public void initialize(URL location, ResourceBundle rb) {
    super.initialize(location, rb);

    rt.getSelectionModel()
            .selectedItemProperty()
            .addListener(
                    (obs, oldSelection, newSelection) -> {
                      if (newSelection == null) {
                        moreInfo.setText("Select a request to view details.");
                      }
                      SR request = rt.getSelection();
                      if (request != null) {
                        try {
                          moreInfo.setText(request.getFormattedInfo());
                        } catch (SQLException e) {
                          e.printStackTrace();
                          moreInfo.setText("Error loading request details.");
                        } catch (StatusError e) {
                          e.printStackTrace();
                        } catch (NonExistingMedEquip e) {
                          e.printStackTrace();
                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                      }

                      selectionButtons.setVisible(newSelection != null);
                    });

    try {
      equipmentSelection
              .getSelectionModel()
              .selectedIndexProperty()
              .addListener((e, o, n) -> setItemsWithFilter(n.intValue()));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

   */
}
