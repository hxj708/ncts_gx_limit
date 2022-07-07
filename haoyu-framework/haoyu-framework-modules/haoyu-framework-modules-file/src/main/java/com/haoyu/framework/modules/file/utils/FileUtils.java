package com.haoyu.framework.modules.file.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileUtils {

	@Value("${file.remote-http-domain}")
	private static String remoteHttpDomain;

	public static String getPreviewUrl(String url) {
		String previewDomain = "https://docview.mingdao.com/op/view.aspx?src={url}";
		url = (remoteHttpDomain + url).replace("://", "%3A%2F%2F").replaceAll("/", "%2F");
		return StringUtils.replace(previewDomain, "{url}", url);
	}

	public static String getFileSize(Long byteSize) {
		String[] units = { "B", "K", "M", "G", "T" };
		int index = 0;
		while (byteSize >= 1024) {
			if(index>=units.length-1){
				break;
			}
			byteSize = byteSize/1024;
			index++;
		}
		return byteSize+units[index];
	}

	public static String getFileSuffix(String path) {
		return path.substring(path.lastIndexOf(".") + 1);
	}
}
