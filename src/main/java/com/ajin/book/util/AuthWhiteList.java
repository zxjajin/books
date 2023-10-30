package com.ajin.book.util;

/**
 * @author zhaoxg on 2023年03月14日 10:46
 */
public class AuthWhiteList {

    /**
     * 需要放行的URL
     */
    public static final String[] AUTH_WHITELIST = {
            // -- book url
            "/book/{id}",
            "/book/book/{id}",
            "/book/books",
            "/book/list",
            "/book/search",
            "/book/type/{id}",
            // -- user url
            "/user/login",
            "/user/resign",
            // -- category url
            "/category/{id}",
            "/category/cates",
            "/category/list",
            // -- common url
            "/common/download",
            "/common/upload",
            // -- admin url
            "/admin/login",
            "/admin/resign",
            // -- swagger ui
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/doc.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/v3/**",
            "/api/**",
            "/static/**",
            "/resources/**"
            // other public endpoints of your API may be appended to this array
    };
}
