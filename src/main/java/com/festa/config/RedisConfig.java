package com.festa.config;

import com.festa.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host")
    private static String redisHost;

    @Value("${spring.redis.port")
    private static int redisPort;

    /*
    RedisConnectionFactory : 레디스 connection을 만들어 주는 인터페이스

    RedisConnectionFactory 인터페이스를 구현하여 레디스와 연결을 해주는 커넥터 역할을 하는 클래스는 대표적으로 JedisConnectionFactory와 LettuceConnectionFactory가 있다.
    Jedis : 사용하기 쉽지만, 멀티스레드 환경을 이용하기 위해선 커넥션 풀이 필요하고 thread-safe하지 않음.
            풀에 연결된 수가 늘어나면 응답 시간이 오래걸림. 만약 풀을 이용하지 않는다면 timeout이 발생
    Lettuce : netty기반 클라이언트로 멀티스레드 환경에서 thread-safe. Netty는 비동기 NIO를 사용하며, Jedis보다 빠른 속도를 보인다.

    스프링부트에서는 기본 의존성으로 lettuce를 사용하고 있어 thread-safe 하다.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
         return new LettuceConnectionFactory(redisHost, redisPort);
    }

    /*
    RedisTemplate : 레디스 데이터 접근 코드를 간단하게 해주는 클래스로, 객체 직렬화를 해주는 부가적인 기능이 있다.

    - 직렬화 : 객체의 내용을 바이트 단위로 변환하여 파일이나 네트워크를 통해 스트림(송수신)하도록 하는것
            객체 내용을 입출력 형식에 구애받지 않고, 객체를 파일에 저장할 수 있다. 쉽게 교환할 수 있다.
            하지만 역직렬화시 클래스 구조가 변경되는경우 java.io.InvalidClassException 에외가 발생한다.(직렬화, 역직렬화시 정보가 일치하지 않을 경우 발생)
            예외가 발생할 수 있으니 사용하는것을 권하지 않는다함
            또, 클레스의 메타정보도 가지고 있어 상대적으로 용량이 크다.(최소 2배, 최대 10배정도라고함)
            Redis와 같은 In-memory Database를 사용하는 중 트래픽이 늘어날 경우 문제가 생길 수 있다.
    setKeySerializer와 setValueSerializer를 이용하면 후에 조회시 key와 value가 정상적으로 보인다
     */
    @Bean
    public RedisTemplate<String, LoginDTO> redisTemplate() {
        RedisTemplate<String, LoginDTO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
