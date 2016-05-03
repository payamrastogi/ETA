package com.nyu.cs9033.eta.http;

/**
 * Created by payamrastogi on 4/21/16.
 */
public interface JSONListener
{
    void jsonReceivedSuccessfully(String json);
    void jsonReceivedFailed();
}