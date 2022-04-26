package com.github.brick.action.flow.execute.protobuf;

import com.google.protobuf.ByteString;
import org.junit.Test;

public class ProtoBufDemo {
    @Test
    public void demo1() {
        ByteString bs=ByteString.copyFrom("hi".getBytes());
        System.out.println(bs.toStringUtf8());

    }
}
