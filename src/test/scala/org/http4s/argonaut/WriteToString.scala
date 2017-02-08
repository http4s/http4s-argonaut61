package org.http4s
package argonaut

import scalaz.Monoid
import scalaz.stream.Process
import scalaz.stream.text.utf8Decode
import scodec.bits.ByteVector

trait WriteToString {
  // In the real repo, this comes from Http4sSpec
  def writeToString[A](a: A)(implicit W: EntityEncoder[A]): String =
    Process.eval(W.toEntity(a))
      .collect { case EntityEncoder.Entity(body, _ ) => body }
      .flatMap(identity)
      .fold1Monoid
      .pipe(utf8Decode)
      .runLastOr("")
      .run

  implicit val byteVectorMonoidInstance: Monoid[ByteVector] = Monoid.instance(_ ++ _, ByteVector.empty)
}
