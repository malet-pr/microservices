package org.acme.simulator.simulations.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum JobType {
    A259HT4("S1"),
    B055BE1("S1"),
    C599HL8("S1"),
    D703TV2("S2"),
    A337PA8("S2"),
    C659RP5("S3"),
    F037NJ6("S3");

    public final String source;
    public static final Map<String, List<JobType>> BY_SOURCE = new HashMap<>();
    public static final List<String> NAMES = new ArrayList<>();

    JobType(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    static {
        for (JobType jobType : JobType.values()) {
            NAMES.add(jobType.name());
        }
    }

    static {
        List<JobType> ts1 = new ArrayList<>();
        List<JobType> ts2 = new ArrayList<>();
        List<JobType> ts3 = new ArrayList<>();
        for (JobType j : values()) {
            switch (j.source) {
                case "S1" -> ts1.add(j);
                case "S2" -> ts2.add(j);
                case "S3" -> ts3.add(j);
                default -> throw new IllegalArgumentException("Unknown source: " + j.source);
            }
        }
        BY_SOURCE.put("S1", ts1);
        BY_SOURCE.put("S2", ts2);
        BY_SOURCE.put("S3", ts3);
    }

}
