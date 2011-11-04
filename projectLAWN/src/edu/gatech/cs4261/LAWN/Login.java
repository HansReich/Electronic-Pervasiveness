package edu.gatech.cs4261.LAWN;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends CustomActivity {
	private static final String TAG = "Login";
	
	/* what to do when the activity is first made*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		/* set up the button listeners*/
		Button btnLogin = (Button)findViewById(R.id.LogInButton);
		btnLogin.setOnClickListener(LoginListener);
		
		/** TODO: set up something to check for remember user*/
	}
	
	/* make the login click listener*/
	private OnClickListener LoginListener = new OnClickListener() {
		public void onClick(View v) {
			/*what to do when the button is clicked*/
			
			/* set up the input listeners*/
			EditText userField = (EditText)findViewById(R.id.UsernameInput);
			
			/* get the data from the input fields*/
			String username = userField.getText().toString();
		}
	};
}
