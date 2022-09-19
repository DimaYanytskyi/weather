package com.scoqu.lasw.egasy.presentation.loading

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.scoqu.lasw.egasy.domain.use_case.user.UserInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoadViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
) : ViewModel(){

    private val _adId = MutableLiveData<String>()

    private val _param = MutableLiveData<String>()
    val param : LiveData<String> = _param

    private val _link = MutableLiveData<String>()
    val link : LiveData<String> = _link

    fun checkUser(context: Context) : Boolean{
        return userInteractor.isUser(context)
    }

    fun checkInternet(context: Context) : Boolean{
        return userInteractor.isNetworkAvailable(context)
    }

    fun initAppsFlyer(context: Context){
        AppsFlyerLib.getInstance()
            .init("iErUe7D54ASvZbQA7FhvWY", object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                    val jsonObject = JSONObject(data as Map<*, *>)
                    var nmg: String = jsonObject.optString("campaign")
                    if (nmg.isEmpty()) nmg = jsonObject.optString("c")

                    viewModelScope.launch(Dispatchers.Main) {
                        _link.value = (
                                    _param.value + "?lsd=" + nmg
                                    + "&dvs=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context)
                                    + "&rfvew=" + _adId.value
                                ).trim()
                    }
                    AppsFlyerLib.getInstance().unregisterConversionListener()
                }
                override fun onConversionDataFail(error: String?) {
                    viewModelScope.launch(Dispatchers.Main) {
                        _link.value = "null"
                    }
                    AppsFlyerLib.getInstance().unregisterConversionListener()
                }

                override fun onAppOpenAttribution(data: MutableMap<String, String>?) {}
                override fun onAttributionFailure(error: String?) {}
            } , context)
        AppsFlyerLib.getInstance().start(context)
        AppsFlyerLib.getInstance().enableFacebookDeferredApplinks(true)
    }

    fun getFirebaseParam(){
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val str = remoteConfig.getString("clacosm")
                    _param.value = JSONObject(str).optString("cossic5")
                } else {
                    _link.value = "null"
                }
            }
            .addOnFailureListener {
                _link.value = "null"
            }
    }

    fun getAdId(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val adInfo =
                AdvertisingIdClient.getAdvertisingIdInfo(context)
            viewModelScope.launch(Dispatchers.Main) {
                _adId.value = adInfo.id
            }
        }
    }
}