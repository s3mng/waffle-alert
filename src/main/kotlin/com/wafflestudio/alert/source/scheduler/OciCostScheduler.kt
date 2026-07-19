package com.wafflestudio.alert.source.scheduler

import com.wafflestudio.alert.domain.evaluator.OciCostEvaluator
import com.wafflestudio.alert.domain.service.AlertIngestionService
import com.wafflestudio.alert.source.oci.OciCostAdapter
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OciCostScheduler(
    private val adapter: OciCostAdapter,
    private val evaluator: OciCostEvaluator,
    private val ingestionService: AlertIngestionService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    fun checkSpike() {
        try {
            val daily = adapter.fetchDailyCosts(14)
            val event = evaluator.evaluateSpike(daily) ?: return  // 정상 범위면 null -> 알림 없음
            ingestionService.ingest(event)
        } catch (e: Exception) {
            log.error("OCI cost spike check failed", e)
        }
    }

    /** 매주 월요일 아침 9시(KST) - 최근 3주 주별 + 3달 월별 비용 요약 발송 */
    @Scheduled(cron = "0 0 9 * * MON", zone = "Asia/Seoul")
    fun sendWeeklySummary() {
        try {
            val weekly = adapter.fetchWeeklyCosts(3)
            val monthly = adapter.fetchMonthlyCosts(3)
            val event = evaluator.buildWeeklySummary(weekly, monthly)
            ingestionService.ingest(event)
        } catch (e: Exception) {
            log.error("OCI cost weekly summary failed", e)
        }
    }
}
