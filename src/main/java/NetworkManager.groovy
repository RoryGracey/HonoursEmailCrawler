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
        while(true) {
            def alt = new ALT([fromParser, fromQuarantine])
            def index = alt.fairSelect()
            switch (index){
                case 0:
                    jobs << fromParser.read()
                    toQuarantine.write('jobav')
                    break
                case 1:
                    def response = fromQuarantine.read()
                    toQuarantine.write(jobs.pop())
                    break
            }
            //def qResponse = fromQuarantine.read()
        }
    }
}
