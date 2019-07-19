package com.example.expandablelistviewdemo.biz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.expandablelistviewdemo.bean.Chapter;
import com.example.expandablelistviewdemo.bean.ChapterItem;
import com.example.expandablelistviewdemo.dao.ChapterDao;
import com.example.expandablelistviewdemo.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChapterBiz {

    private ChapterDao mChapterDao = new ChapterDao();

    private static final String TAG = "ChapterBiz";

    public void loadData(final Context context, final CallBack callBack, boolean useCache) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Boolean, Void, List<Chapter>> asyncTask = new AsyncTask<Boolean, Void, List<Chapter>>() {

            private Exception e;

            @Override
            protected List<Chapter> doInBackground(Boolean... booleans) {

                boolean isUseCache = booleans[0];
                List<Chapter> chapterList = new ArrayList<>();

                try {
                    if (isUseCache) {
                        //load data from db
                        List<Chapter> chapterListFromDB = mChapterDao.loadFromDB(context);
                        Log.d(TAG, "doInBackground: =" + chapterListFromDB);
                        chapterList.addAll(chapterListFromDB);
                    }
                    //load from web
                    if (chapterList.isEmpty()) {
                        //load from web
                        List<Chapter> chapterListFromWeb = loadFromWeb(context);

                        //cache to db
                        mChapterDao.insertToDB(context, chapterListFromWeb);
                        chapterList.addAll(chapterListFromWeb);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    this.e = e;
                }

                return chapterList;
            }

            @Override
            protected void onPostExecute(List<Chapter> chapterList) {
                if (e != null) {
                    callBack.onFailed(e);
                    return;
                }
                callBack.onSuccess(chapterList);
            }
        };

        asyncTask.execute(useCache);

    }

    private List<Chapter> loadFromWeb(Context context) {

        List<Chapter> chapterList = new ArrayList<>();
        String url = "http://www.imooc.com/api/expandablelistview";
        //1.发送请求获取String数据
        String content = HttpUtils.doGet(url);
        Log.d(TAG, "content = " + content);

        //2.将获取的 content -> List<Chapter>
        if (content != null) {
            chapterList = parseContent(content);
            Log.d(TAG, "parse finish chapterList = " + chapterList);
        }


        return chapterList;
    }

    private List<Chapter> parseContent(String content) {
        List<Chapter> chapterList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(content);
            int errorCode = root.optInt("errorCode");
            if (errorCode == 0) {
                JSONArray jsonArray = root.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    //得到chapter
                    JSONObject chapterJsonObj = jsonArray.getJSONObject(i);
                    int id = chapterJsonObj.optInt("id");
                    String name = chapterJsonObj.optString("name");
                    Chapter chapter = new Chapter(id, name);
                    chapterList.add(chapter);

                    JSONArray childrenJsonArray = chapterJsonObj.optJSONArray("children");
                    if (childrenJsonArray != null) {
                        for (int j = 0; j < childrenJsonArray.length(); j++) {
                            JSONObject chapterItemJsonObj = childrenJsonArray.getJSONObject(j);
                            int cId = chapterItemJsonObj.optInt("id");
                            String cName = chapterItemJsonObj.optString("name");

                            ChapterItem chapterItem = new ChapterItem(cId, cName);
                            chapter.addChild(chapterItem);
                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return chapterList;
    }

    public static interface CallBack {
        void onSuccess(List<Chapter> chapterList);

        void onFailed(Exception ex);
    }
}
