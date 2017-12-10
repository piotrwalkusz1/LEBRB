package com.piotrwalkusz.lebrb.lanlearn.wordsexporters

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CSVWordsExporterTest {

    @Test
    fun export() {
        val exporter = CSVWordsExporter()

        val result = exporter.export(mapOf("der Hund" to "dog", "essen" to "eat", "die Katze" to "cat"))

        assertEquals("der Hund;dog\nessen;eat\ndie Katze;cat", result)
    }
}