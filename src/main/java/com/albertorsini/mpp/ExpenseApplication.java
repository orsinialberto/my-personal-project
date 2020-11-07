package com.albertorsini.mpp;

import com.albertorsini.mpp.backend.kafka.MessageBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(MessageBinding.class)
@SpringBootApplication
public class ExpenseApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExpenseApplication.class, args);
  }

}
