package com.example.wanve4k.wanve.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhou on 2017/11/29.
 */

public class JsonConvert {
    private static Gson gson = new Gson();
    private static final JsonParser jsonParser = new JsonParser();

    public static <T> T analysisJson(String result, Type type) {
        return gson.fromJson(result, type);
    }

    /**
     * @param result 后台返回json
     * @param type   解析结果对象
     * @param key    解析json中哪段json
     * @return
     * @author:clj
     */
    public static <T> T analysisJson(String result, Type type, String key) {
        JsonElement targerElement = null;
        JsonElement jsonElement = jsonParser.parse(result);
        try {
            Set<Map.Entry<String, JsonElement>> setJsons = jsonElement.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> set : setJsons) {
                String name = set.getKey();
                if (name.equals(key)) {
                    targerElement = set.getValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.fromJson(targerElement, type);
    }

    public static <T> T analysisJson(String result, String key) throws JSONException {
        JSONObject json = null;
        json = new JSONObject(result);
        return (T) json.get(key);
    }


    public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                                                     Type listType) {
        return gson.fromJson(jsonData, listType);
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }
}
