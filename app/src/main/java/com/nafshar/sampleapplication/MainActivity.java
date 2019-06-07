package com.nafshar.sampleapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;


public class MainActivity extends AppCompatActivity {

    boolean is_empty;
    TextView expressionTextView,subtitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expressionTextView = findViewById(R.id.expressionText);
        subtitleTextView = findViewById(R.id.subtitleText);
        //initializing it
        expressionTextView.setText("0");
        this.is_empty = true;
        subtitleTextView.setText("");
    }

    private void setExpression(String text){
        expressionTextView.setText(text);
    }

    private String getExpression(){
        return (String) expressionTextView.getText();
    }

    public void cleanUp(View view) {
        is_empty = true;
        setExpression("0");
        subtitleTextView.setText("");
    }

    public void add(View view) {
        Button button = (Button)view;
        String text = (String) button.getText();
        if(is_empty){
            setExpression(text);
            is_empty = false;
        }else {
            setExpression(getExpression()+text);
        }
    }

    public void delete(View view) {
        if(! is_empty){
            String text = getExpression();
            String newText = text.substring(0,text.length()-1);
            if(newText.length() == 0){
                is_empty = true;
                setExpression("0");
            }else {
                setExpression(newText);
            }
        }
    }

    public void equals(View view) {
        String expressionText = getExpression();
        Expression expression = new ExpressionBuilder(expressionText).build();
        ValidationResult validationResult = expression.validate();
        if(validationResult.isValid()){
            subtitleTextView.setText(expressionText);
            setExpression(String.valueOf(expression.evaluate()));
        }else {
            subtitleTextView.setText("Invalid Expression!");
        }
    }
}
