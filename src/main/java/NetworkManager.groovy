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
            println('looping')
            println(jobs)
            println('After select')
            def index = alt.select()
            println(index)
            switch (index){
                case 0:
                    jobs << fromParser.read()
                    println('read from parser')
                    break
                case 1:
                    def jobR = fromQuarantine.read()
                    if(jobR == "requestJob"){
                        println('read from Q')
                        if(jobs.size() != 0) {
                            toQuarantine.write(jobs.pop())
                        }else{
                            break
                        }
                        break
                    }
                    break
            }
        }
    }
}
