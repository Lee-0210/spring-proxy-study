package hello.proxy.app.v2;

public class OrderRepositoryV2 {

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
