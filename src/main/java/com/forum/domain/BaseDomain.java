package com.forum.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by lizhiping03 on 2018/9/17.
 */
public class BaseDomain implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
