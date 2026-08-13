package com.wafflestudio.alert.inbound.webhook

import com.wafflestudio.alert.domain.service.AlertIngestionService
import com.wafflestudio.alert.inbound.webhook.dto.AlertmanagerPayload
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Alertmanager webhook 수신. payload의 alerts[]를 순회하며 각 alert를
 * AlertEvent로 변환해 AlertIngestionService에 위임한다.
 */
@RestController
class AlertmanagerWebhookController(
    private val alertIngestionService: AlertIngestionService,
    private val mapper: AlertmanagerEventMapper,
) {
    @PostMapping("/api/v1/alerts/webhook")
    fun receive(
        @RequestBody payload: AlertmanagerPayload,
    ) {
        payload.alerts.forEach { alert ->
            alertIngestionService.ingest(mapper.toAlertEvent(alert))
        }
    }
}
