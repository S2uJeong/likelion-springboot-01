package com.springboot.likelion01.domain.parser;

import java.util.List;

public interface Parser<T> {
    T parse(String str);
}
