import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput
import groovyJCSP.MobileAgent
import java.net.InetAddress

class Checker implements CSProcess{
    ChannelOutput channelOutput
    ChannelInput channelInput
    ChannelOutput toQNM
    def nodeID
    void run(){
        while(true) {
            channelOutput.write('Ready')
            def job = channelInput.read()
            def connection =  new URL(job.toString()).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            def content = scanner.next();
            scanner.close();
            def result = (content =~ /GOOD|BAD/)[0]
            //println(result)
            // println(nodeID + ' did this job: ' + job)
            channelOutput.write('Node ' + nodeID + ': ' + result)
        }
    }

}