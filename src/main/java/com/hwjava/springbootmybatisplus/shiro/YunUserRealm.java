package com.hwjava.springbootmybatisplus.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hwjava.springbootmybatisplus.pojo.YunMenus;
import com.hwjava.springbootmybatisplus.pojo.YunUser;
import com.hwjava.springbootmybatisplus.service.LoginService;
import com.hwjava.springbootmybatisplus.service.YunMenusService;
import com.hwjava.springbootmybatisplus.service.YunUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义Realm
 */
public class YunUserRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(YunUserRealm.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private YunUserService yunUserService;

	@Autowired
	private YunMenusService yunMenusService;

	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//shiro 把用户名，密码，角色，菜单都可以存入PrincipalCollection集合中，供后面使用中直接获取
		YunUser  yunUser = (YunUser) principalCollection.getPrimaryPrincipal();//获取登录用户
		//从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();
		//通过角色r_id去查询左侧显示的菜单
		// 1.获取当前登录用户的r_id
		Long rId = yunUser.getRoleId();

		// 2.通过r_id去数据库查询，该用户的权限和显示菜单
		List<YunMenus> mList = yunMenusService.getMenusListByRoleId(rId);
		//session.setAttribute("menuList",mList);
		//把获取的权限添加到List保存
		List<String> authorizationList = new ArrayList<String>();

		for (YunMenus yunMenus : mList) {
			if(yunMenus.getPermissionCode()!=null&&!"".equals(yunMenus.getPermissionCode())) {
				authorizationList.add(yunMenus.getPermissionCode());
			}
		}

		//获取用户的权限列表
		Set<String> authorizationSet = new HashSet<String>();

		//session.setAttribute("permissionList",authorizationList);
		for (String author : authorizationList) {
			if(StringUtils.isNotEmpty(author)) {

				authorizationSet.addAll(Arrays.asList(author.trim().split(",")));
			}
		}
		/*SecurityUtils.getSubject().getSession().setAttribute("menuList", mList);
		SecurityUtils.getSubject().getSession().setAttribute("permissionList", authorizationList);*/
		// 存入到shiro的对象中去
		SimpleAuthorizationInfo   sa = new SimpleAuthorizationInfo();
		sa.setStringPermissions(authorizationSet);
		return sa;
	}

	/**
	 * 验证当前登录的Subject
	 * LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {


		// 查询用户信息
		String loginName = (String) authcToken.getPrincipal();
		// 用户密码
		String loginPassWord = new String((char[]) authcToken.getCredentials());

		// 查询用户的其他信息,查询用户是否存在
		YunUser yunUser = new YunUser();
		yunUser.setUserName(loginName);
		yunUser.setPassWord(loginPassWord);
		//yunAdmin.setAdminPassword(loginPassWord);

		// 为了方便直接使用mybatisplus的查询方式,如果使用的是mybatis那么此处的方法需要自己定义和研发
		QueryWrapper qw = new QueryWrapper();
		qw.setEntity(yunUser);
		YunUser user = yunUserService.getOne(qw);
		if (user == null) {
			throw new UnknownAccountException("账号不存在！");

		}
		//验证密码 ，运用MD5转换之后验证
		/*loginPassWord = new Md5Hash(loginPassWord).toString();

		if(!loginPassWord.equals(user.getPassword())) {
			throw new UnknownAccountException("密码错误！");
		} *///鉴权
		// 特殊情况，有的使用户是初始用户，无权限也不让登录
		if (user.getRoleId() == null || user.getRoleId() == 0 || "".equals(user.getRoleId())) {
			throw new UnknownAccountException("该账户未分配给具体的角色权限，请联系管理员！");
		}
		SimpleAuthenticationInfo sac = new SimpleAuthenticationInfo(user, loginPassWord, getName());
		return sac;
	}
}
