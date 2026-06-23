package com.wafflestudio.alert.domain.service

// TODO: 두 입력 경로(webhook / OCI scheduler)의 합류점
//   AlertEvent -> IncidentService.upsert -> AlertEventLog 기록
//   -> RoutingPolicy로 채널/팀 결정 -> NotificationPort 전송
