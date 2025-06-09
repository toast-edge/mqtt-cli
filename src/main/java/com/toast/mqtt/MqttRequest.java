package com.toast.mqtt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "MQTT 请求参数实体类")
public class MqttRequest {
    @Schema(description = "MQTT 主题，用于发布或订阅消息", required = true)
    private String topic;

    @Schema(description = "要发布的 MQTT 消息内容，订阅时可忽略", required = false)
    private String message;

    @Schema(description = "MQTT 代理服务器的地址，例如：tcp://localhost:1883", required = true)
    private String brokerUrl;

    @Schema(description = "MQTT 客户端 ID，若未提供则会自动生成一个随机 ID", required = false)
    private String clientId;

    public String getClientId() {
        if (clientId == null) {
            clientId = "MqttRestClient-" + UUID.randomUUID().toString();
        }
        return clientId;
    }
}