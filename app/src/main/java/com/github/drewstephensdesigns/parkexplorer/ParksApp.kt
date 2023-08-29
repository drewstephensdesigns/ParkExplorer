package com.github.drewstephensdesigns.parkexplorer

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.github.drewstephensdesigns.parkexplorer.utils.DelegatesExt

class ParksApp : Application() {

    companion object {
        var instance: ParksApp by DelegatesExt.notNullSingleValue()
        val TAG: String? = ParksApp::class.java.simpleName
    }
    private var mRequestQueue: RequestQueue? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        //applyTheme()
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue()?.add(req)
    }

    private fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(instance)
        } else println("Request is null")
        return mRequestQueue
    }
}