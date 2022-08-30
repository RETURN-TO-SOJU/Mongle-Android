package com.won983212.mongle.debug.test

class MainTestActivity : BaseTestActivity() {
    override val listItems: Array<IScreenInfo> = arrayOf(
        ActivityInfo("View 테스트", ViewTestActivity::class.java),
        ActivityInfo("API 테스트", ApiTestActivity::class.java)
    )
}