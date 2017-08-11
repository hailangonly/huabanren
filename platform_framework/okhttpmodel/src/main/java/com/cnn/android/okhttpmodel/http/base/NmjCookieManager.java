package com.cnn.android.okhttpmodel.http.base;



import com.cnn.android.okhttpmodel.http.utils.CollectionUtils;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 管理 cookie 负责读取，存储的调度
 *
 * Created by nmj on 16/8/8
 */
public class NmjCookieManager {

  public static final String RESPONSE_COOKIE_KEY = "Set-Cookie";
  public static final String REQUEST_COOKIE_KEY = "Cookie";

  private static NmjCookieManager sInstance;

  private CookieManager mCookieManager;
  private NmjCookieStore mWandaCookieStore;

  private NmjCookieManager() {
    mWandaCookieStore = new NmjCookieStore();
    mCookieManager = new CookieManager(mWandaCookieStore, CookiePolicy.ACCEPT_ALL);
  }

  public static synchronized NmjCookieManager getInstance() {
    if (sInstance == null) {
      sInstance = new NmjCookieManager();
    }
    return sInstance;
  }

  /**
   * 通过 volley 的返回，解析并存储 cookie
   *
   * @param uri
   * @param headers
   */
  public void saveCookie(URI uri, Map<String, List<String>> headers) {
    if (CollectionUtils.isEmpty(headers)) {
      return;
    }
    try {
      mCookieManager.put(uri, headers);
    } catch (IOException e) {
    }

    syncToOldCookieStore();
  }

  /**
   * 增加指定 uri 下的 cookie
   *
   * @param uri
   * @param cookie
   */
  public void saveCookie(URI uri, HttpCookie cookie) {
    mWandaCookieStore.add(uri, cookie);

    syncToOldCookieStore();
  }

  /**
   * 得到指定 uri 下的 cookie
   *
   * @param uri
   * @return
   */
  public List<HttpCookie> getCookieList(URI uri) {
    return mWandaCookieStore.get(uri);
  }


  /**
   * 得到向 request 中塞入的 cookie字符串 （是一个以 ; 分割多个 cookie 的字符）
   *
   * @param uri
   * @return
   */
  public String getCookieValue(URI uri) {
    if (uri == null) {
      return null;
    }
    Map<String, List<String>> cookieHeader = null;
    try {
      cookieHeader = mCookieManager.get(uri, new HashMap<String, List<String>>());
    } catch (IOException e) {
    }

    if (CollectionUtils.isEmpty(cookieHeader)
            || CollectionUtils.isEmpty(cookieHeader.get(REQUEST_COOKIE_KEY))) {
      return null;
    } else {
      return cookieHeader.get(REQUEST_COOKIE_KEY).get(0);
    }
  }

  /**
   * 得到 cookie 的 request 头
   *
   * @param uri
   * @return
   */
  public Map<String, List<String>> getCookieHeader(URI uri) {
    if (uri == null) {
      return null;
    }
    Map<String, List<String>> cookieHeader = null;
    try {
      cookieHeader = mCookieManager.get(uri, new HashMap<String, List<String>>());
    } catch (IOException e) {
    }
    return cookieHeader;
  }

  public void clearAllCookie() {
    mWandaCookieStore.removeAll();

    clearOldCookieStore();
  }


  /**
   * ----------------------   旧网络库同步的脏代码，待删除   ----------------------
   */

  private final Set<CookieSyncProcessor> mProcessors = new HashSet();

  /**
   * 旧的网络库向新的网络库注入 cookie
   *
   * @param uri
   * @param cookie
   */
  public void addFromOldCookieStore(URI uri, HttpCookie cookie) {
    mWandaCookieStore.add(uri, cookie);
  }

  public void addProcessor(CookieSyncProcessor processor) {
    synchronized (mProcessors) {
      mProcessors.add(processor);
    }
  }

  private void syncToOldCookieStore() {
    synchronized (mProcessors) {
      Iterator<CookieSyncProcessor> iterator = mProcessors.iterator();
      while (iterator.hasNext()) {
        CookieSyncProcessor processor = iterator.next();
        if (processor != null) {
          processor.syncCookie();
        } else {
          iterator.remove();
        }
      }
    }
  }

  private void clearOldCookieStore() {
    synchronized (mProcessors) {
      Iterator<CookieSyncProcessor> iterator = mProcessors.iterator();
      while (iterator.hasNext()) {
        CookieSyncProcessor processor = iterator.next();
        if (processor != null) {
          processor.clearCookie();
        } else {
          iterator.remove();
        }
      }
    }
  }

  public interface CookieSyncProcessor {

    void syncCookie();

    void clearCookie();
  }

}
