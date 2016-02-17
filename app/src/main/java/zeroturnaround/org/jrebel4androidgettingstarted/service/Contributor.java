package zeroturnaround.org.jrebel4androidgettingstarted.service;

/**
 * Created by shelajev on 16/12/15.
 */
public class Contributor {

    String login;
    int contributions;
    String avatar_url;


    public String getLogin() {
        return login;
    }

    public int getContributions() {
        return contributions;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    @Override
    public String toString() {
        return login;
    }
}
