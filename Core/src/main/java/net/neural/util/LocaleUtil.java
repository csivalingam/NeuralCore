package net.zfp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class LocaleUtil {

  private transient static final Logger LOG = LoggerFactory.getLogger(LocaleUtil.class);

  public static String getDateTimeFormat(int dateStyle, int timeStyle, Locale locale, Date date) {
    DateFormat dateFormat = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
    return dateFormat.format(date);
  }

  public static Locale getLocale(HttpServletRequest request) {
	  return request.getLocale();
	  //return RequestContextUtils.getLocale(request);
  }

  public static Locale getLocale(String localeString) {
    Locale locale = null;

    if (localeString != null) {
      String[] localeArray = localeString.split("_");

      if (localeArray.length == 1) {
        locale = new Locale(localeArray[0]);
      }
      else if (localeArray.length == 2) {
        locale = new Locale(localeArray[0], localeArray[1]);
      }
    }
    return locale;
  }

}