/*
    Telekinesis, version [unreleased]. Copyright 2024 Jon Pretty, Propensive OÜ.

    The primary distribution site is: https://propensive.com/

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
    file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the
    License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    either express or implied. See the License for the specific language governing permissions
    and limitations under the License.
*/

package telekinesis

import anticipation.*
import gossamer.*
import rudiments.*
import turbulence.*

object HttpReadable:
  given Text is HttpReadable as text = (status, body) => body.read[Bytes].utf8
  given Bytes is HttpReadable as bytes = (status, body) => body.read[Bytes]
  given LazyList[Bytes] is HttpReadable as byteStream = (status, body) => body

  given [ContentType: GenericHttpReader as readable]
      => ContentType is HttpReadable as genericHttpReader =
    (status, body) => readable.read(body.read[Bytes].utf8)

  given HttpStatus is HttpReadable as httpStatus:
    def read(status: HttpStatus, body: LazyList[Bytes]) = status

trait HttpReadable:
  type Self
  def read(status: HttpStatus, body: LazyList[Bytes]): Self
