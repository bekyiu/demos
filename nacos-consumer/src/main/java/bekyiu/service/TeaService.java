package bekyiu.service;

import bekyiu.client.HelloApi;
import bekyiu.domain.Tea;
import bekyiu.mapper.TeaMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wangyc
 * @Date: 2020/9/10 14:56
 */
@Service
public class TeaService
{
    @Autowired
    private TeaMapper teaMapper;
    @Autowired
    private HelloApi helloApi;

    @GlobalTransactional
    public void saveTea()
    {
        teaMapper.insert(Tea.builder().name("tttx").build());
        //
        helloApi.save();

        int a = 1 / 0;
    }
}
