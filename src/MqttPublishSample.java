import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//https://help.aliyun.com/document_detail/146631.html

class MqttPostPropertyMessageListener implements IMqttMessageListener {
    @Override
    public void messageArrived(String var1, MqttMessage var2) throws Exception {
        System.out.println("reply topic  : " + var1);
        System.out.println("reply payload: " + var2.toString());
    }
}

public class MqttPublishSample {

    public static void main(String[] args) {

        String topic        = "MQTT Examples";
        String content      = "Message from MqttPublishSample";
        int qos             = 2;
        String broker       = "tcp://mqtt.eclipseprojects.io:1883";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");


            sampleClient.subscribe(topic, new MqttPostPropertyMessageListener());
            System.out.println("subscribe: " + topic);


            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");

            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}