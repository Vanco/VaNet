package io.van.vanet

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // detect the screen ratio to 1920, which will make the scaled screen to looks like 1920.
        val dm = resources.displayMetrics
        val screenWidth = dm.widthPixels
        val screenHeight = dm.heightPixels

        val ratio = screenWidth / 1920.0

        Log.d("VaNet", "Screen $screenWidth x $screenHeight, Ratio = $ratio")

        // fake the PC userAgent String
        browser.settings.userAgentString = "Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13"
        browser.settings.defaultTextEncodingName = "utf-8"
        browser.settings.loadWithOverviewMode = true
//        browser.settings.useWideViewPort = true //don't set this, it will scale the screen.
        browser.settings.javaScriptEnabled = true
        browser.settings.javaScriptCanOpenWindowsAutomatically = true
        browser.settings.loadsImagesAutomatically = true
        browser.settings.allowContentAccess = true
        browser.settings.allowFileAccess = true
        browser.settings.allowFileAccessFromFileURLs = true
        browser.settings.allowUniversalAccessFromFileURLs = true
        browser.settings.databaseEnabled = true
        browser.settings.domStorageEnabled = true

        browser.settings.setAppCacheEnabled(true)
        browser.settings.setGeolocationEnabled(true)

        browser.settings.setSupportZoom(true)
        browser.settings.builtInZoomControls = true
        browser.settings.displayZoomControls = true

        browser.settings.mediaPlaybackRequiresUserGesture = true
        browser.settings.saveFormData = true

        browser.requestFocusFromTouch()

        browser.setInitialScale((100 * ratio).toInt())

        browser.webViewClient = WebViewClient()
        browser.webChromeClient = WebChromeClient()


        try {
            // load the url
            browser.loadUrl("file:///android_asset/index.html")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && browser.canGoBack()) {
            browser.goBack()
            return true
        }

        if (keyCode == KeyEvent.KEYCODE_FORWARD && browser.canGoForward()) {
            browser.goForward()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        browser.visibility = View.GONE
    }
}
