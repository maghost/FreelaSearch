package org.freelasearch.tasks;

public interface AsyncTaskListener {

    void onPreExecute();

    <T> void onComplete(T obj);

    void onError(String errorMsg);

}