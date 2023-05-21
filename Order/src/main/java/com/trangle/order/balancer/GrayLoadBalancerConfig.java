package com.trangle.order.balancer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer.REQUEST;

@Slf4j
@Component
public class GrayLoadBalancerConfig implements LoadBalancerClient {

    @Resource
    private  LoadBalancerClientFactory loadBalancerClientFactory;
    @Resource
    private  LoadBalancerProperties properties;
    @Resource
    private DiscoveryClient discoveryClient;

    @Override
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        log.info("GrayLoadBalancerConfig-execute");
//        String hint = getHint(serviceId);
//        LoadBalancerRequestAdapter<T, TimedRequestContext> lbRequest = new LoadBalancerRequestAdapter<>(request,
//                buildRequestContext(request, hint));
//        Set<LoadBalancerLifecycle> supportedLifecycleProcessors = getSupportedLifecycleProcessors(serviceId);
//        supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onStart(lbRequest));
//        ServiceInstance serviceInstance = choose(serviceId, lbRequest);
//        if (serviceInstance == null) {
//            supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onComplete(
//                    new CompletionContext<>(CompletionContext.Status.DISCARD, lbRequest, new EmptyResponse())));
//            throw new IllegalStateException("No instances available for " + serviceId);
//        }
//        return execute(serviceId, serviceInstance, lbRequest);
        return null;
    }

    @Override
    public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
        log.info("GrayLoadBalancerConfig-execute");
//        DefaultResponse defaultResponse = new DefaultResponse(serviceInstance);
//        Set<LoadBalancerLifecycle> supportedLifecycleProcessors = getSupportedLifecycleProcessors(serviceId);
//        Request lbRequest = request instanceof Request ? (Request) request : new DefaultRequest<>();
//        supportedLifecycleProcessors
//                .forEach(lifecycle -> lifecycle.onStartRequest(lbRequest, new DefaultResponse(serviceInstance)));
//        try {
//            T response = request.apply(serviceInstance);
//            Object clientResponse = getClientResponse(response);
//            supportedLifecycleProcessors
//                    .forEach(lifecycle -> lifecycle.onComplete(new CompletionContext<>(CompletionContext.Status.SUCCESS,
//                            lbRequest, defaultResponse, clientResponse)));
//            return response;
//        }
//        catch (IOException iOException) {
//            supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onComplete(
//                    new CompletionContext<>(CompletionContext.Status.FAILED, iOException, lbRequest, defaultResponse)));
//            throw iOException;
//        }
//        catch (Exception exception) {
//            supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onComplete(
//                    new CompletionContext<>(CompletionContext.Status.FAILED, exception, lbRequest, defaultResponse)));
//            ReflectionUtils.rethrowRuntimeException(exception);
//        }
        return null;
    }

    @Override
    public URI reconstructURI(ServiceInstance instance, URI original) {
        log.info("GrayLoadBalancerConfig-reconstructURI");
        return LoadBalancerUriTools.reconstructURI(instance, original);
    }

    @Override
    public ServiceInstance choose(String serviceId) {
        //获取所有可用服务
        log.info("GrayLoadBalancerConfig-choose-serviceId:{}", serviceId);
        return choose(serviceId, REQUEST);
    }

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        log.info("GrayLoadBalancerConfig-choose-serviceId:{},request:{}", serviceId, request.toString());
        List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceId);
        HttpHeaders headers = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders();
        List<String> grayList = headers.get("gray");
        String gray = null;
        if (CollectionUtils.isNotEmpty(grayList)) {
            gray = grayList.get(0);
        }
        List<ServiceInstance> grayInstanceList = new ArrayList<>();
        List<ServiceInstance> defaultInstanceList = new ArrayList<>();
        for (ServiceInstance instance : instanceList) {
            String instanceGray = instance.getMetadata().get("gray");
            if ("1".equals(instanceGray)) {
                grayInstanceList.add(instance);
            } else {
                defaultInstanceList.add(instance);
            }
        }
        if ("1".equals(gray)) {
            return grayInstanceList.get(0);
        } else {
            return defaultInstanceList.get(0);
        }
    }

    private String getHint(String serviceId) {
        String defaultHint = properties.getHint().getOrDefault("default", "default");
        String hintPropertyValue = properties.getHint().get(serviceId);
        return hintPropertyValue != null ? hintPropertyValue : defaultHint;
    }

//    private <T> TimedRequestContext buildRequestContext(LoadBalancerRequest<T> delegate, String hint) {
//        if (delegate instanceof HttpRequestLoadBalancerRequest) {
//            HttpRequest request = ((HttpRequestLoadBalancerRequest) delegate).getHttpRequest();
//            if (request != null) {
//                RequestData requestData = new RequestData(request);
//                return new RequestDataContext(requestData, hint);
//            }
//        }
//        return new DefaultRequestContext(delegate, hint);
//    }
//
//    private Set<LoadBalancerLifecycle> getSupportedLifecycleProcessors(String serviceId) {
//        return LoadBalancerLifecycleValidator.getSupportedLifecycleProcessors(
//                loadBalancerClientFactory.getInstances(serviceId, LoadBalancerLifecycle.class),
//                DefaultRequestContext.class, Object.class, ServiceInstance.class);
//    }
//
//    private <T> Object getClientResponse(T response) {
//        ClientHttpResponse clientHttpResponse = null;
//        if (response instanceof ClientHttpResponse) {
//            clientHttpResponse = (ClientHttpResponse) response;
//        }
//        if (clientHttpResponse != null) {
//            try {
//                return new ResponseData(clientHttpResponse, null);
//            }
//            catch (IOException ignored) {
//            }
//        }
//        return response;
//    }
}
