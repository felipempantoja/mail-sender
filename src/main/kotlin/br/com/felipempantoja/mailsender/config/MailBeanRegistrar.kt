package br.com.felipempantoja.mailsender.config

import br.com.felipempantoja.mailsender.autoconfiguration.EnableMailSenderAutoConfiguration
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata

class GsiMailBeanRegistrar : ImportBeanDefinitionRegistrar {

    override fun registerBeanDefinitions(metadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        val map = metadata.getAnnotationAttributes(EnableMailSenderAutoConfiguration::class.java.name)
        val clazz = (map!!["sendOnExceptions"] as Array<*>)[0] as Class<*>
        val inst = clazz.newInstance()
        if (inst is Exception) {
            val beanDefinition = GenericBeanDefinition()
            beanDefinition.setBeanClass(ExceptionMailList::class.java)
            beanDefinition.propertyValues.addPropertyValue("exceptions", inst)
            registry.registerBeanDefinition("exceptionMailList", beanDefinition)
        }
    }
}

class ExceptionMailList {
    lateinit var exceptions: Array<Throwable>
}