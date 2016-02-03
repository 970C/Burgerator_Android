package burgerator.db;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

public class MultipartRequest extends Request<JSONObject> {


    private MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    private HttpEntity httpentity;
    private static final String FILE_PART_NAME = "image";

    private final Response.Listener<JSONObject> mListener;
    private final File mFilePart;
    private final Map<String, String> mStringPart;

    public MultipartRequest(String url, Response.ErrorListener errorListener,
                            Response.Listener<JSONObject> listener, File file,
                            Map<String, String> mStringPart) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        this.mStringPart = mStringPart;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        buildMultipartEntity();
    }

    public void addStringBody(String param, String value) {
        mStringPart.put(param, value);
    }

    private void buildMultipartEntity() {
        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
            entity.addTextBody(entry.getKey(), entry.getValue());
        }
        // Comes after for loop to override the value of "image"
        if(mFilePart == null){
            entity.addTextBody(FILE_PART_NAME, "");
        }else{ entity.addPart(FILE_PART_NAME, new FileBody(mFilePart)); }
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}