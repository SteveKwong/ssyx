import com.atguigu.ssyx.user.ServiceUserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kuanggong
 * @date 2023/7/25 14:13
 * @project_name atguigu-ssyx-parent
 */
@SpringBootTest(classes = {ServiceUserApplication.class})
@RunWith(SpringRunner.class)
public class ConfigTest {

    @Value("${wx.open.app_id}")
    private String appid;
    @Value("${wx.open.app_secret}")
    private String appSecret;

    @Test
    public void test() {
        System.out.println(appid);
        System.out.println(appSecret);
    }
}
