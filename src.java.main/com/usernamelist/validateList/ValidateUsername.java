package com.usernamelist.validateList;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.usernamelist.entities.Result;
import com.usernamelist.username.impl.UsernameGenerator;
import com.usernamelist.util.Constants;
import com.usernamelist.util.ErrorMessages;
import com.usernamelist.util.StringUtils;
import com.usernamelist.util.file.FileAdministration;

@Service
public class ValidateUsername {

	final static Logger logger = Logger.getLogger(ValidateUsername.class.getName());

	public Result<Boolean, List<String>> checkUsername(String username) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(Constants.APPLICATION_CONTEXT);
		UsernameGenerator usernameGenerator = (UsernameGenerator) context.getBean(Constants.USERNAMEGENERATOR_BEAN);
		context.close();

		if (StringUtils.isValid(username)) {
			if (username.trim().length() >= Constants.USERNAMEMINIMUNSIZE) {
				if (usernameGenerator.isValidUsername(username)) {
					List<String> usernames = FileAdministration.readFileInList(Constants.RESOURCES_USERNAMES);
					if (null != usernames) {
						if (usernames.contains(username)) {
							List<String> suggestedList = usernameGenerator.generateListUsernames(username);
							return new Result<>(new Boolean(false), suggestedList);
						}
						if (FileAdministration.writeFile(Constants.RESOURCES_USERNAMES, username.trim())) {
							return new Result<>(new Boolean(true), null);
						}
						return new Result<>(new Boolean(false), null);
					}
					logger.error(ErrorMessages.ERROR_USERNAMELIST_NULL,
							new Exception(ErrorMessages.ERROR_USERNAMELIST_NULL));
					return new Result<>(new Boolean(false), null);
				}
				logger.error(ErrorMessages.ERROR_USERNAME_RESTRICTED,
						new Exception(ErrorMessages.ERROR_USERNAME_RESTRICTED));
				return new Result<>(new Boolean(false), null);
			}
			logger.error(ErrorMessages.ERROR_USERNAME_SHORT, new Exception(ErrorMessages.ERROR_USERNAME_SHORT));
			return new Result<>(new Boolean(false), null);
		}
		logger.error(ErrorMessages.ERROR_USERNAME_NULL, new Exception(ErrorMessages.ERROR_USERNAME_NULL));
		return new Result<>(new Boolean(false), null);
	}

	public static void main(String args[]) {
		Result<Boolean, List<String>> result = new ValidateUsername().checkUsername("Ronald");
		for (int i = 0; i < result.getA().size(); i++) {
			System.out.println("- " + result.getA().get(i));
		}
	}
}
