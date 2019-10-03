import com.unknown.core.codec.digest.DigestUtils;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description :这是一个测试类
 * <br>@file_name   :Main.java
 * <br>@system_name :Main
 * <br>@author      :Administrator
 * <br>@create_time :2019/10/2 23:01
 * <br>@mender      :(Please add the modifier name)
 * <br>@Modified    :(Please add modification date)
 */
@Deprecated
public class Main {
    public static void main(String[] args) {
        System.out.println("php:"+DigestUtils.md5Hex("online_server_php".getBytes()).toUpperCase().substring(8, 24));
        System.out.println("admin:"+DigestUtils.md5Hex("online_server_admin".getBytes()).toUpperCase().substring(8, 24));
        System.out.println("mobile:"+DigestUtils.md5Hex("online_server_mobile".getBytes()).toUpperCase().substring(8, 24));
        System.out.println("proxy:"+DigestUtils.md5Hex("online_server_proxy".getBytes()).toUpperCase().substring(8, 24));
    }
}
