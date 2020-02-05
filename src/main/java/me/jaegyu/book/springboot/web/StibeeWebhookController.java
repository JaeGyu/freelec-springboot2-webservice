package me.jaegyu.book.springboot.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

// 52.78.132.66  스티비 IP

/*
*  {id=56391, action=UNSUBSCRIBED, actionType=MANUAL, subscribers=[{email=jg@XXX.com}]}
*
* id를 이용해서 IP와 id를 가자고 보안 처리를 한다.
* IP가 맞아야 하고 application.properties에 입력해놓은 id까지 맞아야 한다.
* */

@RestController
@Slf4j
public class StibeeWebhookController {

    private static final String STIBEE_IP = "52.78.132.66";

    @PostMapping(value = "/stibee", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String webHook(HttpServletRequest request, @RequestBody Map<String, Object> params) {


        System.out.println(">>> WebHook ");
        System.out.println(params);

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        System.out.println("ip :: " + ip);

        if (!STIBEE_IP.equals(ip)) {
            log.error("스티비의 웹훅이 아닙니다.");
            return "fail";
        }

        return "ok";
    }
}
