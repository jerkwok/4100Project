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

    public CacheListAdapter(Context context, int resource, List<Cache> objects, String caches) {
        super(context, resource, objects);
        this.context = context;
        this.caches = caches;
        cachesList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//            View rowView = inflater.inflate(R.layout.cache_row, parent, false);
            convertView = inflater.inflate(R.layout.cache_row, parent, false);
        }

        TextView cacheText = (TextView) convertView.findViewById(R.id.row_cache_info);
        TextView cacheLoc = (TextView) convertView.findViewById(R.id.row_cache_loc);
        TextView cacheDesc = (TextView) convertView.findViewById(R.id.row_cache_desc);
        ImageView statusImage = (ImageView) convertView.findViewById(R.id.status_image);
//        cacheText.setText(cachesList.get(position).getInfo());
        cacheText.setText(cachesList.get(position).getName());
        cacheLoc.setText(cachesList.get(position).getLoc());
        cacheDesc.setText(cachesList.get(position).getDescription());


        Log.d("ADAPTER CACHES", caches);

        if ((position < caches.length()) &&
                (caches.substring(caches.length() - position - 1).charAt(0) == '1')
                ){
//            rowView.setBackgroundColor(Color.BLUE);
            statusImage.setImageResource(R.drawable.star);

        }
        return convertView;
    }
}
