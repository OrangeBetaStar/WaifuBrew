package start;


import javax.swing.*;

// This is what will be used to notify users if something may cause undesirable operation. (i.e.: going back to screen when settings are not saved.)
public class NoticeBox extends JPanel {

    // Size of notice box
    int length = WaifuBrew.getInstance().getRes()[1].x / 2;
    int height = WaifuBrew.getInstance().getRes()[1].y / 2;

    // This will be (slightly transparent) backing of the notice box.
    javaxt.io.Image backgroundPane;

    // Custom Save
    CustomButton saveButton;

    // Custom Back
    CustomButton backButton;

    public NoticeBox() {
        backgroundPane = new javaxt.io.Image(WaifuBrew.getInstance().getImageByName(ImageSelector.VECTOR, "whitebox"));
        backButton = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2) - (length), (WaifuBrew.getInstance().getRes()[1].y / 2) + (height), "config_back_button", Origin.LEFT_BOTTOM, 0, true);
        saveButton = new CustomButton((WaifuBrew.getInstance().getRes()[1].x / 2) + (length), (WaifuBrew.getInstance().getRes()[1].y / 2) + (height), "config_save_button", Origin.RIGHT_BOTTOM, 0, false);

    }



}
