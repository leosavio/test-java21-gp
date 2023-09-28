package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.time.Duration;
import java.util.ArrayList;

import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


@SpringBootApplication
@EnableAsync
public class DemoApplication {

    @Autowired
    RedisConnectionFactory connectionFactory;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        try {

            String redisHost = env.getProperty("spring.data.redis.host");
            String redisPort = env.getProperty("spring.data.redis.port");
            
            System.out.println("Redis Host: " + redisHost);
            System.out.println("Redis Port: " + redisPort);

            String javat = env.getProperty("demo.java-thread");
            String virtualt = env.getProperty("demo.virtual-thread");
            
            System.out.println("Java Thread: " + javat);
            System.out.println("Virtual Thread: " + virtualt);

            connectionFactory.getConnection();
            System.out.println("Successfully connected to Redis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return template;
    }

}

@RestController
class CustomersHttpController { 

    @Autowired
    private Environment env;

	@PostMapping("/customers")
    Collection<Customer> customers(Customer customers) throws InterruptedException {
		//System.out.println(customers.getId());
        String javat = env.getProperty("demo.java-thread");
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(javat); i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(10));
                    
                } catch (InterruptedException e) {
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return Set.of(new Customer(1, "A"), new Customer(2, "B"), new Customer(3, "C"));
    }

    @GetMapping("/test")
    Collection<Customer> customersTest() throws InterruptedException {
        String virtualt = env.getProperty("demo.virtual-thread");
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(virtualt); i++) {
            Thread thread = Thread.startVirtualThread(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(10));
                } catch (InterruptedException e) {
                }
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }

        return Set.of(new Customer(1, "A"), new Customer(2, "B"), new Customer(3, "C"));
    }

    record Customer(Integer id, String name) {
    }

    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public AsyncTaskExecutor asyncTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

}