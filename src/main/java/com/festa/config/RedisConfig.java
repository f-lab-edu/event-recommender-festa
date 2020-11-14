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
    RedisConnectionFactory : Redis와 연결
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
         return new LettuceConnectionFactory(redisHost, redisPort);
    }

    /*
    RedisTemplate : 객체 직렬화를 해줌
    직렬화 : 객체의 내용을 바이트 단위로 변환하여 파일이나 네트워크를 통해 스트림(송수신)하도록 하는것
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
