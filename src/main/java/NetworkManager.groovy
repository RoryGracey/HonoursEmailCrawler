import groovyJCSP.ALT
import jcsp.lang.CSProcess
import jcsp.lang.*


class NetworkManager implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toParser
    ChannelOutput toQuarantine
    ChannelInput fromQuarantine
    void run(){
        def jobs = []
        def alt = new ALT([fromParser, fromQuarantine])
        while(true) {
            println('looping')
            println(jobs)
            println('After select')
            def index = alt.fairSelect()
            println(index)
            switch (index){
                case 0:
                    println('Selected one')
                    jobs << fromParser.read()
                    println('read from parser')
                    toQuarantine.write('jobav')
                    break
                case 1:
                    println('Selected two')
                    def response = fromQuarantine.read()
                    println('read from Q')
                    toQuarantine.write(jobs.pop())
                    break
                default:
                    println('default')
                    break
            }
        }
    }
}
