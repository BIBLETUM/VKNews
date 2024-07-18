package com.example.vknews.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.vknews.presentation.theme.Test
import com.example.vknews.presentation.theme.VKNewsTheme
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.onetap.compose.onetap.sheet.OneTapBottomSheet
import com.vk.id.onetap.compose.onetap.sheet.rememberOneTapBottomSheetState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        VKID.init(this)
//        val vkAuthCallback = object : VKIDAuthCallback {
//
//            override fun onAuth(accessToken: AccessToken) {
//                val token = accessToken.token
//                Log.d("adada", "logged")
//            }
//
//            override fun onFail(fail: VKIDAuthFail) {
//                when (fail) {
//                    is VKIDAuthFail.Canceled -> { /*...*/ }
//                    else -> {
//                        //...
//                    }
//                }
//            }
//        }
//        VKID.instance.authorize(this@MainActivity, vkAuthCallback)
        setContent {
            VKNewsTheme {

               MainScreen()
            }
        }
    }
}

@Composable
fun VKIDAuthScreen() {
    var token: AccessToken? by remember { mutableStateOf(null) }
    val bottomSheetState = rememberOneTapBottomSheetState()
    OneTapBottomSheet(
        state = bottomSheetState,
        onAuth = { _, authToken -> token = authToken },
        serviceName = "VkNewsClient"
    )
    bottomSheetState.show()
}