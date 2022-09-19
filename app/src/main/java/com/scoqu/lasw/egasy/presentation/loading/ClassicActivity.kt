package com.scoqu.lasw.egasy.presentation.loading

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.scoqu.lasw.egasy.R
import com.scoqu.lasw.egasy.databinding.ActivityClassicBinding
import java.io.File
import java.io.IOException

class ClassicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClassicBinding
    lateinit var prefs: SharedPreferences
    private var mCameraPhotoPath: String? = null
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null

    val INPUT_FILE_REQUEST_CODE = 88

    @SuppressLint("AddJavascriptInterface", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("CLASSIC", MODE_PRIVATE)
        val point = prefs.getString("pr", "")
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setSettings()

        binding.classic.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return overrideUrl(view!!, url!!)
            }

            @TargetApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest
            ): Boolean {
                return overrideUrl(view!!, request.url.toString())
            }

            fun overrideUrl(view: WebView, url: String): Boolean {
                return if (url.startsWith("mailto:")) {
                    val i = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                    startActivity(i)
                    true
                } else if (url.startsWith("tg:") || url.startsWith("https://t.me") || url.startsWith(
                        "https://telegram.me"
                    )
                ) {
                    try {
                        val result = view.hitTestResult
                        val data = result.extra
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data))
                        view.context.startActivity(intent)
                    } catch (ex: Exception) {
                    }
                    true
                } else {
                    false
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (prefs.getInt("npr", 0) == 0) {
                    prefs.edit().putString("pr", url).apply()
                    prefs.edit().putInt("npr", 1).apply()
                    CookieManager.getInstance().flush()
                }
                CookieManager.getInstance().flush()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }
        }

        binding.classic.webChromeClient = object : WebChromeClient(){

            @RequiresApi(api = Build.VERSION_CODES.M)
            fun checkPermission() {
                ActivityCompat.requestPermissions(
                    this@ClassicActivity, arrayOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                    ),
                    1
                )
            }

            override fun onShowFileChooser(
                webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                val permissionStatus =
                    ContextCompat.checkSelfPermission(this@ClassicActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {

                    mFilePathCallback?.onReceiveValue(null)
                    mFilePathCallback = filePathCallback

                    var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (takePictureIntent!!.resolveActivity(packageManager) != null) {
                        var photoFile: File? = null
                        try {
                            photoFile = createImageFile()
                            takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                        } catch (ex: IOException) {
                        }
                        if (photoFile != null) {
                            mCameraPhotoPath = "file:" + photoFile.absolutePath
                            takePictureIntent!!.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile)
                            )
                        } else {
                            takePictureIntent = null
                        }
                    }
                    val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                    contentSelectionIntent.type = "image/*"
                    val intentArray: Array<Intent?> = takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
                    val chooser = Intent(Intent.ACTION_CHOOSER)
                    chooser.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                    chooser.putExtra(Intent.EXTRA_TITLE, "Take a photo")
                    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                    startActivityForResult(chooser, INPUT_FILE_REQUEST_CODE)
                    return true
                } else checkPermission()
                return false
            }

            @Throws(IOException::class)
            private fun createImageFile(): File {
                var imageStorageDir = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "DirectoryNameHere"
                )
                if (!imageStorageDir.exists()) imageStorageDir.mkdirs()
                imageStorageDir = File(
                    imageStorageDir.toString() + File.separator + "Pic_" + System.currentTimeMillis()
                        .toString() + ".jpg"
                )
                return imageStorageDir
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.progressBar.isActivated = true
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.progress = newProgress
                if (newProgress == 100) {
                    binding.progressBar.visibility = View.GONE
                    binding.progressBar.isActivated = false
                }
            }
        }

        binding.classic.setDownloadListener { url, _, _, _, _ ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        if (point != null) {
            binding.classic.loadUrl(point)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.classic.canGoBack()) {
            binding.classic.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setSettings(){
        binding.classic.apply {
            settings.apply {
                userAgentString = binding.classic.settings.userAgentString
                javaScriptEnabled = true
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                setAppCacheEnabled(true)
                cacheMode = WebSettings.LOAD_NO_CACHE
                domStorageEnabled = true
                databaseEnabled = true
                setSupportZoom(false)
                domStorageEnabled = true
                domStorageEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                loadWithOverviewMode = true
                useWideViewPort = true
                javaScriptCanOpenWindowsAutomatically = true
                pluginState = WebSettings.PluginState.ON
                savePassword = true
            }
        }
        binding.classic.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding.classic.requestFocus(View.FOCUS_DOWN)
        binding.classic.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.acceptCookie()
        cookieManager.setAcceptThirdPartyCookies(binding.classic, true)
        cookieManager.flush()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        var results: Array<Uri>? = null
        if (resultCode == RESULT_OK) {
            if (data == null || data.data == null) {
                if (mCameraPhotoPath != null) {
                    results = arrayOf(Uri.parse(mCameraPhotoPath))
                }
            } else {
                val dataString = data.dataString
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }
        mFilePathCallback!!.onReceiveValue(results)
        mFilePathCallback = null
    }


    override fun onPause() {
        super.onPause()
        CookieManager.getInstance().flush()
    }

    override fun onResume() {
        super.onResume()
        CookieManager.getInstance().flush()
    }

    override fun onBackPressed() {
        if (binding.classic.canGoBack()) {
            binding.classic.goBack()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Exit")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Do you really want to exit application?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ -> finish() }
                .create()
                .show()
        }
    }
}