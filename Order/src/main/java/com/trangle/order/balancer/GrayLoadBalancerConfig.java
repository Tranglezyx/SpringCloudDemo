package com.trangle.order.balancer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer.REQUEST;

/**
 * 根据灰度信息动态修改服务节点
 * 参考类：org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient
 */
@Slf4j
@Component
public class GrayLoadBalancerConfig implements LoadBalancerClient {

    @Resource
    private LoadBalancerClientFactory loadBalancerClientFactory;
    @Resource
    private LoadBalancerProperties properties;
    @Resource
    private DiscoveryClient discoveryClient;

    @Override
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        log.info("GrayLoadBalancerConfig-execute");
        return null;
    }

    @Override
    public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
        log.info("GrayLoadBalancerConfig-execute");
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
        // 这里做的比较简单，永远只取第一个，实际上可以自定义一些策略，比如轮训
        if ("1".equals(gray)) {
            return grayInstanceList.get(0);
        } else {
            return defaultInstanceList.get(0);
        }
    }
}
