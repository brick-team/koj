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

package com.github.brick.action.flow.method.execute;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class MethodExecuteImplTest {
    MethodExecuteImpl methodExecute = new MethodExecuteImpl();

    @Test
    public void execute() throws Exception {
        Object execute = null;
        execute = methodExecute.execute(A.class.getName(), "data", new String[]{
                Object.class.getName(),
        }, Collections.singletonList("hello"));

        assert execute.equals("hello");
        String[] o = new String[]{
                "hello", "hello"
        };
        ArrayList<Object> args = new ArrayList<>();
        args.add(o);
        execute = methodExecute.execute(A.class.getName(), "data", new String[]{
                Object[].class.getName(),
        }, args);
        assert execute.equals("hellohello");
    }



    public static class A {
        public String data(Object o) {
            return o.toString();
        }

        public String data(Object[] o) {
            StringBuilder stringBuilder = new StringBuilder(32);
            for (Object o1 : o) {
                stringBuilder.append(o1);
            }
            return stringBuilder.toString();
        }

    }

}