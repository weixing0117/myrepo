package com.atguigu.springcloud.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
//@AllArgsConstructor
@Data
@Accessors(chain=true)
public class Dept implements Serializable// entity --orm--- db_table
{
	private Long 	deptno; // 主键
	private String 	dname; // 部门名称
	private String 	db_source;// 来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库
	
	public Dept(String dname)
	{
		super();
		this.dname = dname;
	}
	//import org.springframework.web.multipart.commons.CommonsMultipartResolver;//
	
	//MultipartFile file = ((MultipartRequest) request).getFile("upfile");
	
	// 显示声明CommonsMultipartResolver为mutipartResolver  
/*    @Bean(name = "multipartResolver")  
    public MultipartResolver multipartResolver()  
    {  
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();  
        // resolver.setDefaultEncoding("UTF-8");  
        // resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常  
        // resolver.setMaxInMemorySize(40960);  
        resolver.setMaxUploadSize(10 * 1024 * 1024);// 上传文件大小 5M 5*1024*1024  
        return resolver;  
    }  
    //启动类上添加
    @EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})  
*/

	
}
