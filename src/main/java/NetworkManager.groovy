import groovyJCSP.ALT
import jcsp.lang.CSProcess
import jcsp.lang.*


class NetworkManager implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toQuarantine
    ChannelInput fromQuarantine
    void run(){
        def jobs = []
        def alt = new ALT([fromParser, fromQuarantine])
        while(true) {
            def index = alt.fairSelect()
            switch (index){
                case 0:
                    println('i read a job')
                    jobs << fromParser.read()

                    break
                case 1:
                    def jobR = fromQuarantine.read()
                    if(jobR == "RequestJob"){
                        if(jobs.size() != 0) {
                            toQuarantine.write(jobs.pop())
                            break
                        }else{
                            toQuarantine.write('nojob')
                            break
                        }
                    }
                    break
            }
        }
    }
}
