package com.erz.myprimes.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.erz.myprimes.R;
import com.erz.myprimes.adapters.MyPrimeAdapter;
import com.erz.myprimes.data.Result;
import com.erz.myprimes.fragments.ResultDialog;
import com.erz.myprimes.listeners.BackgroundTaskListener;
import com.erz.myprimes.util.BackgroundTask;

public class MainActivity extends ActionBarActivity implements BackgroundTaskListener {

    MyPrimeAdapter adapter;
    BackgroundTask task;
    EditText number;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.grid);
        adapter = new MyPrimeAdapter(this, 0);
        gridView.setAdapter(adapter);
        gridView.setFastScrollEnabled(true);
        gridView.setEmptyView(findViewById(R.id.empty));
        task = new BackgroundTask(this);
        number = (EditText) findViewById(R.id.number);
        number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH) {
                    search();

                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(number.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    private void search() {
        String num = number.getText().toString();
        int n;
        try {
            n = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            n = 0;
        }

        adapter.setData(n);

        task.cancel(true);
        task = new BackgroundTask(MainActivity.this);
        task.run(n);
    }

    @Override
    public void progress(int position, int color) {
        adapter.setItemColor(position, color);
        if (position < adapter.getCount()
                && position >= gridView.getFirstVisiblePosition()
                && position <= gridView.getLastVisiblePosition()) {

            View v = gridView.getChildAt(position);
            if (v == null) return;

            MyPrimeAdapter.ViewHolder holder = (MyPrimeAdapter.ViewHolder) v.getTag();
            if (holder == null) return;

            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundResource(color);
        }
    }

    @Override
    public void complete(Result result) {
        adapter.notifyDataSetChanged();
        FragmentManager fm = getSupportFragmentManager();
        ResultDialog resultDialog = (ResultDialog) fm.findFragmentByTag("resultDialog");
        if (resultDialog == null) {
            resultDialog = ResultDialog.newInstance(result);
        } else {
            resultDialog.dismiss();
        }

        resultDialog.show(fm, "resultDialog");
    }

    @Override
    public void begin() {}
}
