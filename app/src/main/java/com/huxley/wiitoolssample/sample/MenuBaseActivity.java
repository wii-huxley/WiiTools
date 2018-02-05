package com.huxley.wiitoolssample.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.huxley.wiitoolssample.R;
import com.huxley.wiitoolssample.sample.bilibili.BilibiliActivity;
import com.huxley.wiitoolssample.sample.communicate_with_binder.SimpleActivity;
import com.huxley.wiitoolssample.sample.extra_apis.SeldomUsedApisPlayground;
import com.huxley.wiitoolssample.sample.multi_select.MultiSelectActivity;
import com.huxley.wiitoolssample.sample.normal.NormalActivity;
import com.huxley.wiitoolssample.sample.one2many.OneDataToManyActivity;
import com.huxley.wiitoolssample.sample.payload.TestPayloadActivity;
import com.huxley.wiitoolssample.sample.weibo.WeiboActivity;


/**
 * @author drakeet
 */
public class MenuBaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.NormalActivity:
                intent.setClass(this, NormalActivity.class);
                break;
            case R.id.MultiSelectActivity:
                intent.setClass(this, MultiSelectActivity.class);
                break;
            case R.id.communicate_with_binder:
                intent.setClass(this, SimpleActivity.class);
                break;
            case R.id.BilibiliActivity:
                intent.setClass(this, BilibiliActivity.class);
                break;
            case R.id.WeiboActivity:
                intent.setClass(this, WeiboActivity.class);
                break;
            case R.id.OneDataToManyActivity:
                intent.setClass(this, OneDataToManyActivity.class);
                break;
            case R.id.TestPayloadActivity:
                intent.setClass(this, TestPayloadActivity.class);
                break;
            case R.id.SeldomUsedApisPlayground:
                intent.setClass(this, SeldomUsedApisPlayground.class);
                break;
            default:
                return false;
        }
        startActivity(intent);
        this.finish();
        return true;
    }
}