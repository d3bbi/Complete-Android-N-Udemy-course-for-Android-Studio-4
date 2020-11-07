package mathexa.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    Boolean loginModeActive = false;

    public void toggleLoginMode(View view) {
        Button loginSignupButton = (Button) findViewById(R.id.loginSignupButton);
        TextView toggleLoginModeTextView = (TextView) findViewById(R.id.toggleLoginModeTextView);

        if (loginModeActive) {
            loginModeActive = false;
            loginSignupButton.setText("Sign Up");
            toggleLoginModeTextView.setText("Or, log in");

        } else {
            loginModeActive = true;
            loginSignupButton.setText("Log In");
            toggleLoginModeTextView.setText("Or, sign up");
        }

        // Log.i("Info", "Mode toggled");
    }


    public void signupLogin(View view) {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        if (loginModeActive) {
ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
    @Override
    public void done(ParseUser user, ParseException e) {
        if(e==null) {
            Log.i("Info","user logged in");
        } else {
            String message=e.getMessage();
            if(message.toLowerCase().contains("java")) {
                message=e.getMessage().substring(e.getMessage().indexOf(" "));
            }
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
});
        } else {


            ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Info", "user signed up");
                    } else {
                        String message=e.getMessage();
                        if(message.toLowerCase().contains("java")) {
                            message=e.getMessage().substring(e.getMessage().indexOf(" "));
                        }
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Whatsapp Login");

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        //Uncomment the code below if you cannot login to the Parse server due to this error: "invalid session token error":
        ParseUser.getCurrentUser().logOut();
    }
}