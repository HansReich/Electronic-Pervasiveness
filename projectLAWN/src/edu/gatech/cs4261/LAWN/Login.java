package edu.gatech.cs4261.LAWN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
		//check a boolean in preferences to see if remember was used last time
		
		//if boolean was false wipe the username in preferences and wait for the new one
		
		//if boolean was true start the main activity
	}
	
	/* make the login click listener*/
	private OnClickListener LoginListener = new OnClickListener() {
		public void onClick(View v) {
			/*what to do when the button is clicked*/
			
			/* set up the input listeners*/
			EditText userField = (EditText)findViewById(R.id.UsernameInput);
			
			/* get the data from the input fields*/
			String username = userField.getText().toString();
			
			/*call the main activity with the username if it's hreichenba3 or hbaker3*/
			if(username.equals("hreichenba3") || username.equals("hbaker3")) {
				//make the intent to call the new screen
				Intent KingRaw = new Intent(Login.this, ProjectLAWNActivity.class);
				
				//add the participant id to the intent
				KingRaw.putExtra("username", username);
				
				//start the new activity
				Login.this.startActivity(KingRaw);
				
				Log.i(TAG, "Successful log in");
			} else {
				Log.i(TAG, "Login failed");
				
				/*display a message indicating failure to login*/
				Toast display = Toast.makeText(getBaseContext(), "Failed to login", Toast.LENGTH_SHORT);
				display.show();
			}
		}
	};
}