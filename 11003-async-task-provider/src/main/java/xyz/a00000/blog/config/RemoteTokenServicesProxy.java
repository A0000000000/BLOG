package xyz.a00000.blog.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.a00000.blog.utils.ReflectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RemoteTokenServicesProxy extends RemoteTokenServices implements ResourceServerTokenServices {

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        String tokenName = (String) ReflectUtils.getValueByFieldName(this, "tokenName");
        String clientId = (String) ReflectUtils.getValueByFieldName(this, "clientId");
        String clientSecret = (String) ReflectUtils.getValueByFieldName(this, "clientSecret");
        String checkTokenEndpointUrl = (String) ReflectUtils.getValueByFieldName(this, "checkTokenEndpointUrl");
        AccessTokenConverter tokenConverter = (AccessTokenConverter) ReflectUtils.getValueByFieldName(this, "tokenConverter");
        Method getAuthorizationHeader = ReflectUtils.getMethodByName(getClass(), "getAuthorizationHeader");
        Method postForMap = ReflectUtils.getMethodByName(getClass(), "postForMap");
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add(tokenName, accessToken);
        HttpHeaders headers = new HttpHeaders();
        String param = "";
        try {
            param = (String) getAuthorizationHeader.invoke(this, clientId, clientSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        headers.set("Authorization", param);
        Map<String, Object> map = null;
        try {
            Map<String, Object> tmp = (Map<String, Object>) postForMap.invoke(this, checkTokenEndpointUrl, formData, headers);
            map = (Map<String, Object>) tmp.get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.containsKey("error")) {
            if (logger.isDebugEnabled()) {
                logger.debug("check_token returned error: " + map.get("error"));
            }
            throw new InvalidTokenException(accessToken);
        }
        if (!Boolean.TRUE.equals(map.get("active"))) {
            logger.debug("check_token returned active attribute: " + map.get("active"));
            throw new InvalidTokenException(accessToken);
        }
        return tokenConverter.extractAuthentication(map);
    }

}
