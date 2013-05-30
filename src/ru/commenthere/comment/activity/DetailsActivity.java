package ru.commenthere.comment.activity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import ru.commenthere.comment.AppContext;
import ru.commenthere.comment.Application;
import ru.commenthere.comment.R;
import ru.commenthere.comment.R.id;
import ru.commenthere.comment.R.layout;
import ru.commenthere.comment.model.Note;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

public class DetailsActivity extends ListActivity implements OnClickListener {
	private RadioGroup radioPanel;
	
	private ImageView imageView;
	private VideoView videoView;

	private TextView descTextView;
	private EditText commentEditText;
	
	private Button backButton;
	private Button downloadButton;
	private Button sendButton;
	
	
	private ImageLoader imageLoader;
	private DisplayImageOptions imageOptions;

	private Note note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		parseParams();
		initViews();
		initImageLoader();
		fillData();
	}

	private void parseParams() {
		note = (Note) getIntent().getSerializableExtra(AppContext.NOTE_KEY);
	}

	private void initViews() {
		radioPanel = (RadioGroup)findViewById(R.id.radio_panel);
		imageView = (ImageView)findViewById(R.id.image_view);
		videoView = (VideoView)findViewById(R.id.video_view);
		
		descTextView  = (TextView)findViewById(R.id.desc_text);
		commentEditText = (EditText)findViewById(R.id.comment_edittext);
		
		backButton = (Button)findViewById(R.id.button_back);
		downloadButton = (Button)findViewById(R.id.button_download);
		sendButton = (Button)findViewById(R.id.button_send); 

		backButton.setOnClickListener(this);
		downloadButton.setOnClickListener(this);
		sendButton.setOnClickListener(this);
	}
	
	private void initImageLoader(){
		imageLoader = ImageLoader.getInstance();
		imageOptions = createImageOptions();
	}
	
	private void fillData(){
		if (note.getFileType()==AppContext.PHOTO_FILE_TYPE){
			videoView.setVisibility(View.GONE);
			imageView.setVisibility(View.VISIBLE);			
			imageLoader.displayImage(AppContext.PHOTOS_URL + note.getFileName(), imageView, imageOptions);
		}else{
			videoView.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.GONE);
			videoView.setVideoURI(Uri.parse(AppContext.VIDEOS_URL + note.getFileName()));
		}
		
		descTextView.setText(note.getDescription());
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_back){
			finish();
		} else if(v.getId() == R.id.button_download){
			downloadFile();			
		} else if (v.getId() == R.id.button_send){
			
		}

	}
	
	private void downloadFile(){
		String url = null;
		if (note.getFileType()==AppContext.PHOTO_FILE_TYPE){
			url = AppContext.PHOTOS_URL + note.getFileName();
		}else{
			url = AppContext.VIDEOS_URL + note.getFileName();
		}
		
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setDescription(note.getDescription());
		request.setTitle("Download file");
		// in order for this if to run, you must use the android 3.2 to compile your app
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		    request.allowScanningByMediaScanner();
		    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		}
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, note.getFileName());

		// get download service and enqueue file
		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);
	}

	@Override
	protected void onStop() {
		Application.getInstance().decForegroundActiviesCount();
		super.onStop();
	}

	@Override
	protected void onStart() {
		Application.getInstance().incForegroundActiviesCount();
		super.onStart();
	}
	
	private DisplayImageOptions createImageOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading().cacheInMemory().cacheOnDisc().build();
		return options;
	}

}
