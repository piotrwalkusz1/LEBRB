package com.piotrwalkusz.lebrb.lanlearn

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LanguageTest {

    @Test
    fun valueOfIgnoreCase() {
        assertEquals(Language.ENGLISH, Language.valueOfIgnoreCase("english"))
        assertEquals(Language.ENGLISH, Language.valueOfIgnoreCase("EngLISH"))
        assertEquals(Language.GERMAN, Language.valueOfIgnoreCase("GERman"))
    }
}