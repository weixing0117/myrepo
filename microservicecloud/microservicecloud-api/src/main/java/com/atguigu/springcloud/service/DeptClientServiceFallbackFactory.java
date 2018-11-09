package com.atguigu.springcloud.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.springcloud.entities.Dept;

import feign.hystrix.FallbackFactory;

@Component // 不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService>
{
	@Override
	public DeptClientService create(Throwable throwable)
	{
		return new DeptClientService() {
			@Override
			public Dept get(long id)
			{
				return new Dept().setDeptno(id).setDname("该ID：" + id + "没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
						.setDb_source("no this database in MySQL");
			}

			@Override
			public List<Dept> list()
			{
				return null;
			}

			@Override
			public boolean add(Dept dept)
			{
				return false;
			}
		};
	}
	
	@RequestMapping("api/baidu")
	public String getBaidu() throws Exception {
	//	URL url1 = new URL("http://www.baidu.com");
		URL url1 = new URL("https://collector.githubapp.com/collect");
	//	HttpURLConnection httpconn = (HttpURLConnection) url1.openConnection();
		HttpsURLConnection httpconn = (HttpsURLConnection) url1.openConnection();
		
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
		
		String jsonStr = null;
		httpconn.setDoInput(true);
		httpconn.setDoOutput(true);
		httpconn.setRequestMethod("POST");
		if(null!=jsonStr) {
			OutputStream os = httpconn.getOutputStream();
			os.write(jsonStr.getBytes());
			os.close();
		}
		InputStream is = httpconn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,"utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer bf = new StringBuffer();
		String str = null;
		bf.append("<div><p><i style='font-size:xx-large'>好大一个<i></p></div>");
		while (null!=(str=br.readLine())) {
			bf.append(str);
		}
		JSONObject json = JSONObject.parseObject(bf.toString());
		System.out.println(json.toJSONString());
		return bf.toString();
	}
}
