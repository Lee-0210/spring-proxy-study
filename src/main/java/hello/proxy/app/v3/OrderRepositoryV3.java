package hello.proxy.app.v3;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV3 {

    public void save(String itemId) {
        // 저장 로직
        if("ex".equals(itemId)) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    private void sleep(int miilis) {
        try {
            Thread.sleep(miilis);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
