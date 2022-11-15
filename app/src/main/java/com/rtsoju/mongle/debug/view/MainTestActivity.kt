package com.rtsoju.mongle.debug.view

class MainTestActivity : BaseTestActivity() {
    override val listItems: Array<IScreenInfo> = arrayOf(
        ActivityInfo("View 테스트", ViewTestActivity::class.java),
        ActivityInfo("API 테스트", ApiTestActivity::class.java),
        ManualInfo("알림 테스트") {
        }
    )
}