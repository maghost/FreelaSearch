package org.freelasearch.utils;

import android.text.Html;

/**
 * Created by msms on 29/02/2016.
 */
public class ExceptionFreelaSearch extends RuntimeException {

    public ExceptionFreelaSearch(String message) {
        super(Html.fromHtml(Html.fromHtml(message).toString()).toString());
    }

}
