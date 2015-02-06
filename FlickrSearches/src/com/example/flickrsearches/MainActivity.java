package com.example.flickrsearches;

import java.util.ArrayList;
import java.util.Collections;

import com.example.flickrsearches.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	
	private static final String SEARCHES = "searches";

	private EditText queryEditText;
	private EditText tagEditText;
	private SharedPreferences savedSearches; 
	private ArrayList<String> tags; 
	private ArrayAdapter<String> adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		queryEditText = (EditText) findViewById(R.id.queryEditText);
		tagEditText = (EditText) findViewById(R.id.tagEditText);

		
		savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);

		
		tags = new ArrayList<String>(savedSearches.getAll().keySet());
		Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);

		
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, tags);
		setListAdapter(adapter);

		
		ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(saveButtonListener);

		
		getListView().setOnItemClickListener(itemClickListener);

		
	} 

	
	public OnClickListener saveButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
		
			if (queryEditText.getText().length() > 0
					&& tagEditText.getText().length() > 0) {
				addTaggedSearch(queryEditText.getText().toString(), tagEditText
						.getText().toString());
				queryEditText.setText(""); 
				tagEditText.setText(""); 

				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(tagEditText.getWindowToken(),
								0);
			} else 
			{
				
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);

				
				builder.setMessage(R.string.missingMessage);

				
				builder.setPositiveButton(R.string.OK, null);

			
				AlertDialog errorDialog = builder.create();
				errorDialog.show(); 
			}
		} 
	}; 

	
	private void addTaggedSearch(String query, String tag) {
		
		SharedPreferences.Editor preferencesEditor = savedSearches.edit();
		preferencesEditor.putString(tag, query);
		preferencesEditor.apply(); 

		
		if (!tags.contains(tag)) {
			tags.add(tag); 
			Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
			adapter.notifyDataSetChanged();
		}
	}

	
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			final String tag = ((TextView) view).getText().toString();

			
			
					
							String urlString = "";
							Intent webIntent = new Intent();
							String searchTag = savedSearches.getString(tag, "");
							
						
						
							
							
								urlString = getString(R.string.searchURL)
										+ Uri.encode(
												searchTag,
												"UTF-8");

								
								webIntent = new Intent(Intent.ACTION_VIEW, Uri
										.parse(urlString));

								startActivity(webIntent);
								
						
					
			
			
			
						
						
					} 
				};


	

	

	public static final class DialogSetup extends DialogFragment {
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			String[] filterOptions = { "images", "videos", "links" };
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.filterType)
					.setCancelable(false)
					.setItems(filterOptions,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									
									switch (which) {
									case 0: 
										break;
									case 1: 

										break;
									case 2: 
										break;
									}
								}
							});

			return builder.create();
		}
	}

	public void chooseFilterType() {
		DialogFragment newFragment = new DialogSetup();
		newFragment.setCancelable(false);
		newFragment.show(getFragmentManager(), "citypref");

	}
} 