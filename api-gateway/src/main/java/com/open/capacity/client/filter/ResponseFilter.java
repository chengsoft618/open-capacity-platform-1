package com.open.capacity.client.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.open.capacity.client.utils.LoggingUtils;

/**
 * 请求完成后，将trace_id设置到response header里面进行传递
 * @author gitgeek
 *
 */
@Component
public class ResponseFilter extends ZuulFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class);
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final String FILTER_TYPE = "post";

	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		String URL = requestContext.getRequest().getRequestURL().toString();
		LOGGER.debug("response url " + URL + ", traceId = " + LoggingUtils.getTraceId());
		requestContext.getResponse().addHeader(LoggingUtils.TRACE_ID, LoggingUtils.getTraceId()); 
		return null;
	}
	}
