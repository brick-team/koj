package com.github.brick.action.flow;

import com.github.brick.action.flow.method.search.ClazzSearch;
import org.junit.Test;

import java.util.List;

public class ClassLoaderTest {

    @Test
    public void classLoaderFindAllClass() {
        List<String> clazzName = ClazzSearch.getClazzName("com", true);
        System.out.println();
    }
}
