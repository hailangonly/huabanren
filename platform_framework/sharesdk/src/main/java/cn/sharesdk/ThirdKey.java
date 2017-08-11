package cn.sharesdk;

/**
 * Created by mj 16/5/14.
 */
public interface ThirdKey {
    static String SHARESDK_KEY="12ce342ec3992";
    static String SHARESDK_SECRET="5b20dc84f727b28b6d05a904ea1a1c4f";
    static String SHARESDK_SHARE="1c5d8d98b6400";

    static String WEIXIN_KEY="wxfedc5f54067c5874";
    static String WEIXIN_SECRET="ccbfb6c3d11dee6feebe6d475b3a3014";
    static final String QQ_SCOPE = "all";

    public static final String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";//


    static String QQ_KEY="1104786818";
    static String QQ_SECRET="Mv9yFG3RJ5dYzsHC";

    /// <<< 微信回调
    public static final String ACTION_WEIXIN_CALLBACK = "com.financial360.nicaifu.share.action.WEIXIN_CALLBACK";
    public static final String EXTRA_WEIXIN_RESULT = "weixin_result";
}
