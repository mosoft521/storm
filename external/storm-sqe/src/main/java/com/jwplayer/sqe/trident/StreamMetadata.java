/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jwplayer.sqe.trident;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.apache.storm.shade.org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;
import java.util.Arrays;


public class StreamMetadata implements Serializable {
    public long pid;
    public int partition;
    public long offset;

    public StreamMetadata(long pid, int partition, long offset) {
        this.pid = pid;
        this.partition = partition;
        this.offset = offset;
    }

    public String getPidAndPartitionAsHex() {
        return Long.toHexString(pid) + "-" + Integer.toHexString(partition);
    }

    public byte[] toBytes() {
        return ArrayUtils.addAll(
                Longs.toByteArray(pid),
                ArrayUtils.addAll(Ints.toByteArray(partition), Longs.toByteArray(offset))
        );
    }

    public static StreamMetadata parseBytes(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == 20, "Stream metadata bytes representation must contain exactly 20 bytes");

        long pid = Longs.fromByteArray(Arrays.copyOfRange(bytes, 0, 8));
        int partition = Ints.fromByteArray(Arrays.copyOfRange(bytes, 8, 12));
        long offset = Longs.fromByteArray(Arrays.copyOfRange(bytes, 12, 20));

        return new StreamMetadata(pid, partition, offset);
    }
}
