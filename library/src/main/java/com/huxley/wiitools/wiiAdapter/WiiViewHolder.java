package com.huxley.wiitools.wiiAdapter;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.huxley.wiitools.utils.ViewUtils;

import java.util.LinkedHashSet;

/**
 * Created by huxley on 2017/4/27.
 */
public class WiiViewHolder extends RecyclerView.ViewHolder{

    private final View convertView;
    private final SparseArray<View> views;
    private final LinkedHashSet<Integer> childClickViewIds;
    private final LinkedHashSet<Integer> childLongClickViewIds;

    public WiiViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        childClickViewIds = new LinkedHashSet<>();
        childLongClickViewIds = new LinkedHashSet<>();
        convertView = itemView;
    }

    public WiiViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        ViewUtils.setText(view, value);
        return this;
    }

    public WiiViewHolder setText(int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        ViewUtils.setText(view, strId);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = ViewUtils.getView(convertView, viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
