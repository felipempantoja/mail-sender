package br.com.felipempantoja.mailsender.config

import br.com.felipempantoja.mailsender.mail.WebMailExceptionHandler
import br.com.felipempantoja.mailsender.mail.MailEnvProperties
import br.com.felipempantoja.mailsender.mail.MailService
import br.com.felipempantoja.mailsender.template.TemplateService
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.PropertySource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

// olhar: http://dolszewski.com/spring/sending-html-mail-with-spring-boot-and-thymeleaf/

/**
 * TODO falta resolver a questao do cliente ter que informar os properties
 */
@Configuration
@PropertySource("classpath:mail.yml")
@EnableAspectJAutoProxy
@EnableConfigurationProperties(MailEnvProperties::class)
class MailSenderAutoConfiguration {

//    @Bean
    fun javaMailSender(): JavaMailSender? {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "my.gmail@gmail.com"
        mailSender.password = "password"

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"
        return mailSender
    }

    @Bean
    fun mailService(
            templateService: TemplateService,
            mailSender: JavaMailSender,
            mailEnvProperties: MailEnvProperties
    ) = MailService(templateService, mailSender, mailEnvProperties)

    @Bean
//    @ConditionalOnBean(ExceptionMailList::class)
    fun webMailExceptionHandler(exceptionMailList: ExceptionMailList) = WebMailExceptionHandler(exceptionMailList)
}