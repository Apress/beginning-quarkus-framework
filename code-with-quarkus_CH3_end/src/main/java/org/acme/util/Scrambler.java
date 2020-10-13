package org.acme.util;

import io.netty.util.internal.ThreadLocalRandom;

import java.security.SecureRandom;

public class Scrambler {



    public static String scramble(String valueToScramble) {
        char[] options = valueToScramble.toCharArray();
        int[] positions = new int[options.length];
        ThreadLocalRandom randomizer = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();
        int next = 0;
        for (int i = 0; i < options.length; i++) {
            do {
                next = randomizer.nextInt(valueToScramble.length());
                if (positions[next] == 0) {
                    sb.append(options[next]);
                    positions[next]++;
                    break;
                }
            }while(positions[next] > 0);
        }
        return sb.toString();
    }

}
