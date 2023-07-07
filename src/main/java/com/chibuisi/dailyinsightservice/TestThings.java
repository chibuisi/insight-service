package com.chibuisi.dailyinsightservice;

import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;

import java.util.*;

public class TestThings {
    public static void main(String[] args) {
        int count = countCharsInString("" +
                        "Dive into the captivating world of culture, where vibrant traditions, expressive arts, and shared values intertwine to create a tapestry of human experiences. Culture defines who we are and how we interact with the world. Uncover fascinating stories behind diverse cultural practices, rituals, and celebrations. Immerse yourself in music, art, and culinary delights reflecting the creativity of different cultures.\n" +
                "\n" +
                "Embracing cultural diversity fosters empathy, promotes cross-cultural understanding, and offers new perspectives. Through literature, folklore, and history, gain insights into collective experiences worldwide. Culture shapes identities, influences beliefs, and connects us to our roots. Engage with cultural expressions to expand horizons, challenge stereotypes, and promote inclusivity.\n" +
                "\n" +
                "Join us on a journey of discovery into the vast world of culture. Celebrate heritage, creativity, and resilience of communities. Explore language, arts, traditions, and heritage sites. Embrace the beauty of cultural diversity and forge meaningful connections across borders." +
                ""
        );
        System.out.println(count);
    }

    public static int countCharsInString(String text) {
        return text.length();
    }
}
