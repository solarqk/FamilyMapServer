package Handlers;

import java.io.*;

public class HandlerFunctions {
    public void writeString(String myString, OutputStream os) throws IOException {
        OutputStreamWriter output = new OutputStreamWriter(os);
        output.write(myString);
        output.flush();
    }
    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
