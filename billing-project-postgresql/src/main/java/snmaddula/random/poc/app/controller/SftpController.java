package snmaddula.random.poc.app.controller;

import static org.apache.commons.io.IOUtils.toByteArray;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@RestController
@RequestMapping("/sftp")
public class SftpController {

	private Resource prvkey;
	private String passphrase;
	private String username;
	private String host;
	private int port;
	
	@RequestMapping("/pull")
	public void pull(String file, HttpServletResponse res) throws Exception {
		String fileName = file.indexOf("/") >= 0 ? file.substring(file.lastIndexOf("/") + 1) : file;
		res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		res.getOutputStream().write(toByteArray(sftp().get(file)));
	}
	
	private ChannelSftp sftp() throws Exception {
		JSch jsch = new JSch();
		jsch.addIdentity(prvkey.getFile().getAbsolutePath(), passphrase);
		Session s = jsch.getSession(username, host, port);
		s.setConfig("StrictHostKeyChecking", "no");
		s.connect();
		Channel channel = s.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        return channelSftp;
	}
}
