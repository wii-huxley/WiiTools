package com.huxley.wiitools.companyUtils.yscm.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.huxley.wiitools.WiiTools;
import com.huxley.wiitools.utils.SPUtils;

/**
 * Created by huxley on 2017/8/17.
 */

public class YscmSPModel extends SPUtils {

    private static final String NAME_USER = "sp_user";

    public static class User {
        private static SharedPreferences sp;
        public static final String KEY_USER = "key_user";

        private static SharedPreferences get() {
            if (sp == null) {
                synchronized (User.class) {
                    if (sp == null) {
                        sp = WiiTools.getInstance().mContext.getSharedPreferences(NAME_USER, Context.MODE_PRIVATE);
                    }
                }
            }
            return sp;
        }

        public static void save(String key, Object object) {
            input(get(), key, object);
        }

        public static Object read(String key, Object defaultObject) {
            return output(get(), key, defaultObject);
        }
    }
}
