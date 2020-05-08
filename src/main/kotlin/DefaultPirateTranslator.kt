package com.kotlinlambda

class DefaultPirateTranslator : PirateTranslator {
    val replacements = mapOf("Hello" to "Ahoy!", "Yes" to "Aye!", "Yes, Captain!" to "Aye Aye!")

    override fun translate(message: String): String {
        var result = message
        replacements.forEach { k, v -> result =    result.replace(k, v) }
        return result
    }
}