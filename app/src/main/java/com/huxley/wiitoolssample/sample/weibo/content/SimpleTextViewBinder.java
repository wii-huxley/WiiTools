/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huxley.wiitoolssample.sample.weibo.content;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huxley.wiitoolssample.R;
import com.huxley.wiitoolssample.sample.weibo.ContentHolder;
import com.huxley.wiitoolssample.sample.weibo.WeiboFrameBinder;

/**
 * @author drakeet
 */
public class SimpleTextViewBinder
    extends WeiboFrameBinder<SimpleText, SimpleTextViewBinder.ViewHolder> {

    @Override
    protected ContentHolder onCreateContentViewHolder(
        @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View contentView = inflater.inflate(R.layout.item_weibo_simple_text, parent, false);
        return new ViewHolder(contentView);
    }


    @Override
    protected void onBindContentViewHolder(
        @NonNull ViewHolder holder, @NonNull SimpleText simpleText) {
        holder.simpleText.setText(simpleText.text);
    }


    static class ViewHolder extends ContentHolder {

        private TextView simpleText;


        ViewHolder(View itemView) {
            super(itemView);
            simpleText = (TextView) itemView.findViewById(R.id.simple_text);
        }
    }
}
