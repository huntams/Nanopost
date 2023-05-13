package com.example.homework2.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.homework2.R
import com.example.homework2.domain.CreatePostUseCase
import com.example.homework2.domain.GetContentUriUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostService : Service(), CoroutineScope by MainScope() {


    companion object {
        private const val ARG_TEXT_KEY = "ARG_TEXT_KEY"
        private const val ARG_IMAGE_KEY = "ARG_IMAGE_KEY"
        private const val CHANNEL_ID = "Create Notification"


        //вынести это в strings.xml
        private const val CHANNEL_NAME = "Загрузка ресурсов"
        private const val NOTIFICATION_ID = 123

        private const val ACTION_CREATE_POST = "ACTION_CREATE_POST"
        fun newIntent(context: Context, text: String?, list: List<Uri>?) =
            Intent(context, CreatePostService::class.java).apply {
                action = ACTION_CREATE_POST
                putExtra(ARG_TEXT_KEY, text)
                putParcelableArrayListExtra(ARG_IMAGE_KEY, ArrayList(list))
            }
    }

    @Inject
    lateinit var getContentUriUseCase: GetContentUriUseCase

    @Inject
    lateinit var createPostUseCase: CreatePostUseCase

    override fun onBind(p0: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            startForeground(NOTIFICATION_ID,createNotification())
            if (it.action == ACTION_CREATE_POST) {
                val text = it.extras?.getString(ARG_TEXT_KEY)
                val images = it.extras?.getParcelableArrayList<Uri>(ARG_IMAGE_KEY)?.map { uri ->
                    getContentUriUseCase(uri)
                }
                launch {
                    createPostUseCase.execute(text, images?.filterNotNull())
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification() : Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(CHANNEL_NAME)
            .setSmallIcon(R.drawable.ic_add_photo)
            .setProgress(0, 0, true)
            .build()
    }

    private fun createNotificationChannel(){
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_LOW
        ).setName(CHANNEL_NAME)
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}