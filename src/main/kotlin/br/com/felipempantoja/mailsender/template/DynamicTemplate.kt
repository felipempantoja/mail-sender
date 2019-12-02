package br.com.felipempantoja.mailsender.template

import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

interface DynamicTemplate : Template {

    fun prepareFields(dynamicFields: DynamicFieldsConfig?)

    @JvmDefault
    override fun getMetadata(): MutableMap<String, Any> {
        val fieldsConfig = DynamicFieldsConfig()
        prepareFields(fieldsConfig)
        val metadata: MutableMap<String, Any> = HashMap()
        metadata["fields"] = fieldsConfig.fields
        if (fieldsConfig.stacktrace != null) {
            metadata["stacktrace"] = fieldsConfig.stacktrace!!
        }
        return metadata
    }

    @JvmDefault
    override fun getTemplateName(): String? {
        return "dynamic-template"
    }

    class DynamicFieldsConfig {
        val fields: MutableMap<String, Any> = HashMap()
        var stacktrace: String? = null

        fun addString(key: String, value: String): DynamicFieldsConfig {
            fields[key] = value
            return this
        }

        fun addInteger(key: String, value: Int): DynamicFieldsConfig {
            fields[key] = value
            return this
        }

        fun addDateTime(key: String, value: LocalDateTime?): DynamicFieldsConfig {
            if (value != null) {
                fields[key] = value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            }
            return this
        }

        fun addException(value: Throwable?): DynamicFieldsConfig {
            if (value != null) {
                val sw = StringWriter()
                value.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                stacktrace = exceptionAsString
            }
            return this
        }
    }
}