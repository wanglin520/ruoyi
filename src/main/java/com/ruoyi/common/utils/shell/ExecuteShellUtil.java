package com.ruoyi.common.utils.shell;

import cn.hutool.core.io.IoUtil;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * 执行shell命令
 *
 * @author: wangyg
 * @date: 2019/8/10
 */
@Slf4j
public class ExecuteShellUtil {

	private Vector<String> stdout;

	Session session;

	public ExecuteShellUtil(final String ipAddress, final String username, final String password, int port) {
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, ipAddress, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(3000);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===========初始化Session失败! {}", e.getMessage());
		}

	}

	/**
	 * 判断Session是否可连接
	 * @return
	 */
	public boolean isConnecte() {
		if(session != null && session.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public int execute(final String command) {
		int returnCode = 0;
		ChannelShell channel = null;
		PrintWriter printWriter = null;
		BufferedReader input = null;
		stdout = new Vector<String>();
		try {
			boolean isConnecte = session.isConnected();
			if(!isConnecte) {
				return -2;
			}
			channel = (ChannelShell) session.openChannel("shell");
			channel.connect();
			input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
			printWriter = new PrintWriter(channel.getOutputStream());
			printWriter.println(command);
			printWriter.println("exit");
			printWriter.flush();
			log.info("远程命令是: ");
			String line;
			while ((line = input.readLine()) != null) {
				stdout.add(line);
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return -1;
		}finally {
			IoUtil.close(printWriter);
			IoUtil.close(input);
			if (channel != null) {
				channel.disconnect();
			}
		}
		return returnCode;
	}

	public void close(){
		if (session != null) {
			session.disconnect();
		}
	}

	public String executeForResult(String command) {
		int num = execute(command);
		log.info("返回结果: {}", num);
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(stdout)) {
			for (String str : stdout) {
				sb.append(str);
			}
		}
		return sb.toString();
	}

}
