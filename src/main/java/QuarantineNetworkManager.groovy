import jcsp.lang.*
import groovyJCSP.*
import java.util.Random

class QuarantineNetworkManager implements CSProcess {
    ChannelInput fromNetworkManager
    ChannelInput fromChecker
    ChannelOutput checkerReady
    ChannelOutput toNetworkManager
    void run(){
        def checkerNum = 3
        def cList = []
        def checkerProcesses = []
        def clientsAvailable = []
        // loop through adding all processes to checkProcesses list
        for(i in 0 .. checkerNum){
            def c = Channel.one2one()
            cList << c
            // not sure if i have to define these channels when creating the new process?
            checkerProcesses << new Checker(channelInput: c.in(), channelOutput: c.out(), nodeID: i)
        }
        def n = checkerProcesses.size()
        for(i in 0..n){
            clientsAvailable.add(checkerProcesses[i])
        }4
        // run the available clients which is going to be all of them
        new PAR(clientsAvailable).run()
        println(cList)
        def alt = new ALT(cList)
        def index = alt.select()
        while(true){
            println(index)
        }


        // IGNORE THIS
        fromNetworkManager.read()
        Random rand = new Random()
        def num = rand.nextInt(1)
        if(num == 0){
            toNetworkManager.write('OK')
        }else{
            toNetworkManager.write('BAD')
        }
    }
}
