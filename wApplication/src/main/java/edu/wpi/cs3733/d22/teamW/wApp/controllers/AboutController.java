package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    // Whowie pics:
    @FXML ImageView ethanPic;
    @FXML ImageView edisonPic;
    @FXML ImageView eddyPic;
    @FXML ImageView jackPic;
    @FXML ImageView jasonPic;
    @FXML ImageView dylanPic;
    @FXML ImageView hasanPic;
    @FXML ImageView leonaPic;
    @FXML ImageView charliePic;
    @FXML ImageView calebPic;
    @FXML ImageView philPic;

    public AboutController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadToolTips();
    }

    public void loadToolTips(){
        //ethan:
        Tooltip ethan = new Tooltip(whowieToString("Ethan Pollack", "CS", "The Tea guy"));
        toolTipDisp(ethan, ethanPic);
        //edison:
        Tooltip edison = new Tooltip(whowieToString("Edison Zhang", "CS", "Fashion Star"));
        toolTipDisp(edison, edisonPic);
        //eddy:
        Tooltip eddy = new Tooltip(whowieToString("Edward Enyedy", "RBE, ME", "Wumbo Scrum Master"));
        toolTipDisp(eddy, eddyPic);
        //jack:
        Tooltip jack = new Tooltip(whowieToString("Jack McEvoy","CS, IMGD", "Git Guru"));
        toolTipDisp(jack, jackPic);
        //jason:
        Tooltip jason = new Tooltip(whowieToString("Jason Odell", "CS", "The Navigator"));
        toolTipDisp(jason, jasonPic);
        //dylan:
        Tooltip dylan = new Tooltip(whowieToString("Dylan Olmsted", "CS", "Gettin' Back to Me"));
        toolTipDisp(dylan, dylanPic);
        //hasan:
        Tooltip hasan = new Tooltip(whowieToString("Hasan Gandor", "CS", "CHEESE"));
        toolTipDisp(hasan, hasanPic);
        //leona:
        Tooltip leona = new Tooltip(whowieToString("Leona (Nhi Nguyen)", "RBE, CS","The Artist"));
        toolTipDisp(leona, leonaPic);
        //charlie:
        Tooltip charlie = new Tooltip(whowieToString("Charlie Kneissl-Williams", "CS", "Boba Guy"));
        toolTipDisp(charlie, charliePic);
        //caleb:
        Tooltip caleb = new Tooltip(whowieToString("Caleb Scopetski","CS","League Legend"));
        toolTipDisp(caleb, calebPic);
        //phil:
        Tooltip phil = new Tooltip(whowieToString("Philip Bui", "CS", "Buff Milk Guy"));
        toolTipDisp(phil, philPic);
    }

    private String whowieToString( String name, String major, String tag){
        return ("Name: " + name + "\nMajor: " + major + "\nTag: '" + tag + "'");
    }

    private void toolTipDisp(Tooltip whowie, Node whowiePic){
        whowie.setShowDelay(Duration.seconds(0.1));
        whowie.setHideDelay(Duration.seconds(0.3));
        whowie.setStyle("-fx-font-size: 20");
        Tooltip.install(whowiePic, whowie);
    }
}
