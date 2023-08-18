package io.goribco.apis.tempu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.goribco.apis.model.profile.ProfileCreateReq;
import io.goribco.apis.utils.DateTimeUtil;
import org.springframework.util.ResourceUtils;

import java.nio.file.Path;
import java.rmi.server.UID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class DemoMain {
    private static final AtomicLong counter = new AtomicLong(0);
    private static final AtomicReference<Long> currentTime =
            new AtomicReference<>(System.currentTimeMillis());
    private static final AtomicReference<Long> currentTime3 = new AtomicReference<>(System.currentTimeMillis());

    public static void main(String[] args) {

        readJson();
        // dateImpl();
        // nextIdImpl();
    }

    public static void readJson() {
        // ProfileCreateReq person = new ProfileCreateReq();

        ProfileCreateReq person = json2Java("ProfileCreateReq.json", ProfileCreateReq.class);
        System.out.println(person.getLocation());
    }

    public static <T> T json2Java(String fileName, Class<T> classType) {
        T t = null;

        try {
            Path path = ResourceUtils.getFile("api-service/src/test/resources/" + fileName).toPath();
            //  File file = new File(path.toFile());

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            t = mapper.readValue(path.toFile(), classType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    private static void dateImpl() {
        //Converting milliseconds to Date using java.util.Date
        //current time in milliseconds
        long currentDateTime = DateTimeUtil.getMilliSec();

        //creating Date from millisecond
        Date currentDate = new Date(currentDateTime);

        //printing value of Date
        System.out.println("current Date: " + currentDate);

        DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");

        //formatted value of current Date
        System.out.println("Milliseconds to Date: " + df.format(currentDate));

        //Converting milliseconds to Date using Calendar
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentDateTime);
        System.out.println("Milliseconds to Date using Calendar:"
                + df.format(cal.getTime()));

        //copying one Date's value into another Date in Java
        Date now = new Date();
        Date copiedDate = new Date(now.getTime());

        System.out.println("original Date: " + df.format(now));
        System.out.println("copied Date: " + df.format(copiedDate));
    }

    private static void nextIdImpl() {
        System.out.println("nextId() START");
        System.out.println(nextId());
        System.out.println(nextUID());
        System.out.println(nextUUID());
        System.out.println(nextId2());
        System.out.println(nextId3());
        System.out.println("nextId() END");
    }

    public static long nextId() {
        return counter.incrementAndGet();
    }

    public static String nextUID() {
        return new UID().toString();
    }

    public static String nextUUID() {
        return UUID.randomUUID().toString();
    }

    public static Long nextId2() {
        Long prev;
        Long next = DateTimeUtil.getMilliSec();
        do {
            prev = currentTime.get();
            next = next > prev ? next : prev + 1;
        } while (!currentTime.compareAndSet(prev, next));
        return next;
    }

    public static Long nextId3() {
        return currentTime3.accumulateAndGet(DateTimeUtil.getMilliSec(),
                (prev, next) -> next > prev ? next : prev + 1);
    }
}
