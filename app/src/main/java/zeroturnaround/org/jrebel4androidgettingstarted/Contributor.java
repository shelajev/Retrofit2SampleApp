package zeroturnaround.org.jrebel4androidgettingstarted;

/**
 * Created by shelajev on 16/12/15.
 */
public class Contributor {

    String login;
    String html_url;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")";
    }
}
