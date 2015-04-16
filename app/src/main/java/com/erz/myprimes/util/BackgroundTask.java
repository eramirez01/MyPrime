package com.erz.myprimes.util;

import android.os.AsyncTask;

import com.erz.myprimes.R;
import com.erz.myprimes.data.Result;
import com.erz.myprimes.listeners.BackgroundTaskListener;

import java.util.concurrent.RejectedExecutionException;

public class BackgroundTask extends AsyncTask<Object, BackgroundTask.BackgroundTaskStatus, Result> {
    BackgroundTaskListener listener;

    public BackgroundTask(BackgroundTaskListener listener) {
        this.listener = listener;
    }

    //http://www.algolist.net/Algorithms/Number_theoretic/Sieve_of_Eratosthenes
    protected Result doExecute(Object... params) {
        Result result = new Result();
        result.setStart(System.currentTimeMillis());
        int top = 0, i;
        if(params.length > 0 && params[0] instanceof Integer) {
            top = (Integer) params[0];
        }

        boolean[] isPrime = new boolean[top + 1];
        for (i = 2; i <= top; i++) {
            isPrime[i] = true;
        }

        for (i = 2; i*i <= top; i++) {
            if (isPrime[i]) {
                for (int j = i; i*j <= top; j++) {
                    isPrime[i*j] = false;
                }
            }
        }

        int primes = 0;
        for (i = 2; i <= top; i++) {
            if (isPrime[i]) {
                progress(i, R.drawable.green_bg);
                primes++;
            }
        }

        result.setCount(primes);
        result.setEnd(System.currentTimeMillis());
        return result;
    }

    public void run(Object... params) {
        try {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } catch(RejectedExecutionException e) {
            listener.progress(0, 0);
        }
    }

    @Override
    protected final void onPreExecute() {
        listener.begin();
    }

    @Override
    protected final Result doInBackground(Object... params) {
        return doExecute(params);
    }

    @Override
    protected final void onPostExecute(Result result) {
        if (listener != null) listener.complete(result);
        listener = null;
    }

    @Override
    protected final void onProgressUpdate(BackgroundTaskStatus... values) {

        if (listener == null) return;

        for (BackgroundTaskStatus value : values) {
            listener.progress(value.position, value.color);
        }

    }

    protected void progress(int position, int color) {
        publishProgress(new BackgroundTaskStatus(position, color));
    }

    public static final class BackgroundTaskStatus {
        int position;
        int color;

        public BackgroundTaskStatus(int position, int color) {
            this.position = position;
            this.color = color;
        }
    }
}
