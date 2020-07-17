package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class Controller {

    private BigDecimal left;
    private BigDecimal right;
    private String operator;

    @FXML
    private TextField numField;
    @FXML
    private Button backspace;

    public Controller(){
        this.left=BigDecimal.ZERO;
        this.right=BigDecimal.ZERO;
        this.operator="";
    }

    @FXML
    public void onButtonClicked(ActionEvent e){

        Button button=(Button) e.getSource();
        final String buttonText=button.getText();
        if (buttonText.matches("[0-9]")){
            numField.appendText(buttonText);
            return;
        }

        if (numField.getText() == null || numField.getText().length() == 0) {

        }else{
            if (e.getSource().equals(backspace)) {
                numField.setText(numField.getText().substring(0, numField.getText().length() - 1));
            }
            if (buttonText.matches("C")) {
                operator = "";
                numField.clear();
                return;
            }


            if (buttonText.matches("[+-]")) {
                left = new BigDecimal(numField.getText());
                operator = buttonText;
                numField.appendText(buttonText);
                return;
            }

            if (buttonText.matches("[=]")) {
                right = new BigDecimal(numField.getText().substring(numField.getText().indexOf(operator) + 1));
                left = calculate(operator, left, right);
                numField.setText(left.toString());
                return;
            }
        }
    }
    private static BigDecimal calculate(String operator,BigDecimal left,BigDecimal right){
        switch(operator){
            case "+": return left.add(right);
            case "-": return left.subtract(right);
        }
        return right;
    }
}
