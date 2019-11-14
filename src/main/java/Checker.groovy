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
            println(nodeID + ' did this job: ' + job)
            channelOutput.write('Bad or Good')
        }
        //try {
            //def domainToCheck = 'google.co.uk'
            //def domainInetAddress = InetAddress.getByName(domainToCheck);
            //println("Domain information: ");
            //println(domainInetAddress);
            //toLocal.write("Hello This is coming from agent")
            //result = channelInput.read()
        //} catch (UnknownHostException uhe) {
        //    println('No Domain Found')
        //}

    }

}