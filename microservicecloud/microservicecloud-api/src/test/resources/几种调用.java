package rpcClient;

@XmlRootElement(name="list")
public class StudentList {
    
    List<Student> students;    //所有学生信息的集合
    1.@XmlRootElement，用于类级别的注解，对应xml的跟元素。通过name属性定义这个根节点的名称
    3.@XmlAttribute，用于把java对象的属性映射为xml的属性,并可通过name属性为生成的xml属性指定别名。

    4.@XmlElement，指定一个字段或get/set方法映射到xml的节点。通过name属性定义这个根节点的名称。

    5.@XmlElementWrapper，为数组或集合定义一个父节点。通过name属性定义这个父节点的名称。

    
    
    @XmlElement(name = "student")  
    public List<Student> getStudents() {
        return students;
    }
    
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    String name;  //姓名
    String sex;    //性别
    int number;     //学号
    String className;    //班级
    List<String> hobby;    //爱好
    
    public Student(){
    }
    public Student(String name,String sex,int number,
            String className,List<String> hobby) {
        this.name = name;
        this.sex = sex;
        this.number = number;
        this.className = className;
        this.hobby = hobby;
    }
    @XmlAttribute(name="name")  
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @XmlAttribute(name="sex")  
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    @XmlAttribute(name="number")  
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    
    @XmlElement(name="className")  
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    
    @XmlElementWrapper(name="hobbys")
    @XmlElement(name = "hobby")
    public List<String> getHobby() {
        return hobby;
    }
    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }
}


public static String sendSoapPost(String url, String xml,
		String contentType, String soapAction) {
	String urlString = url;
	HttpURLConnection httpConn = null;
	OutputStream out = null;
	String returnXml = "";
	try {
		httpConn = (HttpURLConnection) new URL(urlString).openConnection();
		httpConn.setRequestProperty("Content-Type", contentType);
		if (null != soapAction) {
			httpConn.setRequestProperty("SOAPAction", soapAction);
		}
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.connect();
		out = httpConn.getOutputStream(); // 获取输出流对象
		httpConn.getOutputStream().write(xml.getBytes("UTF-8")); // 将要提交服务器的SOAP请求字符流写入输出流
		out.flush();
		out.close();
		int code = httpConn.getResponseCode(); // 用来获取服务器响应状态
		String tempString = null;
		StringBuffer sb1 = new StringBuffer();
		if (code == HttpURLConnection.HTTP_OK) {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(),
							"UTF-8"));
			while ((tempString = reader.readLine()) != null) {
				sb1.append(tempString);
			}
			if (null != reader) {
				reader.close();
			}
		} else {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpConn.getErrorStream(),
							"UTF-8"));
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				sb1.append(tempString);
			}
			if (null != reader) {
				reader.close();
			}
		}
		// 响应报文
		returnXml = sb1.toString();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return returnXml;
}

public static void main(String[] args) {
	String url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";

	StringBuilder sb = new StringBuilder("");
	sb.append(
			"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
			.append("xmlns:web=\"http://WebXml.com.cn/\"><soapenv:Header/><soapenv:Body>")
			.append("<web:getSupportProvince/></soapenv:Body></soapenv:Envelope>");
	/*
	 * sb.append(
	 * "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
	 * ) .append(
	 * "xmlns:web=\"http://WebXml.com.cn/\"><soapenv:Header/><soapenv:Body><web:getSupportCity>"
	 * ) .append(
	 * "<web:byProvinceName>河北</web:byProvinceName></web:getSupportCity></soapenv:Body></soapenv:Envelope>"
	 * );
	 */
	String dataXml = sb.toString();
	String contentType = "text/xml; charset=utf-8";
	String soapAction = "http://WebXml.com.cn/getSupportProvince";
	// String soapAction =
	// "\"document/http://pengjunnlee.com/CustomUI:GetWeatherById\"";
	String resultXml = HttpUtil.sendSoapPost(url, dataXml, contentType,
			soapAction);
	System.out.println(resultXml);
	
	
	
	//String类型的数据(原格式为XML格式)
	String data = getXmlData(request);
	System.out.println(data);

    //将String类型转换成json类型
	 JSONObject object = XML.toJSONObject(data);
	 String jsonData = object.toString();		//因为gson.formJson(String,class)
	 System.out.println(jsonData);
    
    //截取字符串
    String jsonObject = jsonData.subString(jsonData.indexOf(":")+1, jsonData.length()-1)

    //将json格式的字符串转换成CertApplyRequest格式
	Gson gson = new Gson();
	CertApplyRequest Request = gson.fromJson(jsonObject, CertApplyRequest.class);

