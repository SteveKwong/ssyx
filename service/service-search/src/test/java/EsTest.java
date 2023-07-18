import com.atguigu.ssyx.search.ServiceSearchApplication;
import com.atguigu.ssyx.search.config.RabbitMQConfig;
import org.elasticsearch.client.ClusterClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kuanggong
 * @date 2023/7/14 15:25
 * @project_name atguigu-ssyx-parent
 */
@SpringBootTest(classes = {ServiceSearchApplication.class})
@RunWith(SpringRunner.class)
public class EsTest {
    @Qualifier(value = "esClient")
    @Autowired
    private RestHighLevelClient client;
    /**
     * 兔子队列
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void esTest() {
        ClusterClient cluster = client.cluster();
        System.out.println(cluster);
    }
    @Test
    public void mqTest() {
        //发送消息
        System.out.println("开始进行信息发送");
        for (int i = 0; i < 1000; i++) {
            rabbitTemplate.convertAndSend("", RabbitMQConfig.AD_UPDATE_QUEUE,"能听到吗"+i);
        }

    }
}
