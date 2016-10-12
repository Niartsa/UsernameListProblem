package com.usernamelist.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.usernamelist.util.ErrorMessages;
import com.usernamelist.util.StringUtils;

public class FileAdministration {

	final static Logger logger = Logger.getLogger(FileAdministration.class.getName());

	/**
	 * Read a file and return a list of string (for each line).
	 *
	 * @param The
	 *            file to be read
	 * @return A List<String> with all the lines of the file document.
	 */
	public static final List<String> readFileInList(final String file) {

		List<String> list = new ArrayList<>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			if (StringUtils.isValid(file)) {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String cadena = null;
				while ((cadena = br.readLine()) != null) {
					list.add(cadena.trim());
					cadena = null;
				}
			}
		} catch (FileNotFoundException e) {
			list = null;
			logger.error(ErrorMessages.ERROR_READING_FILE, e);
		} catch (IOException e) {
			list = null;
			logger.error(ErrorMessages.ERROR_READING_FILE, e);
		} finally {
			try {
				if (null != br) {
					br.close();
				}
			} catch (IOException e) {
				logger.error(ErrorMessages.ERROR_CLOSING_BUFFERR, e);
			}
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (IOException e) {
				logger.error(ErrorMessages.ERROR_CLOSING_READER, e);
			}
		}
		return list;
	}

	/**
	 * Add a new username in a new line in the file
	 *
	 * @param file
	 *            The file where it will be added the new username
	 * @param user
	 *            The user to be added
	 * @return
	 */
	public static final boolean writeFile(final String file, final String user) {
		File archivo = new File(file);
		BufferedWriter bw = null;
		try {
			if (archivo.exists()) {
				bw = new BufferedWriter(new FileWriter(archivo, true));
				bw.append(user);
				bw.newLine();
				return true;
			}
			return false;
		} catch (IOException e) {
			logger.error(ErrorMessages.ERROR_WRITTING_FILE, e);
			return false;
		} finally {
			try {
				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {
				logger.error(ErrorMessages.ERROR_CLOSING_BUFFERW, e);
			}
		}
	}
}
