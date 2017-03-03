package eu.imouto.hupl.upload;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import eu.imouto.hupl.data.FileToUpload;
import eu.imouto.hupl.data.UploaderDB;
import eu.imouto.hupl.data.UploaderEntry;

public class UploaderFactory
{
    public static Uploader getUploaderByName(Context context, String name, FileToUpload file)
    {
        UploaderDB db = new UploaderDB(context);
        UploaderEntry entry = db.getUploaderByName(name);
        if (entry == null)
            return null;

        String type = getStr(entry.json, "type");
        if (type.equals("http"))
        {
            HttpUploader up = new HttpUploader(file);
            up.name = entry.name;
            up.targetUrl = getStr(entry.json, "targetUrl");
            up.fileParam = getStr(entry.json, "fileParam");
            up.responseRegex = getStr(entry.json, "responseRegex");
            up.authUser = getStr(entry.json, "authUser");
            up.authPass = getStr(entry.json, "authPass");
            return up;
        }
        return null;
    }

    private static String getStr(JSONObject obj, String name)
    {
        String str = null;
        try
        {
            str = obj.getString(name);
        }
        catch (JSONException e)
        {}
        return str;
    }
}
