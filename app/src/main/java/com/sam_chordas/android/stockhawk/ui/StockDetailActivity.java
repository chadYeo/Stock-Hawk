package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.service.StockIntentService;

public class StockDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DEFAULT_RANGE_WEEK = 7;
    private static final int RANGE_MONTH = 30;
    private static String symbol;
    private static String name;
    private static String range;
    private final String LOG_TAG = StockDetailActivity.class.getSimpleName();
    boolean isConnected;
    private Intent mServiceIntent;
    private Cursor mCursor;
    private LineChartView lineChartView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = this;
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        setContentView(R.layout.activity_line_graph);
        TextView errorText = (TextView) findViewById(R.id.emptyView);
        lineChartView = (LineChartView) findViewById(R.id.linechart);
        Bundle extras = getIntent().getExtras();
        symbol = extras.getString(QuoteColumns.SYMBOL);
        name = extras.getString(QuoteColumns._ID);
        range = getString(R.string.week);
        if (name != null && !name.isEmpty()) {
            ActionBar mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setTitle(name);
            }
            mServiceIntent = new Intent(this, StockIntentService.class);
            if (savedInstanceState == null) {
                if (isConnected) {
                    range = getString(R.string.week);
                } else {
                    if (errorText != null) {
                        errorText.setVisibility(View.VISIBLE);
                    }
                }
            }
            getLoaderManager().initLoader(MyStocksActivity.CURSOR_LOADER_ID, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_stocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_week_history: {

            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
