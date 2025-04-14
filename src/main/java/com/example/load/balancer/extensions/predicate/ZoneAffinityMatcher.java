///*
// * Copyright (c) 2017 the original author or authors
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.example.load.balancer.extensions.predicate;
//
//import com.example.load.balancer.extensions.support.ZoneAffinityConfig;
//import com.netflix.loadbalancer.PredicateKey;
//import jakarta.validation.constraints.NotNull;
//import lombok.extern.slf4j.Slf4j;
//
//import static java.lang.String.format;
//
///**
// * Filters Servers against the current µservice zone.
// *
// * @author Nadim Benabdenbi
// * @see ZoneAffinityConfig for a concrete usage
// */
//@Slf4j
//public class ZoneAffinityMatcher extends NullSafeServerPredicate {
//    /**
//     * the application zone.
//     */
//    private final String zone;
//
//    /**
//     * Sole Constructor.
//     *
//     * @param zone the application zone.
//     */
//    public ZoneAffinityMatcher(@NotNull String zone) {
//        this.zone = zone;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected boolean doApply(PredicateKey server) {
//        boolean accept = zone.equals(server.getServer().getZone());
//        log.trace("Expected zone [{}] vs {}:{}[{}] => {}",
//                zone,
//                server.getServer().getHostPort(),
//                server.getServer().getMetaInfo().getAppName(),
//                server.getServer().getZone(),
//                accept);
//        return accept;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public String toString() {
//        return format("ZoneAffinityMatcher[zone=%s]", zone);
//    }
//}
