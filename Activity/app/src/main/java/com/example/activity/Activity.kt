package com.example.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity




open class BaseActivity : ComponentActivity() {



    private val activityMap = arrayListOf<Pair<ActivityEnum, Class<*>>>()


        init {
        activityMap.add(Pair(ActivityEnum.SCREEN1, Screen1Activity::class.java))
        activityMap.add(Pair(ActivityEnum.SCREEN2, Screen2Activity::class.java))
        activityMap.add(Pair(ActivityEnum.SCREEN3, Screen3Activity::class.java))
        activityMap.add(Pair(ActivityEnum.SCREEN4, Screen4Activity::class.java))
        activityMap.add(Pair(ActivityEnum.SCREEN5, Screen5Activity::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    open fun goNext(activityEnum: ActivityEnum) {
        val activityClass = activityMap.firstOrNull { it.first == activityEnum  }?.second //firstOrNull will search in the activityMap where first element is activity.screen2 and it takes their second element can say (screen2activity)


        activityClass?.let {
            val intent = Intent(this, it)
            startActivity(intent)
        }
    }

}
