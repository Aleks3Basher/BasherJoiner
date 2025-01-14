package ru.basher.joiner.util;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

	@NotNull
	public static String toColor(@Nullable String legacyMsg) {
		if(legacyMsg == null) return "";
	    String message = legacyMsg.replace("§", "&");
	    Matcher matcher = Pattern.compile("&#[A-Fa-f0-9]{6}").matcher(message);
	    while (matcher.find()) {
	        String hex = matcher.group().substring(1);
	        message = message.replace("&" + hex, ChatColor.of(hex) + "");
	    }
	    message = message.replace("&", "§");
	    return message;
	}
	
}
