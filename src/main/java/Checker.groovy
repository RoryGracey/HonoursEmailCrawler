import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput
import groovyJCSP.MobileAgent
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.net.InetAddress

class Checker implements CSProcess{
    ChannelOutput channelOutput
    ChannelInput channelInput
    ChannelOutput toNetworkBuffer
    def nodeID
    void run(){
        def controllerIP = '146.176.77.200'
        def toNetBuffer = new TCPIPNodeAddress(controllerIP, 3005)
        def toNBChan = NetChannel.any2net(toNetBuffer, 51)
        while(true) {
            def job = channelInput.read()
            def connection =  new URL(job.toString()).openConnection()
            Scanner scanner = new Scanner(connection.getInputStream())
            scanner.useDelimiter("\\Z")
            def content = scanner.next()
            scanner.close()
            def result = (content =~ /GOOD|BAD/)[0]
            //println(result)
            // println(nodeID + ' did this job: ' + job)
            if(result == 'GOOD') {
                toNBChan.write('Node ' + nodeID + ': ' + result)
                sleep(1000)
                println('Node ' + nodeID + ' got bad result: ' + result)
            }else if(result == 'BAD'){
                println('Node ' + nodeID + ' got bad result: ' + result)
            }
        }
    }

}