package com.huxley.wiitoolssample.testMultiType;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxley.wiitoolssample.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by huxley on 2017/8/10.
 */

public class SongViewBinder extends ItemViewBinder<Song, SongViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Song item) {
        holder.text.setText(item.text);
        holder.icon.setImageResource(item.icon);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        final TextView text;
        final ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
