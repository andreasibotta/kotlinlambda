package com.kotlinlambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class App : RequestHandler<HandlerInput, HandlerOutput> {
    val translator : PirateTranslator =   DefaultPirateTranslator()

    override fun handleRequest(input: HandlerInput?, context: Context?): HandlerOutput {
        input?.let {
            return HandlerOutput(it.message, translator.translate(it.message))
        }
        return HandlerOutput("", "");
    }
}