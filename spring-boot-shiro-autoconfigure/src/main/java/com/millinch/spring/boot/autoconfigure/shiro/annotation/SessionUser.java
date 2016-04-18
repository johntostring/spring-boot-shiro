package com.millinch.spring.boot.autoconfigure.shiro.annotation;

import java.lang.annotation.*;

/**
 * 获取Shiro当前用户
 * @author 张劲航
 * @see SessionUserArgumentResolver
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionUser {
}
