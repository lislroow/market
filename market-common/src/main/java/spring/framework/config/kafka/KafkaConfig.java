package spring.framework.config.kafka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import spring.framework.common.Constant;

@Configuration
@Conditional(KafkaProperties.class)
@EnableConfigurationProperties({KafkaProperties.class})
public class KafkaConfig {

  @Autowired
  KafkaProperties properties;
  
  // kafka-producer
  @Bean
  ProducerFactory<String, Object> producerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    
    ObjectMapper objectMapper = null;
    {
      objectMapper = new ObjectMapper();
      JavaTimeModule module = new JavaTimeModule();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);
      module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
      objectMapper.registerModule(module);
    }
    return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer(objectMapper));
  }
  
  @Bean
  KafkaTemplate<String, Object> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
  // --kafka-producer
  
  
  // kafka-consumer
  @Bean
  ConsumerFactory<String, Object> consumerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    config.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getAutoOffsetReset());
    config.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // [issue] the class '...' is not in the trusted packages
    
    ObjectMapper objectMapper = null;
    {
      objectMapper = new ObjectMapper();
      JavaTimeModule module = new JavaTimeModule();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);
      module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
      objectMapper.registerModule(module);
    }
    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer(objectMapper));
  }
  
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListener() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    //factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    return factory;
  }
  // --kafka-consumer
  
}