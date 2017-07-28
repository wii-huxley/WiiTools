package com.huxley.wiitools.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.huxley.wiidialog.WiiDialog;
import com.huxley.wiitools.R;
import com.huxley.wiitools.utils.ViewUtils;

/**
 * Created by huxley on 2017/7/3.
 */

public class DialogUtils {

    public static WiiDialog getBottom(Context context, String title, View contentView) {
        View headerView = ViewUtils.getLayout(context, R.layout.dialog_header);
        TextView tvTitle = (TextView)headerView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        return WiiDialog.newDialog(context)
                .setExpanded(false)
                .setCancelable(true)
                .setGravity(17)
                .setContentHolder(new com.huxley.wiidialog.ViewHolder(contentView))
                .setHeader(headerView).create();
    }

    public static WiiDialog getSimpleCenter(Context context, String title, String content, String sure, View.OnClickListener listener) {
        View view = ViewUtils.getLayout(context, R.layout.dialog_center_simple);
        TextView tvContent = ViewUtils.getView(view, R.id.tv_content);
        ViewUtils.setText(tvContent, content);
        return getCenter(context, title, view, "取消", sure, listener);
    }

    public static WiiDialog getCenter(Context context, String title, View contentView, String cancel, String sure, final View.OnClickListener listener) {
        View headerView = ViewUtils.getLayout(context, R.layout.dialog_header);
        TextView tvTitle = ViewUtils.getView(headerView, R.id.tv_title);
        ViewUtils.setText(tvTitle, title);
        View footerView = ViewUtils.getLayout(context, R.layout.dialog_footer);
        TextView tvCancel = ViewUtils.getView(footerView, R.id.tv_cancel);
        TextView tvSure = ViewUtils.getView(footerView, R.id.tv_sure);
        ViewUtils.setText(tvCancel, cancel);
        ViewUtils.setText(tvSure, sure);
        final WiiDialog dialogPlus = WiiDialog.newDialog(context)
                .setExpanded(false)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new com.huxley.wiidialog.ViewHolder(contentView))
                .setHeader(headerView)
                .setFooter(footerView)
                .create();
        ViewUtils.setOnClickListener(tvSure, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
                listener.onClick(view);
            }
        });
        ViewUtils.setOnClickListener(tvCancel, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
            }
        });
        return dialogPlus;
    }
}
