package br.com.felipempantoja.mailsender.mail

import br.com.felipempantoja.mailsender.config.ExceptionMailList
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnBean(ExceptionMailList::class)
@RestControllerAdvice
class WebMailExceptionHandler(private val exceptionMailList: ExceptionMailList) {

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): ResponseEntity<Any> {
        println(exceptionMailList)
        throw ex
    }
}