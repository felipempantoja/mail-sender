package br.com.felipempantoja.mailsender.mail

import br.com.felipempantoja.mailsender.template.Template
import br.com.felipempantoja.mailsender.template.TemplateService
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import javax.mail.internet.MimeMessage

class MailService(
        private val templateService: TemplateService,
        private val mailSender: JavaMailSender,
        private val mailEnvProperties: MailEnvProperties) {

    /**
     * Send email using a custom template.
     */
    fun send(template: Template) {
        try {
//            log.debug("Preparando envio de email para {}: {}", emailEnvProperties.getEquipe(), template.getTitle());
            val messagePreparator = MimeMessagePreparator { mimeMessage: MimeMessage ->
                val messageHelper = MimeMessageHelper(mimeMessage)
                messageHelper.setFrom(mailEnvProperties.from)
                messageHelper.setTo(mailEnvProperties.to)
                messageHelper.setSubject(mailEnvProperties.titlePrefix + template.getTitle())
                messageHelper.setText(templateService.process(template), true)
            }
            mailSender.send(messagePreparator)
//            log.debug("Email enviado com sucesso!");
        } catch (e: Exception) {
//            log.error("O email nao pode ser enviado. Motivo: {}", e.getMessage());
        }
    }
}