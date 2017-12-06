package com.piotrwalkusz.lebrb.lanlearn

enum class Language {

    POLISH,
    ENGLISH,
    GERMAN;

    companion object {

        fun valueOfIgnoreCase(value: String): Language? {
            return Language.values().find { it.name.toLowerCase() == value.toLowerCase() }
        }
    }
}