/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kite9.diagram.common.fraction

/**
 * Thrown for any BigFraction issues
 */
class FractionException : RuntimeException {

    constructor(v: String) : super(v);

    /**
     * Constructs an exception with specified formatted detail message.
     * Message formatting is delegated to [java.text.MessageFormat].
     * @param value double value to convert
     * @param maxIterations maximal number of iterations allowed
     */
    constructor(value: Double, maxIterations: Int) : super(
        "Unable to convert %d to fraction after %i iterations".format(
        value,
        maxIterations))

    /**
     * Constructs an exception with specified formatted detail message.
     * Message formatting is delegated to [java.text.MessageFormat].
     * @param value double value to convert
     * @param p current numerator
     * @param q current denominator
     */
    constructor(value: Double, p: Long, q: Long) : super(
        "Overflow trying to convert %d to fraction (%l/%l)".format(
        value,
        p,
        q)
    )

}