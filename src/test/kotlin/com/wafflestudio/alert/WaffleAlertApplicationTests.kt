package com.wafflestudio.alert

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

// Testcontainers MySQL 위에서 전체 컨텍스트(JPA/Flyway 포함)가 로딩되는지 검증.
@SpringBootTest
@Import(MySQLTestContainerConfig::class)
class WaffleAlertApplicationTests {
    @Test
    fun contextLoads() {
    }
}
