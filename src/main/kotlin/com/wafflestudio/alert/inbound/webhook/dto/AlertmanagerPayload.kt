package com.wafflestudio.alert.inbound.webhook.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/** Alertmanager webhook(v4) payload. 그룹 단위로 온다 — alerts[]는 묶인 얼러트 목록. */
@JsonIgnoreProperties(ignoreUnknown = true)
data class AlertmanagerPayload(
    val version: String? = null,
    val groupKey: String? = null,
    val status: String? = null,
    val receiver: String? = null,
    val groupLabels: Map<String, String> = emptyMap(),
    val commonLabels: Map<String, String> = emptyMap(),
    val commonAnnotations: Map<String, String> = emptyMap(),
    val externalURL: String? = null,
    val alerts: List<AlertmanagerAlert> = emptyList(),
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AlertmanagerAlert(
    val status: String,
    val labels: Map<String, String> = emptyMap(),
    val annotations: Map<String, String> = emptyMap(),
    val startsAt: String? = null,
    val endsAt: String? = null,
    val generatorURL: String? = null,
    val fingerprint: String,
)
