package tw.tonyyang.drama

import android.app.Application
import android.content.Context

class CoreApplication : Application() {

    companion object {

        private lateinit var mInstance: CoreApplication

        fun getContext(): Context {
            return mInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}