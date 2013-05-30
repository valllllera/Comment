package ru.commenthere.comment.adapter;

import java.util.List;

import ru.commenthere.comment.R;
import ru.commenthere.comment.model.Note;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NotesAdapter extends BaseAdapter {

	private List<Note> notesList;
	private Context cnt;
	private ViewHandler viewHandler;
	private ImageLoader imageLoader;
	private DisplayImageOptions imageOptions;

	public NotesAdapter(Context context, List<Note> notes) {
		cnt = context;
		notesList = notes;
		imageLoader = ImageLoader.getInstance();
		imageOptions = createImageOptions();
	}

	public void setDataList(List<Note> comments) {
		notesList = comments;
	}

	@Override
	public int getCount() {
		return notesList == null ? 0 : notesList.size();
	}

	@Override
	public Object getItem(int position) {
		return notesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		long id = 0;
		if (position >= 0 && position < notesList.size()) {
			id = notesList.get(position).getId();
		}
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflateListItem();
			convertView.setTag(viewHandler);
		}
		viewHandler = (ViewHandler) convertView.getTag();
		Note note = notesList.get(position);
		imageLoader.displayImage(note.getFileNamePreview(), viewHandler.image,
				imageOptions);
		// imageLoader.displayImage(note.getFileNamePreview(),
		// viewHandler.statusImage, imageOptions);
		viewHandler.firstName.setText(note.getDescription());
		viewHandler.likesAmount.setText(String.valueOf(note.getLikes()));
		viewHandler.dislikesAmount.setText(String.valueOf(note.getDislikes()));
		convertView.setTag(viewHandler);
		return convertView;
	}

	private View inflateListItem() {
		LayoutInflater inflanter = (LayoutInflater) cnt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = (RelativeLayout) inflanter.inflate(R.layout.note_item,
				null);
		viewHandler = new ViewHandler();
		viewHandler.image = (ImageView) layout.findViewById(R.id.note_image);
		viewHandler.firstName = (TextView) layout
				.findViewById(R.id.note_first_name);
		viewHandler.likesAmount = (TextView) layout
				.findViewById(R.id.note_likes_amount);
		viewHandler.dislikesAmount = (TextView) layout
				.findViewById(R.id.note_dislikes_amount);
		return layout;
	}

	private DisplayImageOptions createImageOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading().cacheInMemory().cacheOnDisc().build();
		return options;
	}

	private static class ViewHandler {
		public ImageView image;
		public TextView firstName;
		public TextView status;
		public TextView likesAmount;
		public TextView dislikesAmount;
	}

}
