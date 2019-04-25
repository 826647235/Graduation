import java.io.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Certify extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String userName = request.getParameter("UserName");
        String password = request.getParameter("Password");
        String code = request.getParameter("Code");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();

        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://us.nwpu.edu.cn/eams/login.action?username=" + userName + "&password=" + password + "&captcha_response=" + code;
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        HttpResponse httpResponse = client.execute(get);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity resEntity = httpResponse.getEntity();
            String message = EntityUtils.toString(resEntity, "utf-8");
            Document document = Jsoup.parse(message);
            Elements nameElement = document.getElementsByClass("person_title_1");
            Elements collegeElement = document.getElementsByClass("person_title_2");
            if(nameElement.size() == 0 && collegeElement.size() == 0) {
                out.write("Input Error");
            } else {
                String name = nameElement.get(0).ownText();
                String college = collegeElement.get(0).ownText();
                out.write(name + "      " +college);
            }
        } else {
            System.out.println("请求失败");
            out.write("Network Error");
        }
        out.flush();
        out.close();
    }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.write("Servlet is Ok");
        out.flush();
        out.close();
    }
}
