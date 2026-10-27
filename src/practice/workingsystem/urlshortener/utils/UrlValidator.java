package practice.workingsystem.urlshortener.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public class UrlValidator {
    public boolean isValid(String url) {
        if(url == null || url.isBlank())
            return false;

        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();

            if(scheme == null)
                return false;

            scheme = scheme.toLowerCase(Locale.ROOT);
            if(!scheme.equals("http") && !scheme.equals("https"))
                return false;

            if(!uri.isAbsolute() || (uri.getHost() == null)) {
                return false;
            }

            return true;

        } catch (URISyntaxException exception) {
            return false;
        }
    }
}
