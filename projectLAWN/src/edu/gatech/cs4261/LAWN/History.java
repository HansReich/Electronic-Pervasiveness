package edu.gatech.cs4261.LAWN;

import android.os.Bundle;
import android.widget.Toast;

public class History extends CustomActivity {
	/*called when the class is first made*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        
        /* DEBUG*/
        Toast debug = Toast.makeText(getBaseContext(), "In History Class", Toast.LENGTH_SHORT);
		debug.show();
	}
}
