package com.github.friday.app.config.shiro;

import com.github.friday.app.base.ApiResult;
import com.github.friday.app.base.ResultCode;
import com.github.friday.app.exception.TokenException;
import com.github.friday.app.utils.RequestUtils;
import com.github.friday.app.utils.ResponseUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码的执行流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String getToken(HttpServletRequest request) {
		return RequestUtils.getToken(request);
	}

	/**
	 * 判断用户是否想要登入。 检测header里面是否包含Authorization字段即可
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = getToken(req);
		return authorization != null;
	}

	/**
	 *
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authorization = getToken(httpServletRequest);
		JWTToken token = new JWTToken(authorization);

		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		try {
			getSubject(request, response).login(token);

		} catch (TokenException e) {
			logger.warn(e.getMessage());
			ApiResult apiResult = new ApiResult(e.getCode(), e.getMessage(), null);
			ResponseUtils.responseWithJson((HttpServletResponse) response, apiResult);
			return false;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ApiResult apiResult = new ApiResult(ResultCode.FAIL, null);
			ResponseUtils.responseWithJson((HttpServletResponse) response, apiResult);
			return false;
		}

		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}

	/**
	 * 如果找不到token，或者读取token出错，标识调用方是非法调用，调转到401报错
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginAttempt(request, response)) {
			return executeLogin(request, response);

		} else {
			ApiResult apiResult = new ApiResult(ResultCode.NO_AUTH, null);
			ResponseUtils.responseWithJson((HttpServletResponse) response, apiResult);
			return false;
		}

	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		return super.preHandle(request, response);
	}

	/**
	 * 此处重写父类方法是因为
	 * preHandle 会调用到 AccessControlFilter.onPreHandle()
	 * 导致上面的 isAccessAllowed return false 之后又会调用一遍 executeLogin 没有必要
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}

}
