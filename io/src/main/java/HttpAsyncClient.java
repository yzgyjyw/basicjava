import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpAsyncClient {
    public static void main(String[] args) {
        demo();
    }

    static void demo(){
        String url = "https://www.baidu.com";
        HttpGet get = new HttpGet(url);

        //connectTimeout:建立连接的时间
        //socketTimeout:请求超时，传输过程中数据包之间间隔的最大时间
        //connectionRequestTimeout:使用连接池管理连接时，从连接池获取连接时的超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).setConnectionRequestTimeout(1000).build();

        //IO线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors()).setSoKeepAlive(true).build();
        //设置连接池大小
        try {
            DefaultConnectingIOReactor connectingIOReactor = new DefaultConnectingIOReactor(ioReactorConfig);
            PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(connectingIOReactor);
            //设置最大连接数
            connectionManager.setMaxTotal(100);
            //每一个route允许使用的连接数
            //举例来说,我们使用HttpClient的实现来分别请求 www.baidu.com 的资源和 www.bing.com 的资源那么他就会产生两个route
            connectionManager.setDefaultMaxPerRoute(1);
            CloseableHttpAsyncClient client = HttpAsyncClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();

            client.start();
            client.execute(get, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse httpResponse) {
                    try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line=null;
                        while((line=bufferedReader.readLine())!=null){
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Exception e) {
                    System.out.println(e);
                }

                @Override
                public void cancelled() {

                }
            });

        } catch (IOReactorException e) {
            e.printStackTrace();
        }




    }
}
