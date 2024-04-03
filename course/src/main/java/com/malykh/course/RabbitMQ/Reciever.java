package com.malykh.course.RabbitMQ;

import com.malykh.course.controller.StudentController;
import lombok.Getter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Reciever {

    StudentController studentService;

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        String[] parts = message.split(",");
        long student_id = Long.parseLong(parts[0]);
        long course_id = Long.parseLong(parts[1]);

        studentService.removeStudentFromCourse(student_id, course_id);
        latch.countDown();
    }

}