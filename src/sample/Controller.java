package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    enum State{
        INITIAL,
        REGULAR
    }
    public Label expressionLabel;
    public Label stateLabel;
    public Button ACButton;
    public Button backButton;
    public Button equalButton;
    State state;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clean();
    }

    public void clean() {
        expressionLabel.setText("0");
        this.state = State.INITIAL;
        stateLabel.setText("");
    }

    public void insert(ActionEvent actionEvent) {
        Button btn = (Button)actionEvent.getSource();
        String expression = this.getExpression();
        if(this.state == State.INITIAL){
            expression = "";
            this.state = State.REGULAR;
        }
        this.setExpression(expression.concat(btn.getText()));
    }

    public void delete() {
        String txt = this.getExpression();
        if(! txt.isEmpty() && this.state == State.REGULAR){
            String next = txt.substring(0,txt.length()-1);
            if(next.isEmpty()){
                this.clean();
            }else {
                this.setExpression(next);
            }
        }
    }

    public void equals(ActionEvent actionEvent) {
        Expression expression = new ExpressionBuilder(this.getExpression()).build();
        ValidationResult validationResult = expression.validate();
        if(validationResult.isValid()){
            stateLabel.setText(this.getExpression());
            double result = expression.evaluate();
            if (result == 0d) {
                this.setExpression(String.valueOf(expression.evaluate()));
                this.state = State.INITIAL;
            } else {
                this.setExpression(String.valueOf(expression.evaluate()));
            }
        }else {
            stateLabel.setText("Expression is Not Valid");
        }

    }

    private String getExpression(){
        return this.expressionLabel.getText();
    }

    private void setExpression(String txt){
        this.expressionLabel.setText(txt);
    }
}
