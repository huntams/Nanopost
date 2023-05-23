package com.example.homework2.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.homework2.R
import com.example.homework2.data.PrefsStorage
import com.example.homework2.domain.CreatePostUseCase
import com.example.homework2.domain.EditProfileUseCase
import com.example.homework2.domain.GetContentUriUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileService : Service(), CoroutineScope by MainScope() {


    companion object {
        private const val ARG_NAME_KEY = "ARG_TEXT_KEY"
        private const val ARG_BIO_KEY = "ARG_TEXT_KEY"
        private const val ARG_IMAGE_KEY = "ARG_IMAGE_KEY"
        private const val CHANNEL_ID = "Create Notification"


        //вынести это в strings.xml
        private const val CHANNEL_NAME = "Загрузка ресурсов"
        private const val NOTIFICATION_ID = 123

        private const val ACTION_EDIT_PROFILE = "ACTION_EDIT_PROFILE"
        fun newIntent(context: Context, displayName: String?, bio: String?, avatar: Uri?) =
            Intent(context, CreatePostService::class.java).apply {
                action = ACTION_EDIT_PROFILE
                putExtra(ARG_NAME_KEY, displayName)
                putExtra(ARG_BIO_KEY, bio)
                putExtra(ARG_IMAGE_KEY, avatar)
                //putParcelableArrayListExtra(ARG_IMAGE_KEY, ArrayList(list))

            }
    }


    @Inject
    lateinit var prefs: PrefsStorage

    @Inject
    lateinit var getContentUriUseCase: GetContentUriUseCase

    @Inject
    lateinit var editProfileUseCase: EditProfileUseCase

    override fun onBind(p0: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            startForeground(NOTIFICATION_ID, createNotification())
            if (it.action == ACTION_EDIT_PROFILE) {
                val displayName = it.extras?.getString(ARG_NAME_KEY)
                val bio = it.extras?.getString(ARG_BIO_KEY)
                val images = it.extras?.getParcelable<Uri>(ARG_IMAGE_KEY)?.let { uri ->
                    getContentUriUseCase(uri)
                }
                launch {
                    editProfileUseCase.execute(
                        profileId = prefs.username.toString(),
                        displayName = displayName,
                        bio = bio,
                        images
                    )
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(): Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(CHANNEL_NAME)
            .setSmallIcon(R.drawable.ic_add_photo)
            .setProgress(0, 0, true)
            .build()
    }

    private fun createNotificationChannel() {
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