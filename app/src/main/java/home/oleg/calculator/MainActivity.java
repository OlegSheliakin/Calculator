package home.oleg.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputExp = (EditText) findViewById(R.id.inputExp);
        inputExp.setOnClickListener(this);

        Button[] buttons = new Button[]{
                (Button) findViewById(R.id.btnAdd),
                (Button) findViewById(R.id.btnSubtract),
                (Button) findViewById(R.id.btnMultply),
                (Button) findViewById(R.id.btnDivide),
                (Button) findViewById(R.id.btnEq),
                (Button) findViewById(R.id.btnReset),
                (Button) findViewById(R.id.btnDelete),
                (Button) findViewById(R.id.btnOne),
                (Button) findViewById(R.id.btnTwo),
                (Button) findViewById(R.id.btnThree),
                (Button) findViewById(R.id.btnFour),
                (Button) findViewById(R.id.btnFive),
                (Button) findViewById(R.id.btnSix),
                (Button) findViewById(R.id.btnSeven),
                (Button) findViewById(R.id.btnEight),
                (Button) findViewById(R.id.btnNine),
                (Button) findViewById(R.id.btnZero),
                (Button) findViewById(R.id.btnPoint)
        };

        for (Button b: buttons) {
            b.setOnClickListener(this);
        }

        setExpression(getString(R.string.zero));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, 1, 0, R.string.reset);
        menu.add(0, 2, 0, R.string.quit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 1:
                inputExp.setText(R.string.empty);
                break;
            case 2:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        double result;

        String expression = inputExp.getText().toString();

        if (expression.equals(getString(R.string.error))) {
            inputExp.setText(R.string.empty);
        }

        switch (v.getId()) {
            case R.id.btnAdd:
                setOperation(expression, MathsOperations.ADD);
                break;
            case R.id.btnSubtract:
                setOperation(expression, MathsOperations.SUBTRACT);
                break;
            case R.id.btnMultply:
                if (!expression.isEmpty()) {//doesn't allow to set the symbol if the expression is empty
                    setOperation(expression, MathsOperations.MULTIPLY);
                }
                break;
            case R.id.btnDivide:
                if (!expression.isEmpty()) {//doesn't allow to set the symbol if the expression is empty
                    setOperation(expression, MathsOperations.DIVIDE);
                }
                break;
            case R.id.btnEq:
                try {
                    expression = formExpression(expression);//forms expression before evaluating
                    result = new Calculator().calculate(expression);//gets result
                    setExpression(Double.toString(result));
                } catch (Exception e) {
                    //sets 'Error' in the expression, if it throws any exception
                    setExpression(getString(R.string.error));
                }
                break;
            case R.id.btnOne:
                inputExp.append(getString(R.string.one));
                break;
            case R.id.btnTwo:
                inputExp.append(getString(R.string.two));
                break;
            case R.id.btnThree:
                inputExp.append(getString(R.string.three));
                break;
            case R.id.btnFour:
                inputExp.append(getString(R.string.four));
                break;
            case R.id.btnFive:
                inputExp.append(getString(R.string.five));
                break;
            case R.id.btnSix:
                inputExp.append(getString(R.string.six));
                break;
            case R.id.btnSeven:
                inputExp.append(getString(R.string.seven));
                break;
            case R.id.btnEight:
                inputExp.append(getString(R.string.eight));
                break;
            case R.id.btnNine:
                inputExp.append(getString(R.string.nine));
                break;
            case R.id.btnZero:
                inputExp.append(getString(R.string.zero));
                break;
            case R.id.btnReset:
                inputExp.setText("");
                break;
            case R.id.btnDelete:
                // delete symbols until the string will be empty
                if (!expression.isEmpty()) {
                    expression = expression.substring(0, expression.length() - 1);
                    setExpression(expression);
                }
                break;
            case R.id.btnPoint:
                //sets point for value and avoids repeating of points
                setPoint(expression);
                break;
            case R.id.inputExp:
                //hides keyboard when editText is touched
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputExp.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }

    // sets operations in the expression and avoids repeating them
    private void setOperation(String expression, char operation) {
        if (expression.isEmpty()) {
            inputExp.append(String.valueOf(operation));
            expression = String.valueOf(operation);
        }

        char lastSymbol = expression.charAt(expression.length() - 1);

        if ((lastSymbol == MathsOperations.MULTIPLY || lastSymbol == MathsOperations.DIVIDE) && operation == MathsOperations.SUBTRACT) {
            inputExp.append(String.valueOf(operation));
        } else if (!MathsOperations.isOperator(lastSymbol)) {
            inputExp.append(String.valueOf(operation));
        }
    }
    //sets the expression and puts the cursor in the end of 'inputExp'
    private void setExpression(String text) {
        inputExp.setText(text);
        inputExp.setSelection(inputExp.getText().length());
    }

    private void setPoint(String expression) {
        if (expression.isEmpty()) {
            return;
        }

        int i = expression.length() - 1;
        while (!MathsOperations.isOperator(expression.charAt(i))) {
            if (i == 0) {
                break;
            }
            i--;
        }
        String str = expression.substring(i, expression.length());
        if (!str.contains(".")) {
            inputExp.append(".");
        }
    }

    private String formExpression(String expression) {
        int i = expression.length() - 1;
        while (MathsOperations.isOperator(expression.charAt(i))) {
            if (i == 0) {
                break;
            }
            i--;
        }
        return expression.substring(0, i + 1);
    }
}
