package com.TQS.lab2;

import java.io.IOException;

public interface ISimpleHttpClient {

    public String doHttpGet(String url) throws IOException;
}
