package com.scoqu.lasw.egasy.domain.use_case.user

import android.content.Context

interface UserInteractor {

    fun isNetworkAvailable(context: Context) : Boolean
    fun isUser(context: Context) : Boolean
}