package com.maytemur.firebasefcm.service

import com.maytemur.firebasefcm.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitObject {
    companion object {
        private val retrofit by lazy { //initiliaze edene kadar hafızada yer kaplamayan
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api by lazy {
            retrofit.create(NotificationService::class.java)
        }
    }
}