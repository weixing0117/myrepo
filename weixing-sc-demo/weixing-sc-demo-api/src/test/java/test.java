package com.wx.t;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wx.bean.UserPO;
import com.wx.bean.UserVO;
import com.wx.service.IUserService;

@Controller
public class test {
	@Autowired
	private IUserService userService;

	@RequestMapping("login.test") // servlet根据请求找到该方法
	public ModelAndView getMessage(UserVO uservo,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		if(request.getSession().getAttribute("userSession")!=null){
			mv.setViewName("main");
		}else{
		UserPO userpo = new UserPO();
		userpo.setUsername(uservo.getUsername());
		userpo.setPassword(uservo.getPassword());
		boolean boo = userService.checkUser(userpo);
		if (boo) {
			userpo  = userService.findu(userpo);
			uservo.setNickname(userpo.getNickname());
			uservo.setAge(userpo.getAge());
			uservo.setUserPic(userpo.getUserPic());
			request.getSession().setAttribute("userSession", userpo);
			request.getSession().setAttribute("login", true);
			mv.setViewName("main");
		} else {
			mv.setViewName("login");
			}
		}
		return mv;
	}

	@RequestMapping("makeUser.test") // servlet根据请求找到该方法
	public ModelAndView getMessage2(UserVO uservo,@RequestParam("pic") MultipartFile file) {
		ModelAndView mv = new ModelAndView("");
		UserPO userpo = new UserPO();
		userpo.setUsername(uservo.getUsername());
		userpo.setPassword(uservo.getPassword());
		userpo.setAge(uservo.getAge());
		userpo.setNickname(uservo.getNickname());
		String picname = file.getOriginalFilename();
		String picadr = "";
		picadr = new Date().getTime()+picname.substring(picname.lastIndexOf("."));
		File newfile = new File("d:\\pic",picadr);
		try {
			 file.transferTo(newfile);
				userpo.setUserPic(picadr);
				userService.addUser(userpo);
				mv.addObject("message","保存成功");
				mv.setViewName("login");
		} 
		 catch (Exception e) { 
			 mv.addObject("message","保存失败");
			 mv.setViewName("zhuce");
		  e.printStackTrace(); 
		  }
		
		return mv;
	}

	@RequestMapping("checkUser.test") // servlet根据请求找到该方法
	@ResponseBody
	public UserVO checkuUer(UserVO uservo) {
		UserPO userpo = new UserPO();
		userpo.setUsername(uservo.getUsername());
		if (userService.findu(userpo) == null) {
			return null;
		} else {
			return uservo;
		}
	}

	@RequestMapping("upload.test")
	public ModelAndView upload(@RequestParam("filesName") MultipartFile[] file) {
		ModelAndView mv = new ModelAndView("zhuce");
		// 获得文件名
		for(int i = 0;i<file.length;i++){
		String fileName = file[i].getOriginalFilename();
		String where = "";
		where = fileName.substring(fileName.lastIndexOf(".") + 1);
		System.out.println("文件类型:" + file[i].getContentType());
		// 创建一个文件
		File newfile = new File("d:\\pic", new Date().getTime() + "." + where);
		 try { //把上传文件拷贝到本地 
			 file[i].transferTo(newfile); } 
		 catch (Exception e) { // TODO Auto-generated catch block
		  e.printStackTrace(); }
		}
		return mv;
	}

	/**
	 * 
	 * @return 键值对
	 */
	@RequestMapping("MVN33.test") // servlet根据请求找到该方法
	@ResponseBody
	public List<UserVO> getMessage3() {
		List<UserVO> list = new ArrayList<UserVO>();
		UserVO uservo = new UserVO();
		uservo.setUsername("asb");
		uservo.setPassword("333");
		uservo.setAge(18);
		uservo.setNickname("aaa");
		list.add(uservo);
		UserVO uservo1 = new UserVO();
		uservo1.setUsername("asb1");
		uservo1.setPassword("3331");
		uservo1.setAge(20);
		uservo1.setNickname("aaa1");
		list.add(uservo1);
		return list;
	}

	@RequestMapping(value = "{tag}/uservo/{name}.test", method = RequestMethod.GET) // servlet根据请求找到该方法
	@ResponseBody
	public List<UserVO> getMessage4(@PathVariable("tag") String tag, @PathVariable("name") String name) {
		List<UserVO> list = new ArrayList<UserVO>();
		UserVO uservo = new UserVO();
		uservo.setUsername("asb" + name);
		uservo.setPassword("333");
		uservo.setAge(18);
		uservo.setNickname("aaa" + name);
		list.add(uservo);
		UserVO uservo1 = new UserVO();
		uservo1.setUsername("asb1" + name);
		uservo1.setPassword("3331");
		uservo1.setAge(20);
		uservo1.setNickname("aaa1" + name);
		list.add(uservo1);
		if (tag.equals("a")) {
			list.add(uservo1);
		} else if (tag.equals("b")) {
			list.add(uservo);
		}
		return list;
	}

	// @RequestParam("name") 必传参数
	// http://localhost:8080/MMVVNN/MVN6.test?username=1
	@RequestMapping("MVN6.test")
	@ResponseBody
	public String paramString(@RequestParam("username") String name, String age) {
		System.out.println(name + age);
		return "666";
	}

}
