package com.toy.mealimeter.config.security;

import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtils {

	public Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
		return WebUtils.getCookie(httpServletRequest, name);
	}

}
