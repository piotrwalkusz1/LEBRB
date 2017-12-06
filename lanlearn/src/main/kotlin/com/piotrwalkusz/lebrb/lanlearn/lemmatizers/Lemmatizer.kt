package com.piotrwalkusz.lebrb.lanlearn.lemmatizers

import java.io.Reader

interface Lemmatizer {

    fun lemmatize(source: Reader, dictionary: Set<String>): Map<String, Int>
}