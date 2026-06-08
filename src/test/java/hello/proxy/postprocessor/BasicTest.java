package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Slf4j
public class BasicTest {

    @Test
    void basicConfig() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessor.class);

        // beanA 라는 이름으로 B 객체가 빈으로 등록된다.
        B beanA = applicationContext.getBean("beanA", B.class);
        beanA.helloB();

        // A는 빈으로 등록되지 않는다. (예외가 터져야 Build 성공)
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessor {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AtoBPostProcessor helloPostProcessor() {
            return new AtoBPostProcessor();
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");

        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");

        }
    }

    @Slf4j
    static class AtoBPostProcessor implements org.springframework.beans.factory.config.BeanPostProcessor {

        @Nullable
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}", beanName, bean);
            // A 객체가 빈으로 등록되려 하면 B 로 교체
            if(bean instanceof A) {
                return new B();
            }
            // if 문에 걸리지 않으면 그대로 빈 등록
            return bean;
        }
    }
}
