/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.metrics.model;

import org.apache.dubbo.metrics.model.sample.MetricSample;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.support.RpcUtils;

import java.util.Map;
import java.util.Objects;

import static org.apache.dubbo.common.constants.MetricsConstants.TAG_GROUP_KEY;
import static org.apache.dubbo.common.constants.MetricsConstants.TAG_VERSION_KEY;
import static org.apache.dubbo.metrics.MetricsConstants.INVOCATION_METRICS_COUNTER;

/**
 * Metric class for method.
 */
public class MethodMetric extends ServiceKeyMetric {
    private String side;
    private final String methodName;
    private String group;
    private String version;
    private final MetricSample.Type sampleType;

    public MethodMetric(ApplicationModel applicationModel, Invocation invocation) {
        super(applicationModel, MetricsSupport.getInterfaceName(invocation));
        this.methodName = RpcUtils.getMethodName(invocation);
        this.side = MetricsSupport.getSide(invocation);
        this.group = MetricsSupport.getGroup(invocation);
        this.version = MetricsSupport.getVersion(invocation);
        this.sampleType = (MetricSample.Type) invocation.get(INVOCATION_METRICS_COUNTER);
    }

    public MetricSample.Type getSampleType() {
        return sampleType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getTags() {
        Map<String, String> tags = MetricsSupport.methodTags(getApplicationModel(), getInterfaceName(), methodName);
        tags.put(TAG_GROUP_KEY, group);
        tags.put(TAG_VERSION_KEY, version);
        return tags;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "MethodMetric{" +
                "applicationName='" + getApplicationName() + '\'' +
                ", side='" + side + '\'' +
                ", interfaceName='" + getInterfaceName() + '\'' +
                ", methodName='" + methodName + '\'' +
                ", group='" + group + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodMetric that = (MethodMetric) o;
        return Objects.equals(getApplicationName(), that.getApplicationName()) && Objects.equals(side, that.side) && Objects.equals(getInterfaceName(), that.getInterfaceName()) && Objects.equals(methodName, that.methodName) && Objects.equals(group, that.group) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApplicationName(), side, getInterfaceName(), methodName, group, version);
    }
}
