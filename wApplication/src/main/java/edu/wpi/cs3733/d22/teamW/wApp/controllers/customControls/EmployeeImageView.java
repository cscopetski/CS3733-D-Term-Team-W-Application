package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import javafx.geometry.NodeOrientation;
import javafx.scene.AccessibleRole;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeImageView extends ImageView {
    Integer empID;

    public EmployeeImageView() {
        super();
    }

    /**
     * Allocates a new ImageView object with image loaded from the specified
     * URL.
     * <p>
     * The {@code new ImageView(url)} has the same effect as
     * {@code new ImageView(new Image(url))}.
     * </p>
     *
     * @param url the string representing the URL from which to load the image
     * @throws NullPointerException if URL is null
     * @throws IllegalArgumentException if URL is invalid or unsupported
     * @since JavaFX 2.1
     */
    public EmployeeImageView(String url) {
        super(url);
    }

    /**
     * Allocates a new ImageView object using the given image.
     *
     * @param image Image that this ImageView uses
     */
    public EmployeeImageView(Image image) {
        super(image);
    }
}
