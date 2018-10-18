package org.devio.simple;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class MyBaseAdapter<E> extends BaseAdapter {
    private List<E> list;

    public MyBaseAdapter(List<E> list, Context context) {
        super();
        this.list = list;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {

        this.list = list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
