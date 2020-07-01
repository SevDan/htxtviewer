/*
 * Copyright 2020 d.sevostyanov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package model.api

import model.beans.TextViewReaderImpl
import model.objects.TextBuffer
import model.objects.TextView

/**
 * Service for read text views from text buffer
 */
interface TextViewReader {

    /**
     * Factory method
     */
    fun get() = TextViewReaderImpl

    /**
     * Returns numbered lines from text view
     */
    fun getViewContent(textView: TextView): Sequence<Pair<Long, String>>

    /**
     * Reads text view from text buffer and gets new text buffer if needed
     */
    fun readTextView(offset: Long, linesCount: Int, maxLineSize: Int, textBuffer: TextBuffer): Pair<TextView, TextBuffer>
}