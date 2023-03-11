package com.maytemur.firebasefcm.service


import com.maytemur.firebasefcm.model.PushNotification
import com.maytemur.firebasefcm.util.Constants.Companion.CONTENT_TYPE
import com.maytemur.firebasefcm.util.Constants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationService {
    @Headers("Authorization: key=$SERVER_KEY","Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(   //couroutine lerle çalışırken gerekli
        @Body notification: PushNotification

    ) : Response<ResponseBody>
}