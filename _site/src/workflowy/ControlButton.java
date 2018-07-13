
package workflowy;

import javafx.scene.control.Button;

//special cases for the button
public class ControlButton extends Button {
    public ControlButton(){
            setText("•");
            setPrefHeight(15);
            setPrefWidth(15);
            setTranslateY(0);
            setTranslateX(3);
            setMaxHeight(USE_PREF_SIZE);
            setMinHeight(USE_PREF_SIZE);
            setMaxWidth(USE_PREF_SIZE);
            setMinWidth(USE_PREF_SIZE);
            setMnemonicParsing(false);
        }
}
