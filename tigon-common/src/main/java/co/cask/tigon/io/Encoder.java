/*
 * Copyright © 2014 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.tigon.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Interface for encoding data.
 */
public interface Encoder {

  Encoder writeNull() throws IOException;

  Encoder writeBool(boolean b) throws IOException;

  Encoder writeInt(int i) throws IOException;

  Encoder writeLong(long l) throws IOException;

  Encoder writeFloat(float f) throws IOException;

  Encoder writeDouble(double d) throws IOException;

  Encoder writeString(String s) throws IOException;

  Encoder writeBytes(byte[] bytes) throws IOException;

  Encoder writeBytes(byte[] bytes, int off, int len) throws IOException;

  /**
   * Writes out the remaining bytes in {@link java.nio.ByteBuffer}.
   * <p>
   * The given {@link java.nio.ByteBuffer} is untouched after this method returns (i.e. same position and limit).
   * </p>
   *
   * @param bytes
   * @throws java.io.IOException
   */
  Encoder writeBytes(ByteBuffer bytes) throws IOException;
}
