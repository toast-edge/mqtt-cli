package com.toast.mqtt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MQTT 接口", description = "MQTT 订阅和发布接口")
@RestController
public class MqttController {

    @Operation(summary = "发布 MQTT 消息", description = "向指定的 MQTT 代理和主题发布消息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "消息发布成功", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "消息发布失败", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/mqtt/publish")
    public String publish(@Parameter(description = "MQTT 请求参数", required = true) @RequestBody MqttRequest request) {
        try {
            MqttClient client = new MqttClient(request.getBrokerUrl(), request.getClientId());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);

            MqttMessage mqttMessage = new MqttMessage(request.getMessage().getBytes());
            client.publish(request.getTopic(), mqttMessage);

            client.disconnect();
            return "消息发布成功";
        } catch (MqttException e) {
            e.printStackTrace();
            return "消息发布失败: " + e.getMessage();
        }
    }

    @Operation(summary = "订阅 MQTT 主题", description = "订阅指定的 MQTT 代理和主题")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "订阅成功", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "订阅失败", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/mqtt/subscribe")
    public String subscribe(@Parameter(description = "MQTT 请求参数", required = true) @RequestBody MqttRequest request) {
        try {
            MqttClient client = new MqttClient(request.getBrokerUrl(), request.getClientId());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);

            client.subscribe(request.getTopic(), (receivedTopic, mqttMessage) -> {
                System.out.println("收到消息 - 主题: " + receivedTopic + ", 内容: " + new String(mqttMessage.getPayload()));
            });

            return "订阅成功，等待接收消息";
        } catch (MqttException e) {
            e.printStackTrace();
            return "订阅失败: " + e.getMessage();
        }
    }
}