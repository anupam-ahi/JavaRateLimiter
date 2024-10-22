package org.example.apistats.apis;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Create {
    TokenBucketAlgo tokenBucket = new TokenBucketAlgo(10, 2);
    @GetMapping("/greet")
    public String greetUser (HttpServletRequest request){
        if(tokenBucket.handle()){
            String userIP = getClientIpAddress(request);
            try{
            Thread.sleep(2);

            }catch(InterruptedException e){}
            return "Hello, user at: " + userIP;
        }else return "Too many requests. Please try again. ";
    }
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
