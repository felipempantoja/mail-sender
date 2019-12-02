package br.com.felipempantoja.mailsender.autoconfiguration

import br.com.felipempantoja.mailsender.config.MailSenderAutoConfiguration
import br.com.felipempantoja.mailsender.config.GsiMailBeanRegistrar
import br.com.felipempantoja.mailsender.config.MailTemplateAutoConfiguration
import org.springframework.context.annotation.Import
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(value = [GsiMailBeanRegistrar::class, MailSenderAutoConfiguration::class, MailTemplateAutoConfiguration::class])
annotation class EnableMailSenderAutoConfiguration(val sendOnExceptions: Array<KClass<out Exception>> = [])