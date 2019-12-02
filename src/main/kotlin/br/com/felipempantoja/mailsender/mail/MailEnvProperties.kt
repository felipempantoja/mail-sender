package br.com.felipempantoja.mailsender.mail

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "mail")
class MailEnvProperties {
    lateinit var from: String
    lateinit var to: Array<String>
    lateinit var titlePrefix: String
}

//@ConstructorBinding
//@ConfigurationProperties(prefix = "gsi.mail")
//class MailEnvProperties(
//        val from: String,
//        val to: Array<String>,
//        val titlePrefix: String
//)