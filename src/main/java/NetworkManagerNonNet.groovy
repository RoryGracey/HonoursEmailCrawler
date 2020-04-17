import jcsp.lang.*
import groovyJCSP.*

class NetworkManagerNonNet implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toQuarantine
    ChannelInput fromQuarantine
    void run(){
        def jobs = []
        def alt = new ALT([fromParser, fromQuarantine])
        while(true) {
            def index = alt.select()
            switch (index){
                case 0:
                    jobs << fromParser.read()
                    break
                case 1:
                    def jobR = fromQuarantine.read()
                    if (jobs.size() != 0) {
                        toQuarantine.write(jobs.pop())
                    }else if(jobs.size() == 0 ){
                        toQuarantine.write('NOJ')
                    }
                    break
            }
        }
    }
}
