package hello.proxy.cgilb.code;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(
        Object o,
        Method method,
        Object[] args,
        MethodProxy methodProxy // CGLIB 는 성능상 methodProxy 를 사용
    ) throws Throwable {
        log.info("TimeProxy 실행");
        long start = System.currentTimeMillis();
        Object result = methodProxy.invoke(target, args);
        long end = System.currentTimeMillis();
        long resultTime = end - start;
        log.info("TimeProxy 종료 resultTime={}", resultTime);

        return result;
    }
}
