package com.wafflestudio.alert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WaffleAlertApplication

fun main(args: Array<String>) {
    runApplication<WaffleAlertApplication>(*args)
}
