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

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class A {
    private static final String[] ops = new String[]{
            ">",
            ">=",
            "==",
            "<",
            "<=",
            "&&",
            "||",
    };
    static SpelExpressionParser parser = new SpelExpressionParser();

    public static void main(String[] args) {
        String s0 = "$.age>=10 ";
        String s1 = "( $.age > 10 ) && ( $.age > 10 )";
        String s2 = "( ( ( true && (1 == 1) || ( ( ( true && (1 == 1) ) && ((  1 > 2 ) || ( 1 > 3 )) ) || (  1 > 4 ) ) && ( 1 > 5 ) ) && ((  1 > 2 ) || ( 1 > 3 )) ) || (  1 > 4 ) ) && ( 1 > 5 )";


        char[] chars = s0.toCharArray();

        int start = 0;
        int end = 0;
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (inOps(aChar)) {
                start = i;
                if (inOps(Character.toString(aChar) + chars[i + 1])) {
                    end = i + 1;
                    break;

                } else {
                    end = i;
                }
            }
        }
        System.out.println("left = " + s0.substring(0, start));
        System.out.println("op = " + s0.substring(start, end + 1));
        System.out.println("right = " + s0.substring(end + 1));
    }

    private static void handlerLeftRight(String s2) {
        char[] chars = s2.toCharArray();

        StringBuilder sb = new StringBuilder();
        int start = 0;
        int end = 0;
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            sb.append(aChar);
            if (aChar == '(') {
                start = i;
            }
            if (aChar == ')') {
                end = i;
            }
            if (end != 0) {
                String s = sb.toString();

                String substring = s.substring(start + 1, end);
//                System.out.println(substring);
                String[] s1 = substring.trim().split(" ");
                if (s1.length == 3) {
                    Expression expression = parser.parseExpression(substring);
                    EvaluationContext context = new StandardEvaluationContext();
                    Boolean value = expression.getValue(context, Boolean.class);
                    s2 = s2.replace(substring, value.toString());
                }
                start = 0;
                end = 0;
            }
        }
        System.out.println(s2);
    }


    private static boolean inOps(String aChar) {
        for (String op : ops) {
            if (op.contains(aChar)) {
                return true;
            }
        }
        return false;
    }


    private static boolean inOps(char aChar) {
        for (String op : ops) {
            if (op.contains(Character.toString(aChar))) {
                return true;
            }
        }
        return false;
    }

    private static class Expressions {
        String left;
        String op;
        String right;

        @Override
        public String toString() {
            return "Expression{" +
                    "left='" + left + '\'' +
                    ", op='" + op + '\'' +
                    ", right='" + right + '\'' +
                    '}';
        }
    }

}
