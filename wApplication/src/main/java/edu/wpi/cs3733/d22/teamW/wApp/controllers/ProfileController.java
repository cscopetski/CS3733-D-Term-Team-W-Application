package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import com.sun.jdi.InvalidTypeException;
import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.PageManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmployeeImageView;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.FilterControl;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.MedicalEquipmentSR;
import edu.wpi.cs3733.d22.teamW.wApp.serviceRequests.SR;
import edu.wpi.cs3733.d22.teamW.wDB.Errors.*;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.LocationManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.UserImageManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.wpi.cs3733.d22.teamW.wDB.entity.Request;
import edu.wpi.cs3733.d22.teamW.wDB.entity.UserImage;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class ProfileController implements Initializable {
    public RequestTable rt;
    @FXML
    ImageView profile;
    @FXML
    Label name;
    @FXML
    Label id;
    @FXML
    Label type;
    @FXML
    Label email;
    @FXML
    Label phoneNumber;
    @FXML
    Label address;
    @FXML
    public TextArea moreInfo;
    @FXML
    public FilterControl<RequestType> filter;
    @FXML
    public CheckBox emergencyFilter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });

        filter.loadValues(RequestType.values());
        filter.addValuesListener(c -> resetItems());
        emergencyFilter.selectedProperty().addListener(c -> resetItems());
        PageManager.getInstance().attachOnLoad(PageManager.Pages.Profile, this::onLoad);
        onLoad();
    }

    public void onLoad() {
        rt.distributeColumnWidths();
        moreInfo.setText("Select a request to view details.");
        resetItems();

        Employee employee = AccountManager.getInstance().getEmployee();
        profile.setImage(EmployeeImageGenerator.generateEmployeeImage(employee.getEmployeeID()));
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
                            .getAllEmployeeRequests(AccountManager.getInstance().getEmployee().getEmployeeID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rt.setEditable(false);
    }

    public void uploadProfilePictureAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");

        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(WindowManager.getInstance().getPrimaryStage());
        if (selectedFile != null) {
            //Delete old photo
            try {
                UserImageManager.getUserImageManager().deleteUserImage(AccountManager.getInstance().getEmployee().getUsername());
            } catch (SQLException e) {
                System.out.println("Deleted old picture.");
            }
            //Add new photo
            try {
                UserImageManager.getUserImageManager().addNewUserImage(new UserImage(AccountManager.getInstance().getEmployee().getUsername(), selectedFile.toPath().toString()));
                System.out.println("Successfully set picture");
                onLoad();
            } catch (SQLException e) {
                System.out.println("Duplicate username entry.");
            } catch (IOException e) {
                System.out.println("Error in copying/loading file.");
            } catch (InvalidTypeException e) {
                System.out.println("File is not a .png");
            }
        } else {
            System.out.println("No file selected.");
        }

    }

    public void deleteProfilePictureAction(ActionEvent actionEvent) {
        Alert confirmAlert = new ConfirmAlert();
        confirmAlert.showAndWait();
        if (confirmAlert.getResult() == ButtonType.OK) {
            try {
                UserImageManager.getUserImageManager().deleteUserImage(AccountManager.getInstance().getEmployee().getUsername());
            } catch (SQLException e) {
                System.out.println("No profile picture found to delete.");
            }
        }
        onLoad();
    }

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

    private void resetItems() {
        try {
            ArrayList<Request> employeeItems = new ArrayList<Request>();

            if (emergencyFilter.isSelected()) {
                for (Request r : RequestFacade.getRequestFacade()
                        .getRequests(filter.getEnabledValues().toArray(new RequestType[]{}))) {
                    if (r.getEmergency() == 1) {
                        if (r.getEmployeeID().equals(AccountManager.getInstance().getEmployee().getEmployeeID())) {
                            employeeItems.add(r);
                        }
                    }
                }
            } else {
                ArrayList<Request> items = RequestFacade.getRequestFacade()
                        .getRequests(filter.getEnabledValues().toArray(new RequestType[]{}));
                for (Request r : items) {
                    if (r.getEmployeeID().equals(AccountManager.getInstance().getEmployee().getEmployeeID())) {
                        employeeItems.add(r);
                    }
                }
            }

            rt.setItems(employeeItems);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearSelection() {
        rt.getSelectionModel().clearSelection();
        resetItems();
    }
}
