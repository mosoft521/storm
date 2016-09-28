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
package com.jwplayer.sqe.trident.aggregator;

import static org.junit.Assert.*;

import com.clearspring.analytics.stream.cardinality.HyperLogLogPlus;
import com.clearspring.analytics.stream.cardinality.ICardinality;
import com.jwplayer.sqe.trident.SingleValuesCollector;
import org.junit.Before;
import org.junit.Test;
import org.apache.storm.trident.tuple.TridentTupleView;
import org.apache.storm.tuple.Fields;

import java.io.IOException;


public class HllpAggregatorTest {
    private CardinalityEstimatorAggregator aggregator;
    private SingleValuesCollector collector;

    @Before
    public void setup() {
        aggregator = new HllpAggregator(11);
        collector = new SingleValuesCollector();
    }

    @Test
    public void testAggregator() throws IOException {
        ICardinality hllp = aggregator.init(null, collector);

        assertEquals(hllp.cardinality(), 0);

        aggregator.aggregate(hllp, TridentTupleView.createFreshTuple(new Fields("Object"), "a"), collector);
        aggregator.aggregate(hllp, TridentTupleView.createFreshTuple(new Fields("Object"), "b"), collector);
        aggregator.aggregate(hllp, TridentTupleView.createFreshTuple(new Fields("Object"), "c"), collector);

        assertEquals(hllp.cardinality(), 3);

        aggregator.complete(hllp, collector);
        HyperLogLogPlus hllp2 = HyperLogLogPlus.Builder.build((byte[]) collector.values.get(0));

        assertEquals(hllp.cardinality(), hllp2.cardinality());
    }
}
