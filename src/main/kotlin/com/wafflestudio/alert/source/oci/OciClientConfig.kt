package com.wafflestudio.alert.source.oci

import com.oracle.bmc.ConfigFileReader
import com.oracle.bmc.Region
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider
import com.oracle.bmc.usageapi.UsageapiClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OciClientConfig {
    @Bean
    fun ociAuthProvider(props: OciProperties): BasicAuthenticationDetailsProvider =
        when (props.authType) {
            OciProperties.AuthType.CONFIG_FILE -> {
                val configFile = ConfigFileReader.parse(props.configFilePath, props.profile)
                ConfigFileAuthenticationDetailsProvider(configFile)
            }

            OciProperties.AuthType.INSTANCE_PRINCIPAL ->
                InstancePrincipalsAuthenticationDetailsProvider.builder().build()
        }

    @Bean
    fun usageapiClient(
        provider: BasicAuthenticationDetailsProvider,
        props: OciProperties,
    ): UsageapiClient =
        UsageapiClient.builder()
            .region(Region.fromRegionId(props.region))
            .build(provider)
}
