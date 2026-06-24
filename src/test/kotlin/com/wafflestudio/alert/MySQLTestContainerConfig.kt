package com.wafflestudio.alert

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

// 테스트용 MySQL 컨테이너. @ServiceConnection 이 datasource(url/user/password)를
// 스프링에 자동 주입하므로 application.yml 에 접속정보를 둘 필요가 없다.
@TestConfiguration
class MySQLTestContainerConfig {
    @Bean
    @ServiceConnection
    fun mysqlContainer(): MySQLContainer<*> =
        MySQLContainer(DockerImageName.parse("mysql:8.4"))
            .withDatabaseName("waffle_alert")
}
