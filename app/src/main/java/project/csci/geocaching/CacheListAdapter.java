package project.csci.geocaching;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CacheListAdapter extends ArrayAdapter<Cache> {

    private Context context;
    private String caches;
    private List<Cache> cachesList;
    private int selected;

    public void setSelected(int selected) {
        this.selected = selected;
        Log.d("Selected Changed", Integer.toString(this.selected));
    }

    public CacheListAdapter(Context context, int resource, List<Cache> objects, String caches, int selected) {
        super(context, resource, objects);
        this.context = context;
        this.caches = caches;
        cachesList = objects;
        this.selected = selected;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cache_row, parent, false);
        }

        TextView cacheText = (TextView) convertView.findViewById(R.id.row_cache_info);
        TextView cacheLoc = (TextView) convertView.findViewById(R.id.row_cache_loc);
        TextView cacheDesc = (TextView) convertView.findViewById(R.id.row_cache_desc);
        ImageView statusImage = (ImageView) convertView.findViewById(R.id.status_image);

        cacheText.setText(cachesList.get(position).getName());
        cacheLoc.setText(cachesList.get(position).getLoc());
        cacheDesc.setText(cachesList.get(position).getDescription());

//        Log.d("ADAPTER CACHES", caches);
        Log.d("Child", Integer.toString(position));
        Log.d("Caches", caches);
        Log.d("Selected", Integer.toString(selected));

        if ((position < caches.length()) &&
                (caches.substring(caches.length() - position - 1).charAt(0) == '1')
                ) {
            Log.d("Substring", Character.toString(caches.substring(caches.length() - position - 1).charAt(0)));
            statusImage.setImageResource(R.drawable.star);
        }else if(position == selected){
            statusImage.setImageResource(R.drawable.magnifying);
        }else{
            statusImage.setImageResource(0);
        }
        return convertView;
    }
}
