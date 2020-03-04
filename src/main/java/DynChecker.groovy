import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput
import groovyJCSP.MobileAgent
import jcsp.net2.NetChannel
import jcsp.net2.Node
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.net.InetAddress

class DynChecker implements CSProcess{
    ChannelOutput channelOutput
    ChannelInput channelInput
    def nodeID
    def toNBChan
    void run(){
        print("started+")
        println nodeID + " waiting to read"
        def job = channelInput.read()
        def connection =  new URL(job.toString()).openConnection()
        Scanner scanner = new Scanner(connection.getInputStream())
        scanner.useDelimiter("\\Z")
        def content = scanner.next()
        scanner.close()
        def result = (content =~ /GOOD|BAD/)[0]
        if(result == 'GOOD') {
            toNBChan.write('Node ' + nodeID + ' got bad result: ' + result)
            println('Node ' + nodeID + ' got bad result: ' + result)
        }else if(result == 'BAD'){
            println('Node ' + nodeID + ' got bad result: ' + result)
        }
        channelOutput.write("im done")
    }
}