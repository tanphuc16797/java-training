package com.amit.springsercurity.util;

public interface Constant {
    interface HEADER{
        String TOKEN_PREFIX = "bearer ";
        String HEADER_TOKEN = "Authorization";
    }

    interface MESSAGE{
        interface VALIDATION{
            String REQUIRED = "This field is required";
        };
    }

    interface REDIS_HASH{
        String TOKEN = "amit_token";
        String ACTIVITY = "amit_activity";
    }

    interface MAIL_TEMPLATE{
        String WELCOME = "template/mail/greeting.vm";
    }
}
