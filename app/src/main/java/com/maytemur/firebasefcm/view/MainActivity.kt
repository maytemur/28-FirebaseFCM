package com.maytemur.firebasefcm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.maytemur.firebasefcm.R
import com.maytemur.firebasefcm.model.NotificationData
import com.maytemur.firebasefcm.model.PushNotification
import com.maytemur.firebasefcm.service.RetrofitObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val topic= "/topics/genelduyurular"
    private lateinit var dbToken: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbToken= FirebaseFirestore.getInstance()

        FirebaseMessaging.getInstance().subscribeToTopic(topic) // channel topice mesaj göndermenin haricinde birde cihaza
        //telefona vs mesaj gönderme var oda cihazın token'ı alınarak yapılıyor. Önceden FirebaseInstanceId ile alınıyormus
        //artık firebaseMessaging.gerInstance ile alınıyor
        val cihazToken=FirebaseMessaging.getInstance().token.addOnSuccessListener {

            val dataMap= hashMapOf<String,String>()
            dataMap.put("token",it)
            dataMap.put("kullanici_adi","Galaxy Nexus Api23")

            dbToken.collection("Kullanici").add(dataMap).addOnSuccessListener {

            }

        }
        //println("Token 4.65inch : $cihazToken")
    }

    fun yolla(view: View) {
        val baslik= baslik_text.text.toString()
        val mesaj= mesaj_text.text.toString()
        if(baslik!="" && mesaj!="") {
            val data= NotificationData(baslik,mesaj)
            val notification = PushNotification(data,topic)
            notificationYolla(notification)
        }
    }

    private fun notificationYolla(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cevap=RetrofitObject.api.postNotification(notification)
                if (cevap.isSuccessful){
                    println(cevap)
                }else {
                    println(cevap.errorBody().toString())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }

}