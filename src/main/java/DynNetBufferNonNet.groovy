import java.time.Duration
import java.time.Instant
import jcsp.lang.*
import groovyJCSP.*

class DynNetBufferNonNet implements CSProcess{
    ChannelInput fromCheckerProcesses
    ChannelOutput toReceiver
    ChannelOutput toNM
    void run(){
        Instant starts = Instant.now()
        def procDone = '1'
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
            toNM.write(procDone)

        }}
}
