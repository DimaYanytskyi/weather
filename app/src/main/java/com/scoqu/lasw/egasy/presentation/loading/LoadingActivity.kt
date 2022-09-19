package com.scoqu.lasw.egasy.presentation.loading

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.onesignal.OneSignal
import com.scoqu.lasw.egasy.R
import com.scoqu.lasw.egasy.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity : AppCompatActivity() {
    private val viewModel: LoadViewModel by viewModels()
    private lateinit var spref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        OneSignal.initWithContext(this@LoadingActivity)
        OneSignal.setAppId("c76f3472-7eaa-4a91-8a4f-5c2a197bec34")

        spref = getSharedPreferences("CLASSIC", MODE_PRIVATE)

        if(spref.getBoolean("sr", true)){
            if(viewModel.checkInternet(this)){
                if(viewModel.checkUser(this)){
                    spref
                        .edit()
                        .putBoolean("sr", false)
                        .apply()
                    startGame()
                } else {
                    initMainView()
                }
            } else {
                spref
                    .edit()
                    .putBoolean("sr", false)
                    .apply()
                startGame()
            }
        } else {
            if(spref.getString("pr", "")!!.isNotEmpty()){
                startPlay()
            } else {
                startGame()
            }
        }
    }

    private fun initMainView(){
        viewModel.getAdId(this)
        viewModel.getFirebaseParam()
        viewModel.param.observe(this) {
            viewModel.initAppsFlyer(this@LoadingActivity)
        }
        viewModel.link.observe(this) {
            if(it == "null" || it.isEmpty() || it.equals("912436")){
                spref
                    .edit()
                    .putBoolean("sr", false)
                    .apply()
                startGame()
            } else {
                spref
                    .edit()
                    .putBoolean("sr", false)
                    .putString("pr", it)
                    .apply()
                startPlay()
            }
        }
    }

    private fun startPlay() {
        startActivity(Intent(this@LoadingActivity, ClassicActivity::class.java))
        finish()
    }

    private fun startGame(){
        startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
        finish()
    }
}