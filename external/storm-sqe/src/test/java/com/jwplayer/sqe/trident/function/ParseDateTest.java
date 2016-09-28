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
package com.jwplayer.sqe.trident.function;

import static org.junit.Assert.*;

import com.jwplayer.sqe.trident.SingleValuesCollector;
import org.junit.Test;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.trident.tuple.TridentTupleView;
import org.apache.storm.tuple.Fields;

import java.util.Date;


public class ParseDateTest {
    @Test
    public void testParseDate() {
        ParseDate parseDate = new ParseDate();
        String date = "1970-01-01 00:00:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        TridentTuple tuple = TridentTupleView.createFreshTuple(new Fields("Date", "Format"), date, format);
        SingleValuesCollector collector = new SingleValuesCollector();

        parseDate.execute(tuple, collector);

        assertEquals(collector.values.get(0), new Date(0l));
    }
}
