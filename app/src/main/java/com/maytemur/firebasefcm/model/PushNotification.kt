package com.maytemur.firebasefcm.model

data class PushNotification(
    val data: NotificationData,
    val to: String
)