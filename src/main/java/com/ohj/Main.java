package com.ohj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * @author Hipopaaaaa
 * @create ${DATE} ${TIME}
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        LocalDateTime befor = LocalDateTime.of(2000, 7, 4,0,0,0);
        LocalDateTime now = LocalDateTime.now();
        System.out.println((now.toEpochSecond(ZoneOffset.UTC)-befor.toEpochSecond(ZoneOffset.UTC))/24/60/60);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
        String befs = befor.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        String nows = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        System.out.println(befs);
        Date date = simpleDateFormat.parse(befs);
        Date date2 = simpleDateFormat.parse(nows);
        long gap=date2.getTime()-date.getTime();
        System.out.println((gap/24/60/60/1000));

        System.out.println(ChronoUnit.DAYS.between(befor, now));
    }
}