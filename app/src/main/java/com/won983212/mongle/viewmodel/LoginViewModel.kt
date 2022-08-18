package com.won983212.mongle.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.won983212.mongle.common.util.SingleLiveEvent
import com.won983212.mongle.common.util.asLiveData

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _eventLoginKakao = SingleLiveEvent<Unit>()
    val eventLoginKakao = _eventLoginKakao.asLiveData()

    fun loginWithKakao() {
        // TODO (DEBUG) 나중에 실제 로그인하도록 수정
        _eventLoginKakao.call()
        /*
        val context = getApplication<Application>().applicationContext
        val client = UserApiClient.instance
        if (!client.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    Log.e("KakaoLogin", error.toString())
                    Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    _onLoginKakao.call()
                }
            }
        } else {
            client.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("KakaoLogin", error.toString())
                    Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    _onLoginKakao.call()
                }
            }
        }*/
    }
}