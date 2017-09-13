package com.huxley.wiitoolssample.testMultiType;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.huxley.wiitoolssample.R;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TestMultiTypeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MultiTypeAdapter adapter;

    private Items items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_multi_type_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        adapter = new MultiTypeAdapter();
        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(Song.class, new SongViewBinder());
        mRecyclerView.setAdapter(adapter);

        items = new Items();
        for (int i = 0; i < 20; i++) {
            items.add(new Category("Songs"));
            items.add(new Song("drakeet", R.drawable.kale));
            items.add(new Song("wii", R.drawable.ic_error));
        }
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }


}
