package br.com.felipempantoja.mailsender.config

import br.com.felipempantoja.mailsender.template.TemplateService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
class MailTemplateAutoConfiguration {

    @Bean
    fun htmlTemplateEngine(): TemplateEngine {
        val templateEngine = SpringTemplateEngine()

        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.order = 2
        templateResolver.prefix = "/templates/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = "UTF-8"
        templateResolver.isCacheable = false

        templateEngine.addTemplateResolver(templateResolver)
        templateEngine.addDialect(Java8TimeDialect())
        return templateEngine
    }

    @Bean
    fun templateService(templateEngine: TemplateEngine) = TemplateService(templateEngine)
}