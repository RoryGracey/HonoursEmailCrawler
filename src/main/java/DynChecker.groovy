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
        println(nodeID + ' got job: ' + job)
        if(job == 'DONE') {
            toNBChan.write(job)
        }else {
            def connection = new URL(job.toString()).openConnection()
            Scanner scanner = new Scanner(connection.getInputStream())
            scanner.useDelimiter("\\Z")
            def content = scanner.next()
            scanner.close()
            def delay
            def result = (content =~ /GOOD:\d\d\d\d|BAD:\d\d\d\d|DONE/)[0]
            if (result) {
                def split = result.split(':')
                if (split[0] == 'GOOD') {
                    println(nodeID + ' is sleeping')
                    delay = Integer.parseInt(split[1].toString())
                    sleep(delay)
                    println(split[1])
                    toNBChan.write('Node ' + nodeID + ' got bad result: ' + result)
                    println('Node ' + nodeID + ' got bad result: ' + result)
                } else if (split[0] == 'BAD') {
                    delay = Integer.parseInt(split[1].toString())
                    sleep(delay)
                    println(nodeID + ' is sleeping')
                    println(split[1])
                    println('Node ' + nodeID + ' got bad result: ' + result)
                    toNBChan.write('Completed with bad')
                }
            }
        }
    }
}