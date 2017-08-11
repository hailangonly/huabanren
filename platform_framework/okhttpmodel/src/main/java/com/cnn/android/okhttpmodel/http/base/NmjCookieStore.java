package com.cnn.android.okhttpmodel.http.base;



import com.cnn.android.okhttpmodel.http.utils.CollectionUtils;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmj on 16/8/8
 */
public class NmjCookieStore implements CookieStore {

  private MemoryCookieStore mMemoryCookieStore;
  private SharePrefCookieStore mPrefCookieStore;

  public NmjCookieStore() {
    mMemoryCookieStore = new MemoryCookieStore();
    mPrefCookieStore = new SharePrefCookieStore();
  }

  @Override
  public void add(URI uri, HttpCookie cookie) {
    mMemoryCookieStore.add(uri, cookie);
    mPrefCookieStore.add(uri, cookie);
  }

  /**
   * 目前支持向顶级域名种 cookie ，无法取到子域下的 cookie。
   * 即: 如果向 account.ffan.com 下种 cookie。 内存缓存有可能取不到。
   * 因为内存缓存只要找到顶级域的 cookie 就会返回，不会再去硬盘缓存寻找子域的 cookie
   *
   * @param uri
   * @return
   */
  @Override
  public List<HttpCookie> get(URI uri) {
    List<HttpCookie> cookies = mMemoryCookieStore.get(uri);
    if (!CollectionUtils.isEmpty(cookies)) {
      return cookies;
    }
    cookies = mPrefCookieStore.get(uri);
    if (!CollectionUtils.isEmpty(cookies)) {
      for (HttpCookie cookie : cookies) {
        mMemoryCookieStore.add(uri, cookie);
      }
      return cookies;
    }
    cookies = new ArrayList<>();
    return cookies;
  }

  /**
   * 只从本地获取
   */
  @Override
  public List<HttpCookie> getCookies() {
    List<HttpCookie> cookies = mPrefCookieStore.getCookies();
    if (!CollectionUtils.isEmpty(cookies)) {
      return cookies;
    }
    cookies = new ArrayList<>();
    return cookies;
  }

  /**
   * 只从本地获取
   */
  @Override
  public List<URI> getURIs() {
    List<URI> uris = mPrefCookieStore.getURIs();
    if (!CollectionUtils.isEmpty(uris)) {
      return uris;
    }
    uris = new ArrayList<>();
    return uris;
  }

  @Override
  public boolean remove(URI uri, HttpCookie cookie) {
    mMemoryCookieStore.remove(uri, cookie);
    mPrefCookieStore.remove(uri, cookie);
    return true;
  }

  @Override
  public boolean removeAll() {
    mMemoryCookieStore.removeAll();
    mPrefCookieStore.removeAll();
    return true;
  }
}
