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

package model.beans

import model.api.TextViewReader
import model.objects.TextBuffer
import model.objects.TextView

object TextViewReaderImpl : TextViewReader {

    override fun getViewContent(textView: TextView): Sequence<Pair<Long, String>> {
        var currentLine = textView.startLine
        val result: MutableList<Pair<Long, String>> = mutableListOf()

        var seq = textView.textBuffer.content
                .drop(textView.offset.toInt())

        while (currentLine < textView.endLine) {
            var strSize = 0
            var strBuf = mutableListOf<Char>()

            for (ch in seq) {
                strSize++

                if (strSize == textView.maxLineSize) {
                    strBuf.add(ch)
                    strBuf.add('\n')
                    result.add(Pair(currentLine, String(strBuf.toCharArray())))
                    break
                }

                if (ch == '\n') {
                    strBuf.add(ch)
                    currentLine++
                    result.add(Pair(currentLine, String(strBuf.toCharArray())))
                    break
                }
            }
            seq = seq.drop(strSize)
        }

        return result.asSequence()
    }

    override fun readTextView(offset: Long, linesCount: Int, maxLineSize: Int, textBuffer: TextBuffer): Pair<TextView, TextBuffer> {
        TODO("Method should try get data from text buffer, splits it by lines and change buffer if needed")
    }
}