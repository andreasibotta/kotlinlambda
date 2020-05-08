package com.kotlinlambda


import kotlin.test.Test
import kotlin.test.assertEquals

class AppTest {
    @Test fun testAppHasAGreeting() {
        val classUnderTest = App()
        val input = HandlerInput()
        input.message = "Hello"
        val expected = HandlerOutput("Hello there", "Ahoy!")
        var output = classUnderTest.handleRequest(input, null)
        assertEquals(expected, output)
    }
}
