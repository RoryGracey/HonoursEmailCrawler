import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput
import groovyJCSP.MobileAgent
import java.net.InetAddress

class Checker implements CSProcess{
    ChannelOutput channelOutput
    ChannelInput channelInput
    def result
    void run(){
        println('running')
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