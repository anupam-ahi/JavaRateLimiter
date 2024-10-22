package org.example.apistats;

import jakarta.servlet.http.HttpServletRequest;
import org.example.apistats.apis.Create;
import org.example.apistats.apis.TokenBucketAlgo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest
class ApiStatsApplicationTests {
	private Create create;
	private TokenBucketAlgo tokenBucket;

	@BeforeEach
	public void setUp(){
		create = new Create();
		tokenBucket = new TokenBucketAlgo(2, 1);

	}
	@Test
	public void testGetClientIpAddress(){
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getHeader("x-forwarded-for")).thenReturn("192.168.1.100");
		String result = create.greetUser(request);
		Assertions.assertEquals("Too many requests. Please try again.", result);

	}
	@Test
	public void testGetClientIpAddress2(){
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getHeader("x-forwarded-for")).thenReturn(null);
		when(request.getRemoteAddr()).thenReturn("127.0.0.1");
		String result = create.greetUser(request);
		Assertions.assertEquals("Too many requests. Please try again.", result);

	}
	@Test
	public void testGetClientIpAddress3(){
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getHeader("x-forwarded-for")).thenReturn("unknown");
		when(request.getRemoteAddr()).thenReturn("10.0.0.1");
		String result = create.greetUser(request);
		Assertions.assertEquals("Too many requests. Please try again. ", result);

	}


}
