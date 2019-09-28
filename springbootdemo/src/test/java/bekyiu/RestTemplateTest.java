package bekyiu;

import bekyiu.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = App.class)
public class RestTemplateTest
{
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void get()
    {
        User[] list = restTemplate.getForObject("http://localhost/users", User[].class);
        for (User user : list)
        {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void fun()
    {
        String str = "@tap @tap.stop @tap.prevent @tap.once @click @click.stop @click.prevent @click.once @change @change.lazy @change.number @change.trim v-model v-for v-text v-html v-if v-else-if v-else v-pre v-once v-bind scoped";
        String[] s = str.split(" ");
        String res = "";
        for (String s1 : s)
        {
            res += (s1 + ",");
        }
        System.out.println(res);
    }
}
