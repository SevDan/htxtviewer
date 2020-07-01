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

import model.api.TextFileReader
import model.objects.TextBuffer
import java.nio.ByteBuffer
import java.nio.channels.SeekableByteChannel

object TextFileReaderImpl : TextFileReader {

    override fun readTextBuffer(
            fileChannel: SeekableByteChannel,
            offset: Long,
            size: Long
    ): TextBuffer {
        check(fileChannel.isOpen) { "File channel is closed" }
        check(offset > 0) { "Start from negative index" }
        check(size > 0) { "Negative size" }

        val byteBuffer = ByteBuffer.allocate(size.toInt())

        fileChannel.position(offset).read(byteBuffer)

        return TextBuffer(offset, size, byteBuffer.asCharBuffer().asSequence())
    }

    override fun readTextBufferPart(fileChannel: SeekableByteChannel,
                                    textBuffer: TextBuffer,
                                    offset: Long
    ): TextBuffer {
        check(fileChannel.isOpen) { "File channel is closed" }

        if (offset < 0) {
            check(-offset < textBuffer.offset)

            val byteBuffer = ByteBuffer.allocate((-offset).toInt())

            val start = textBuffer.offset + offset
            fileChannel.position(start).read(byteBuffer)

            return TextBuffer(start, textBuffer.size, sequence {
                byteBuffer.asCharBuffer().asSequence()
                textBuffer.content.take((textBuffer.size + offset).toInt())
            })
        } else {
            val byteBuffer = ByteBuffer.allocate(offset.toInt())

            val start = textBuffer.offset + offset
            fileChannel.position(start + textBuffer.size).read(byteBuffer)

            return TextBuffer(start, textBuffer.size, sequence {
                textBuffer.content.drop(offset.toInt())
                byteBuffer.asCharBuffer().asSequence()
            })
        }
    }
}