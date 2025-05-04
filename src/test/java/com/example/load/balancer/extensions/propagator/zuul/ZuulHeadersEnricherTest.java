package com.example.load.balancer.extensions.propagator.zuul;

import com.example.load.balancer.extensions.zuul.ZuulHeadersEnricher;
import com.netflix.zuul.context.RequestContext;
import org.junit.jupiter.api.Test;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class ZuulHeadersEnricherTest {
    Set<String> keys = new HashSet<>(Arrays.asList("1"));
    HashMap<String, String> extraHeaders = new HashMap<String, String>() {{
        put("1", "1");
        put("2", "2");
    }};
    ZuulHeadersEnricher enricher = new ZuulHeadersEnricher(keys::contains, extraHeaders);
    HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    public void preHandle() throws Exception {
        assertThat(enricher.preHandle(request, null, null), is(true));
        assertThat(RequestContext.getCurrentContext().getZuulRequestHeaders().get("1"), is("1"));
        assertThat(RequestContext.getCurrentContext().getZuulRequestHeaders().containsKey("2"), is(false));
    }

    @Test
    public void postHandle() throws Exception {
        enricher.postHandle(request, null, null, null);
    }

    @Test
    public void afterCompletion() throws Exception {
        enricher.afterCompletion(request, null, null, null);
    }
}