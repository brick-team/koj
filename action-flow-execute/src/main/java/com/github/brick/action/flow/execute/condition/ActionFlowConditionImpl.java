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

package com.github.brick.action.flow.execute.condition;

import com.github.brick.action.flow.execute.ActionFlowExecute;
import com.github.brick.action.flow.execute.extract.Extract;
import com.github.brick.action.flow.execute.extract.ExtractActionFlowFactory;
import com.github.brick.action.flow.model.ActionFlowFactory;
import com.github.brick.action.flow.model.enums.ExtractModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Zen Huifer
 */
public class ActionFlowConditionImpl implements ActionFlowCondition {
    public static final String DROOL = "$.";
    private static final String[] ops =
            new String[] {">", ">=", "==", "<", "<=", "&&", "||",};
    static SpelExpressionParser parser = new SpelExpressionParser();
    private final ActionFlowFactory<ExtractModel, Extract> extractFactory =
            new ExtractActionFlowFactory();

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

    @Override
    public boolean condition(String condition, ExtractModel elType, Object o) {

        if (elType == ExtractModel.JSON_PATH) {
            return handlerLeftRight(condition, o);
        }

        Extract factory = extractFactory.factory(elType);
        return (boolean) factory.extract(o, condition);
    }

    @Override
    public boolean condition(String condition, ExtractModel elType, String json) {
        Extract factory = extractFactory.factory(elType);
        return (boolean) factory.extract(json, condition);
    }

    private Boolean handlerLeftRight(String s2, Object o) {
        char[] chars = s2.toCharArray();
        Extract extract = this.extractFactory.factory(ExtractModel.JSON_PATH);

        if (s2.contains("(") && s2.contains(")")) {


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
                    String[] s1 = substring.trim().split(" ");
                    if (s1.length == 3) {

                        Boolean value =
                                handlerLeftAndRightCondition(s1[0], s1[1], s1[2], extract,
                                        o);
                        s2 = s2.replace(substring, value.toString());
                    }
                    start = 0;
                    end = 0;
                }
            }
            Expression expression = parser.parseExpression(s2);
            EvaluationContext context = new StandardEvaluationContext();
            Boolean value = expression.getValue(context, Boolean.class);
            return value;
        } else {
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

            Boolean value = handlerLeftAndRightCondition(s2.substring(0, start),
                    s2.substring(start, end + 1), s2.substring(end + 1), extract, o);
            return value;
        }

    }

    private Boolean handlerLeftAndRightCondition(String s2,
                                                 String s21,
                                                 String s22,
                                                 Extract extract,
                                                 Object o) {
        String left = s2;
        String op = s21;
        String right = s22;
        Object leftVal = null;
        Object rightVal = null;
        if (left.contains(DROOL)) {
            leftVal = extract.extract(o, left);
        }
        if (right.contains(DROOL)) {
            rightVal = extract.extract(o, right);
        }


        Expression expression = parser.parseExpression(leftVal + op + rightVal);
        EvaluationContext context = new StandardEvaluationContext();
        Boolean value = expression.getValue(context, Boolean.class);
        return value;
    }


}
