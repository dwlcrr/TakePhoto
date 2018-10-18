package org.devio.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 17/1/10.
 */

public class BossCenterMessAdapter extends MyBaseAdapter<String> {
    private List<String> list;
    private Context context;

    public BossCenterMessAdapter(List<String> list, Context context) {
        super(list, context);
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_item, null);
            holder = new ViewHolder();

            holder.simple_text = convertView.findViewById(R.id.simple_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.simple_text.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView simple_text;
    }
}
