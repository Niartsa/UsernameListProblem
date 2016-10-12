package com.usernamelist.username.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.usernamelist.util.Constants;
import com.usernamelist.util.file.FileAdministration;

public class UsernameGenerator {

	private static List<String> restrictedList = null;

	public List<String> generateListUsernames(final String username) {

		List<String> suggestedUsernames = null;
		for (int i = 0; i < Constants.NUMBERATTEMPTSSUGGESTUSERS; i++) {
			suggestedUsernames = generateUserNames(suggestedUsernames, username);
			if (null != suggestedUsernames) {
				suggestedUsernames = validateRestrictedList(suggestedUsernames);

				// Remove the repeated elements
				Set<String> suggestedUsernamesSet = new HashSet<>();
				suggestedUsernamesSet.addAll(suggestedUsernames);
				suggestedUsernames.clear();
				suggestedUsernames.addAll(suggestedUsernamesSet);

				if (suggestedUsernames.size() >= 14) {
					i = Constants.NUMBERATTEMPTSSUGGESTUSERS;
				}
			}
		}
		// Sort the elements
		Collections.sort(suggestedUsernames);

		return suggestedUsernames;
	}

	private List<String> generateUserNames(final List<String> suggestion, final String username) {
		List<String> suggestedUsernames = suggestion;
		if (null == suggestedUsernames) {
			suggestedUsernames = new ArrayList<>();
		}

		// This is a way to generate the usernames.
		if (suggestedUsernames.size() == 0) {
			suggestedUsernames.add(username.substring(0, 1) + username);
			suggestedUsernames.add(username + username);
			suggestedUsernames.add(username + Calendar.DAY_OF_YEAR);
			suggestedUsernames.add(username + Calendar.YEAR);
			suggestedUsernames.add(username + Calendar.DAY_OF_YEAR + Calendar.YEAR);
			suggestedUsernames.add(username + new StringBuffer(username).reverse().toString());
			suggestedUsernames.add(username + username.substring(0, (username.length() / 2)));
			suggestedUsernames.add(username.substring(0, 1) + "_" + username);
			suggestedUsernames.add(username + "_" + username);
			suggestedUsernames.add(username + "_" + Calendar.DAY_OF_YEAR);
			suggestedUsernames.add(username + "_" + Calendar.YEAR);
			suggestedUsernames.add(username + "_" + Calendar.DAY_OF_YEAR + Calendar.YEAR);
			suggestedUsernames.add(username + "_" + new StringBuffer(username).reverse().toString());
			suggestedUsernames.add(username + "_" + username.substring(0, (username.length() / 2)));
		} else {
			for (int i = suggestedUsernames.size(); i < Constants.NUMBERSUGGESTUSERS; i++) {
				suggestedUsernames.add(suggestedUsernames.get(suggestedUsernames.size()) + "_00" + i);
			}
		}
		return suggestedUsernames;
	}

	private List<String> validateRestrictedList(final List<String> suggestedList) {
		for (int i = 0; i < suggestedList.size(); i++) {
			if (!isValidUsername(suggestedList.get(i).trim())) {
				suggestedList.remove(i);
				i--;
			}
		}
		return suggestedList;
	}

	public boolean isValidUsername(final String username) {
		for (int i = 0; i < getRestrictedList().size(); i++) {
			if ((username.contains(getRestrictedList().get(i).trim()))) {
				return false;
			}
		}
		return true;
	}

	private List<String> getRestrictedList() {
		if (null == restrictedList) {
			restrictedList = FileAdministration.readFileInList(Constants.RESOURCES_RESTRICTED);
		}
		return restrictedList;
	}
}
