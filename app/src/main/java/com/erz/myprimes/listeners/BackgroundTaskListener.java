package com.erz.myprimes.listeners;

import com.erz.myprimes.data.Result;

public interface BackgroundTaskListener {
    void progress(int position, int color);
    void complete(Result result);
    void begin();
}