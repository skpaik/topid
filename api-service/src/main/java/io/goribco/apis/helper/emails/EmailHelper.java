package io.goribco.apis.helper.emails;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.webclient.EmailServiceClient;
import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAsync
@Component
public class EmailHelper {
    private static final Logger logger = LoggerFactory.getLogger(EmailHelper.class);
    private final RestTemplate restTemplate;


    @Autowired
    private EmailServiceClient emailServiceClient;

    //    public EmailHelper(BeServiceExchangeClient beServiceExchangeClient) {
//        this.beServiceExchangeClient = beServiceExchangeClient;
//        this.restTemplate = new RestTemplate();
//    }
    public EmailHelper(EmailServiceClient emailServiceClient) {
        this.emailServiceClient = emailServiceClient;
        this.restTemplate = new RestTemplate();
    }

    public EmailHelper() {
        // this.employeeClient = employeeClient;
        this.restTemplate = new RestTemplate();
    }

    public void sendEmailAsync2(@Email String email, EmailEvent emailEvent) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadPool);

        ListenableFuture<Long> guavaFuture = service.submit(() ->
                processAndSendEmail2(email, emailEvent)
        );

        guavaFuture.get();
    }

    private long processAndSendEmail2(String email, EmailEvent emailEvent) throws InterruptedException {
        //SEND EMAIL BODY

        return 1;
    }

    public void sendEmailAsync(@Email String email, EmailEvent emailEvent) throws InterruptedException {
        CompletableFuture<List<String>> completableFuture = processAndSendEmail(email, emailEvent.ordinal());

        // Wait until they are all done
        CompletableFuture.allOf(completableFuture).join();
    }

    @Async
    public CompletableFuture<List<String>> processAndSendEmail(String email, int emailEvent) throws InterruptedException {
        //  List<String> emailSend2 = this.beServiceExchangeClient.emailSend(email, emailEvent);
        List<String> emailSend = emailServiceClient.emailSend(email, emailEvent);

        /*
        logger.info("Looking up " + email);
        String url = String.format("https://api.github.com/users/%s", email);
        UserData results = restTemplate.getForObject(url, UserData.class);
        */

        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);

        return CompletableFuture.completedFuture(emailSend);
    }

    @Async
    public CompletableFuture<UserData> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        UserData results = restTemplate.getForObject(url, UserData.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }
}
