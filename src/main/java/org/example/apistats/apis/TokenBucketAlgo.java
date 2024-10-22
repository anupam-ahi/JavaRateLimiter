package org.example.apistats.apis;

import java.time.Duration;
import java.time.LocalTime;

public class TokenBucketAlgo {
    int tokens;
    int timeUnit;
    LocalTime lastCheck = LocalTime.now();
    public TokenBucketAlgo(int tokens, int timeUnit){
        this.tokens = tokens;
        this.timeUnit = timeUnit;
    }
    int tokensInBucket = tokens;
    public boolean handle(){
        LocalTime currentTime = LocalTime.now();
        Duration timePassed = Duration.between(this.lastCheck, currentTime);
        double tokensToAdd = (double) timePassed.getSeconds() * (this.tokens / (double) this.timeUnit);
        this.tokensInBucket += (int) tokensToAdd;
        if(this.tokensInBucket >= this.tokens){
            this.tokensInBucket = this.tokens;
        }
        this.lastCheck = currentTime;
        if(this.tokensInBucket < 1) return false;
        else{
            this.tokensInBucket = this.tokensInBucket - 1;
            return true;
        }
    }

}
