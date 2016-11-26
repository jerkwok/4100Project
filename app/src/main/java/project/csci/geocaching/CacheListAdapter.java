package project.csci.geocaching;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jeremy on 24/11/16.
 */

public class CacheListAdapter extends ArrayAdapter<Cache> {

    Context context;
    String caches;
    List<Cache> cachesList;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cache_row, parent, false);
        TextView cacheText = (TextView) rowView.findViewById(R.id.row_cache_info);
        TextView cacheLoc = (TextView) rowView.findViewById(R.id.row_cache_loc);
        TextView cacheDesc = (TextView) rowView.findViewById(R.id.row_cache_desc);

        cacheText.setText(cachesList.get(position).getInfo());
//        cacheText.setText(cachesList.get(position).getName());
        cacheLoc.setText(cachesList.get(position).getLoc());
        cacheDesc.setText(cachesList.get(position).getDescription());


        Log.d("ADAPTER CACHES", caches);

        if ((position < caches.length()) &&
                (caches.substring(caches.length() - position - 1).charAt(0) == '1')
                ){
            rowView.setBackgroundColor(Color.BLUE);
        }
        return rowView;
    }
}
