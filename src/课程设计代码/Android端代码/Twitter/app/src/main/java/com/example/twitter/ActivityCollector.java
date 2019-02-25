package com.example.twitter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018年12月14日 0014.
 */

public class ActivityCollector {
    public  static List<Activity> activities = new ArrayList<>();
    public static void addActivtiy(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }

        }
        activities.clear();
    }
}
