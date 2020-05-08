package com.kotlinlambda

import kotlin.test.Test
import kotlin.test.assertEquals

class PirateTranslatorTest {
    @Test fun testPirateTranslator() {
        val translator : PirateTranslator = DefaultPirateTranslator()

        assertEquals("Ahoy!, I am Captain Jack Sparrow", translator.translate("Hello, I am Captain Jack Sparrow"))
        assertEquals("Aye!", translator.translate("Yes"))
    }
}