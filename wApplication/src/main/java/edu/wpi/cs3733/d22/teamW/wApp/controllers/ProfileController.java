package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import com.sun.jdi.InvalidTypeException;
import edu.wpi.cs3733.d22.teamW.Managers.AccountManager;
import edu.wpi.cs3733.d22.teamW.Managers.WindowManager;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.EmployeeImageView;
import edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls.RequestTable;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.EmployeeManager;
import edu.wpi.cs3733.d22.teamW.wDB.Managers.UserImageManager;
import edu.wpi.cs3733.d22.teamW.wDB.RequestFacade;
import edu.wpi.cs3733.d22.teamW.wDB.entity.Employee;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.wpi.cs3733.d22.teamW.wDB.entity.UserImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onLoad();
    }

    public void onLoad() {
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
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
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
        try {
            UserImageManager.getUserImageManager().deleteUserImage(AccountManager.getInstance().getEmployee().getUsername());
        } catch (SQLException e) {
            System.out.println("No profile picture found to delete.");
        }
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
}
