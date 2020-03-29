package org.gradle.bot.aop.logging

import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Module
import com.google.inject.Singleton
import com.google.inject.matcher.AbstractMatcher
import com.google.inject.matcher.Matchers
import java.lang.RuntimeException
import java.lang.reflect.Array
import java.lang.reflect.Method
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger

interface testInterface {
    fun testMethod(arg1: String, arg2: Int): String
    fun testDoNoting()
    fun testException()
}

@Singleton
open class testInterfaceImpl @Inject constructor() : testInterface {

    @DebugLog(LogLevel.INFO)
    override fun testDoNoting() {
    }

    @DebugLog(LogLevel.WARN)
    override fun testMethod(arg1: String, arg2: Int): String = "$arg1$arg2"

    @DebugLog
    override fun testException() {
        throw RuntimeException()
    }
}

@ExtendWith(MockitoExtension::class)
class TestDebugLog {

    @Mock
    lateinit var logger: Logger

    @Captor
    lateinit var stringCaptor: ArgumentCaptor<String>

    @Captor
    lateinit var argumentsCaptor: ArgumentCaptor<Any>

    lateinit var injector: Injector

    private val debugLogMethodInterceptor = DebugLogMethodInterceptor()

    lateinit var instance: testInterface

    @BeforeEach
    fun setup() {
        injector = Guice.createInjector(Module {
            it.bind(testInterface::class.java).to(testInterfaceImpl::class.java)
            it.bindInterceptor(Matchers.any(), object : AbstractMatcher<Method>() {
                override fun matches(t: Method): Boolean {
                    return t.isAnnotationPresent(DebugLog::class.java) && !t.isSynthetic
                }
            }, debugLogMethodInterceptor)
        })
        instance = injector.getInstance(testInterface::class.java)
        val instanceLogger = debugLogMethodInterceptor.javaClass.getDeclaredField("logger")
        instanceLogger.isAccessible = true
        instanceLogger.set(debugLogMethodInterceptor, logger)
    }

    @Test
    fun `test aop warn log with args and return`() {
        val result = instance.testMethod("hello", 1)

        verify(logger, times(2)).warn(stringCaptor.capture(),
                argumentsCaptor.capture())

        Assertions.assertTrue(stringCaptor.allValues[0].startsWith("Entering method"))
        Assertions.assertTrue(stringCaptor.allValues[1].startsWith("Exiting method"))

        // parameter(s)
        Assertions.assertNotNull((argumentsCaptor.allValues[0] as kotlin.Array<*>)[2])
        // method
        Assertions.assertTrue((argumentsCaptor.allValues[0] as kotlin.Array<*>)[0] == (argumentsCaptor.allValues[1] as kotlin.Array<*>)[0])
        // class
        Assertions.assertTrue((argumentsCaptor.allValues[0] as kotlin.Array<*>)[1] == (argumentsCaptor.allValues[1] as kotlin.Array<*>)[1])
        // spent
        Assertions.assertNotNull((argumentsCaptor.allValues[1] as kotlin.Array<*>)[2])
        // return
        Assertions.assertEquals(result, (argumentsCaptor.allValues[1] as kotlin.Array<*>)[3])
    }

    @Test
    fun `test aop info log do nothing`() {
        instance.testDoNoting()

        verify(logger, times(2)).info(stringCaptor.capture(),
                argumentsCaptor.capture())

        Assertions.assertTrue(stringCaptor.allValues[0].startsWith("Entering method"))
        Assertions.assertTrue(stringCaptor.allValues[1].startsWith("Exiting method"))

        // parameter(s)
        Assertions.assertEquals("[]", (argumentsCaptor.allValues[0] as kotlin.Array<*>)[2])
        // method
        Assertions.assertTrue((argumentsCaptor.allValues[0] as kotlin.Array<*>)[0] == (argumentsCaptor.allValues[1] as kotlin.Array<*>)[0])
        // class
        Assertions.assertTrue((argumentsCaptor.allValues[0] as kotlin.Array<*>)[1] == (argumentsCaptor.allValues[1] as kotlin.Array<*>)[1])
        // spent
        Assertions.assertNotNull((argumentsCaptor.allValues[1] as kotlin.Array<*>)[2])
        // return
        Assertions.assertNull((argumentsCaptor.allValues[1] as kotlin.Array<*>)[3])
    }

    @Test
    fun `test aop debug log throw exception`() {
        `when`(logger.isDebugEnabled).thenReturn(true)

        instance.testException()
        verify(logger, times(2)).debug(stringCaptor.capture(),
                argumentsCaptor.capture())

        Assertions.assertTrue(stringCaptor.allValues[0].startsWith("Entering method"))
        Assertions.assertTrue(stringCaptor.allValues[1].startsWith("Exiting method"))

        // parameter(s)
        Assertions.assertEquals("[]", (argumentsCaptor.allValues[0] as kotlin.Array<*>)[2])
        // method
        Assertions.assertTrue((argumentsCaptor.allValues[0] as kotlin.Array<*>)[0] == (argumentsCaptor.allValues[1] as kotlin.Array<*>)[0])
        // class
        Assertions.assertTrue((argumentsCaptor.allValues[0] as kotlin.Array<*>)[1] == (argumentsCaptor.allValues[1] as kotlin.Array<*>)[1])
        // spent
        Assertions.assertNotNull((argumentsCaptor.allValues[1] as kotlin.Array<*>)[2])
        // exception
        Assertions.assertNotNull((argumentsCaptor.allValues[1] as kotlin.Array<*>)[3])
    }
}
