package com.rtsoju.mongle.domain.model

data class StatisticsResult(
    /** 행복 점수를 나타냄. 주별이면 7개, 월별이면 그 월의 week개, 연별이면 12개 */
    val scores: List<Score>,
    /** 감정별 문장 총 개수. Emotion.ordinary 위치에 그 감정 개수가 저장됨 */
    val emotionCount: List<Int>
) {
    data class Score(
        val label: String,
        val score: Float?
    )
}