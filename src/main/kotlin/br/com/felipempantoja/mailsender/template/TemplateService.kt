package br.com.felipempantoja.mailsender.template

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.*

//@Service
class TemplateService(private val templateEngine: TemplateEngine) {

    fun process(template: Template): String {
        try {
            val context: Context = template.getContext()

            // shared variables alongside templates
            context.setVariable("locale", Locale("pt", "BR"))
            context.setVariable("fullDateFormat", "dd 'de' MMMM 'de' yyyy")
            context.setVariable("dateTimeFormat", "dd/MM/yyyy HH:mm")

            return templateEngine.process(template.getTemplateName(), context)
        } catch (e: Exception) {
            throw TemplateProcessException("Error processing GSI template", e)
        }
    }
}