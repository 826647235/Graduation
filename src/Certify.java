import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String userName = request.getParameter("UserName");
        String password = request.getParameter("Password");
        String code = request.getParameter("Code");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://us.nwpu.edu.cn/eams/login.action?username=" + userName + "&password=" + password + "&captcha_response=" + code + "&session_locale=zh_CN";
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        HttpResponse httpResponse = client.execute(get);
        System.out.println(userName + " " + password);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        doPost(request, response);
        out.write("Servlet is Ok");
        out.flush();
        out.close();
    }
}
