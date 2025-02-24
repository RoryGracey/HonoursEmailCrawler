import groovyJCSP.ALT
import groovyJCSP.ChannelOutputList
import jcsp.lang.CSProcess
import jcsp.lang.*
import jcsp.net2.*
import jcsp.net2.tcpip.TCPIPNodeAddress

class NetworkManager implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toQuarantine
    void run(){
        def qnmIDs = 1
        def qnmsConnected = 0
        def nodeAddr = new TCPIPNodeAddress(3002)
        Node.getInstance().init(nodeAddr)
        println "controller ip address = ${nodeAddr.getIpAddress()}"
        def fromQNMs = NetChannel.net2one()
        def fromQNMLoc = fromQNMs.getLocation()
        def qnmList = new ChannelOutputList()
        def qnmAmount = 1
        for (p in 0..<1) qnmList.append(null)
        def jobs = []
        if( qnmsConnected == 0) {
            def qnm = fromQNMs.read()
            qnmsConnected = qnmsConnected + 1
            def toQNM = NetChannel.one2net(qnm)
            qnmList[0] = toQNM
            println "Joined"
        }
        while(true) {
            def alt = new ALT([fromParser, fromQNMs])
            def index = alt.select()
            switch (index) {
                case 0:
                    jobs << fromParser.read()
                    break
                case 1:
                    println jobs
                    println "waiting from QNM"
                    def jobR = fromQNMs.read()
                    println "job request"
                    sleep(100)
                    if (jobs.size() != 0)
                        qnmList[0].write(jobs.pop())
                        println "Job sent"
                }
            }
        }
    }
