package com.huxley.wiitoolssample.testMultiType;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxley.wiitoolssample.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by huxley on 2017/8/10.
 */

public class CategoryViewBinder extends ItemViewBinder<Category, CategoryViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Category item) {
        holder.category.setText(item.text);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView category;

        public ViewHolder(View itemView) {
            super(itemView);
            this.category = (TextView) itemView.findViewById(R.id.category);
        }
    }

}
