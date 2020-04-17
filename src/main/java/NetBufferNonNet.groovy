import jcsp.lang.*
import groovyJCSP.*
import org.junit.rules.Stopwatch

import java.time.Duration
import java.time.Instant

class NetBufferNonNet implements CSProcess{
    ChannelInput fromCheckerProcesses
    ChannelOutput toReceiver
    void run() {
        Instant starts = Instant.now()
        while(true){

            def result = fromCheckerProcesses.read()
            if(result == 'DONE') {
                Instant ends = Instant.now()
                def dur = Duration.between(starts, ends).toString()
                def secstart = starts.getEpochSecond()
                def secends = ends.getEpochSecond()
                def duration = secends - secstart
                println(Duration: duration)
            }
            toReceiver.write(result)
        }
    }
}
