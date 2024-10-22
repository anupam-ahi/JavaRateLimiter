package org.example.apistats;
import jakarta.servlet.http.HttpServletRequest;
import org.example.apistats.apis.Create;
import org.example.apistats.apis.TokenBucketAlgo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

public class mocktesfile1 {
    @Mock
    private TokenBucketAlgo tokenBucket;
    @Mock
    private HttpServletRequest request;
    @InjectMocks
    private Create createController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGreetUserWithValidRequest(){
        when(tokenBucket.handle()).thenReturn(true);
        when(request.getHeader("x-forwarded-for")).thenReturn("192.168.1.1");
    }
    @Test
    public void testGreetUserWithTooManyRequests(){
        when(tokenBucket.handle()).thenReturn(false);
        String response = createController.greetUser(request);
        Assertions.assertEquals("Too many requests. Please try again.", response);


    }
    @Test
    public void testGetClientIpAddressWithForwardHeader(){
        when(request.getHeader("x-forwarded-for")).thenReturn("192.168.1.1");
        String response = createController.getClientIpAddress(request);
        Assertions.assertEquals("192.168.1.1", response);


    }
    @Test
    public void testGetClientIpAddressWithoutForwardedHeader(){
        when(request.getHeader("x-forwarded-for")).thenReturn("127.0.0.1");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        String ipAddress = createController.getClientIpAddress(request);
        Assertions.assertEquals("127.0.0.1", ipAddress);

    }

}
