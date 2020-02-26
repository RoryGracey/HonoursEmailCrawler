import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput
import groovyJCSP.MobileAgent
import jcsp.net2.NetChannel
import jcsp.net2.Node
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.net.InetAddress

class Checker implements CSProcess{
    ChannelOutput channelOutput
    ChannelInput channelInput
    ChannelOutput toNetworkBuffer
    int portNumber
    int vcnNum
    def nodeID
    void run(){
        def controllerIP  = "146.176.165.95"
        println("started")

        def toNb = new TCPIPNodeAddress(controllerIP, 3003)
        println('i did it')
        def toNBChan = NetChannel.any2net(toNb, 51)
        //def fromNM = NetChannel.net2one()
        //def fromNMLoc = fromNM.getLocation()
        println "got here"
        while(true) {
            println("im here")
            def job = channelInput.read()
            println job
            def connection =  new URL(job.toString()).openConnection()
            Scanner scanner = new Scanner(connection.getInputStream())
            scanner.useDelimiter("\\Z")
            def content = scanner.next()
            scanner.close()
            def result = (content =~ /GOOD|BAD/)[0]
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