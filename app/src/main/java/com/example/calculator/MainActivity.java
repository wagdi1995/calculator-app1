package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputView;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputView = findViewById(R.id.inputView);
        resultView = findViewById(R.id.resultView);

        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] buttonIds = new int[]{
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv,
                R.id.btnDel, R.id.btnClear, R.id.btnEquals
        };
        for (int id : buttonIds) {
            Button btn = findViewById(id);
            if (btn != null) {
                btn.setOnClickListener(v -> {
                    String text = ((Button) v).getText().toString();
                    handleClick(text);
                });
            }
        }
    }

    private void handleClick(String text) {
        switch (text) {
            case "C":
                inputView.setText("");
                resultView.setText("");
                break;
            case "Del":
                String current = inputView.getText().toString();
                if (!current.isEmpty()) {
                    inputView.setText(current.substring(0, current.length() - 1));
                }
                break;
            case "=":
                calculate();
                break;
            default:
                inputView.append(text);
                break;
        }
    }

    private void calculate() {
        String expr = inputView.getText().toString();
        if (expr.isEmpty()) return;
        try {
            double result = eval(expr);
            resultView.setText(String.format("= %.10f", result));
        } catch (Exception e) {
            resultView.setText("Error");
        }
    }

    // Very naive expression evaluator that only supports binary +, -, *, / left to right
    private double eval(String expr) throws Exception {
        String[] tokens = expr.split("(?<=[\\-+\\*/])|(?=[\\-+\\*/])");
        double value = 0;
        if (tokens.length == 0) return 0;
        value = Double.parseDouble(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String op = tokens[i];
            double next = Double.parseDouble(tokens[i + 1]);
            switch (op) {
                case "+": value += next; break;
                case "-": value -= next; break;
                case "*": value *= next; break;
                case "/": value /= next; break;
            }
        }
        return value;
    }
}
