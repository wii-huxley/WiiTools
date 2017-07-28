package com.huxley.wiitools.commAdapter.adapter.base;


import com.huxley.wiitools.commAdapter.adapter.ViewHolder;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);



}
