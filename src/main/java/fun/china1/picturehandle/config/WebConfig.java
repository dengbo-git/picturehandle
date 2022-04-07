package fun.china1.picturehandle.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import fun.china1.picturehandle.controller.filter.HasTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author ：波波
 * @date ：Created in 2022/3/21 21:57
 * @description：
 * @modified By：
 * @version: $
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private HasTokenFilter hasTokenFilter;

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hasTokenFilter).addPathPatterns("/handle/**", "/history/**","/suggest/**");
    }

    /**
     * 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
     * paginationInterceptor.setOverflow(false);
     * 设置最大单页限制数量，默认 500 条，-1 不受限制
     * paginationInterceptor.setLimit(500);
     *分页拦截器
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(false);
        paginationInnerInterceptor.setMaxLimit(500L);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return mybatisPlusInterceptor;
    }
    @Bean
    public RestTemplate restTemplate(){     //在SpringBoot启动类中注册RestTemplate
        return new RestTemplate();
    }
}
