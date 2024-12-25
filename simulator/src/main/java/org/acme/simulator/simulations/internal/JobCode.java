package org.acme.simulator.simulations.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum JobCode {
    YY171T(JobType.A259HT4.name()),
    ZY074P(JobType.A259HT4.name()),
    CI069K(JobType.A259HT4.name()),
    VB733A(JobType.A259HT4.name()),
    VX528U(JobType.A259HT4.name()),
    MH453G(JobType.A259HT4.name()),
    UL915E(JobType.A259HT4.name()),
    SB741U(JobType.B055BE1.name()),
    XL844Q(JobType.B055BE1.name()),
    PP088M(JobType.B055BE1.name()),
    PA757Z(JobType.B055BE1.name()),
    YR808G(JobType.B055BE1.name()),
    EY945M(JobType.C599HL8.name()),
    TH504U(JobType.C599HL8.name()),
    WV831J(JobType.C599HL8.name()),
    TI843W(JobType.C599HL8.name()),
    MQ208B(JobType.D703TV2.name()),
    SM466I(JobType.D703TV2.name()),
    FY987O(JobType.D703TV2.name()),
    LW192Z(JobType.A337PA8.name()),
    RK286U(JobType.A337PA8.name()),
    AI743N(JobType.C659RP5.name()),
    FJ884M(JobType.C659RP5.name()),
    MK160K(JobType.F037NJ6.name()),
    FK113Z(JobType.F037NJ6.name());

    public final String jobType;
    public static final Map<String, List<JobCode>> BY_JOB_TYPE = new HashMap<>();

    JobCode(String type) {
        this.jobType = type;
    }

    public String getJobType() {
        return jobType;
    }

    static {
        List<JobCode> jc1 = new ArrayList<>();
        List<JobCode> jc2 = new ArrayList<>();
        List<JobCode> jc3 = new ArrayList<>();
        List<JobCode> jc4 = new ArrayList<>();
        List<JobCode> jc5 = new ArrayList<>();
        List<JobCode> jc6 = new ArrayList<>();
        List<JobCode> jc7 = new ArrayList<>();
        for (JobCode e : values()) {
            if(e.jobType.equals(JobType.A259HT4.name())) {
                jc1.add(e);
            } else if(e.jobType.equals(JobType.B055BE1.name())) {
                jc2.add(e);
            } else if(e.jobType.equals(JobType.C599HL8.name())) {
                jc3.add(e);
            } else if(e.jobType.equals(JobType.D703TV2.name())) {
                jc4.add(e);
            } else if(e.jobType.equals(JobType.A337PA8.name())) {
                jc5.add(e);
            } else if(e.jobType.equals(JobType.C659RP5.name())) {
                jc6.add(e);
            } else if(e.jobType.equals(JobType.F037NJ6.name())) {
                jc7.add(e);
            } else {
                throw new RuntimeException("Unknown job type: " + e.jobType);
            }
        }
        BY_JOB_TYPE.put(JobType.A259HT4.name(),jc1);
        BY_JOB_TYPE.put(JobType.B055BE1.name(),jc2);
        BY_JOB_TYPE.put(JobType.C599HL8.name(),jc3);
        BY_JOB_TYPE.put(JobType.D703TV2.name(),jc4);
        BY_JOB_TYPE.put(JobType.A337PA8.name(),jc5);
        BY_JOB_TYPE.put(JobType.C659RP5.name(),jc6);
        BY_JOB_TYPE.put(JobType.F037NJ6.name(),jc7);
    }
}

