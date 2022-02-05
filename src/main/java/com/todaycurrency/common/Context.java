package com.todaycurrency.common;

import java.util.Map;

public class Context {
    public Map<String, String> params;

    public Context(Map<String, String> params) {
        this.params = params;
    }
}
