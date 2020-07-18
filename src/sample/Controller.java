package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Controller {

    private BigDecimal left;
    private BigDecimal right;
    private String operator;

    @FXML
    private TextField numField;
    @FXML
    private Button backspace;
    @FXML
    private Button pow;
    @FXML
    private Button sqrt;

    public Controller(){
        this.left=BigDecimal.ZERO;
        this.right=BigDecimal.ZERO;
        this.operator="";
    }

    @FXML
    public void onButtonClicked(ActionEvent e){

        Button button=(Button) e.getSource();
        final String buttonText=button.getText();
        if (buttonText.matches("[0-9.]")){
            numField.appendText(buttonText);
            return;
        }

        if (!numField.getText().isEmpty() || numField.getText().length() != 0) {//when there is no number

            if (e.getSource().equals(backspace)) {
                if (numField.getText().contains(operator)) {
                    numField.setText(numField.getText().substring(0, numField.getText().length() - 1));
                    operator="";
                }else numField.setText(numField.getText().substring(0, numField.getText().length() - 1));
            }
            if (buttonText.matches("AC")) {
                operator = "";
                numField.clear();
                left=BigDecimal.ZERO;
                right=BigDecimal.ZERO;
                return;
            }
            if (e.getSource().equals(pow)) {
                left = new BigDecimal(numField.getText());
                numField.setText(left.pow(2).toString());
                return;
            }
            if (e.getSource().equals(sqrt)) {
                left = new BigDecimal(numField.getText());
                if (left.compareTo(BigDecimal.ZERO)<0){
                    numField.setText("Invalid input");
                    return;
                }
                MathContext mc=new MathContext(10);
                numField.setText(left.sqrt(mc).toString());
                return;
            }

            if (buttonText.matches("[x÷+-]")) {
               if (operator.isEmpty()) {
                   left = new BigDecimal(numField.getText());
                   operator = buttonText;
                   numField.appendText(buttonText);
                   return;
               }
            }

            if (buttonText.equals("=")) {
                String temp=numField.getText().substring(numField.getText().indexOf(operator) + 1);

                if (temp.isEmpty()) right = left;//when right side of calculation is empty
                else right = new BigDecimal(temp);

                if(right.compareTo(BigDecimal.ZERO)==0){
                    numField.setText("Cannot divide by zero");
                    return;
                }
                left = calculate(operator, left, right);
                numField.setText(left.toString());
            }
        }
    }
    private static BigDecimal calculate(String operator,BigDecimal left,BigDecimal right){
        switch(operator){
            case "+": return left.add(right);
            case "-": return left.subtract(right);
            case "x": return left.multiply(right);
            case "÷": return left.divide(right,2, RoundingMode.DOWN);
        }
        return right;
    }
}
