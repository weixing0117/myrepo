package rpcClient;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import pojo.Req;
import pojo.Res;

/**
 *
 *
 */
public class SxClient {

	/**
	 * main
	 *
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws AxisFault {
		
		//准备工作
        RPCServiceClient serviceClient = new RPCServiceClient();//使用RPC方式创建客户端对像
        Options options = serviceClient.getOptions();			//创建options对象
        EndpointReference targetEPR = new EndpointReference(	//指定调用WebService的URL
                "http://localhost:1234/axis2/services/myService");
        options.setTo(targetEPR);								//设置选项
        //声明变量
        Object[] opAddEntryArgs = null;	
        Class[] classes = null;			
        QName opAddEntry = null;		
        
        //调用InsertSxInfo方法
        opAddEntryArgs = new Object[] {reqXml1("111111")};
        classes = new Class[] {String.class};
        opAddEntry = new QName("http://rpcService", "InsertSxInfo");
        Object [] obj = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
        //打印结果
        System.out.println(obj[0]);
        
        try {
            JAXBContext context = JAXBContext.newInstance(Res.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Res u = (Res)unmarshaller.unmarshal(new StringReader(obj[0].toString()));
            //返回的东西
            System.out.println("状态:"+u.getData().getZt()+","+u.getResult().getMessage()+","+u.getResult().getCode()); 
        } catch (JAXBException e) {
            e.printStackTrace();  
        }
        
	}
	
	
	 private static String reqXml1(String epcId) {
			StringWriter sw = new StringWriter();
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                String str = sdf.format(new Date());
	                System.out.println(str+"--------");
	 		Req req = new Req("hunan_auth_code",epcId,"1","S6000","192.168.1.178",str);
	        try {
	           JAXBContext context = JAXBContext.newInstance(Req.class); 
	           Marshaller marshaller = context.createMarshaller();
	           marshaller.setProperty(marshaller.JAXB_ENCODING, "GBK");//设置传递xml编码
	           marshaller.marshal(req, sw);
//	           System.out.println("-----封装的xml-------"+sw.toString());
	         } catch (JAXBException e) {  
	             e.printStackTrace();  
	         }
	         return sw.toString();
		}
	 

}
