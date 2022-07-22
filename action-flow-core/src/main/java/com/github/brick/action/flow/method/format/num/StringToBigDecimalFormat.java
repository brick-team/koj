/*
 *    Copyright [2022] [brick-team]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.brick.action.flow.method.format.num;

import com.github.brick.action.flow.method.format.Format;

import java.math.BigDecimal;

/**
 * string to BigDecimal.
 * @author Zen Huifer
 */
public class StringToBigDecimalFormat implements Format<String> {
    @Override
    public BigDecimal format(String s, Class<?> clazz) {
        return new BigDecimal(s);
    }
}
