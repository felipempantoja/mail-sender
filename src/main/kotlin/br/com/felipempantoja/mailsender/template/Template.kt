package br.com.felipempantoja.mailsender.template

import org.thymeleaf.context.Context

interface Template {

    fun getTitle(): String

    fun getTemplateName(): String?

    fun getMetadata(): MutableMap<String, Any>

    @JvmDefault
    fun getContext(): Context {
        val ctx = Context()
        val metadata = getMetadata()
        metadata["title"] = getTitle()
        for ((key, value) in metadata) {
            ctx.setVariable(key, value)
        }
        return ctx
    }
}