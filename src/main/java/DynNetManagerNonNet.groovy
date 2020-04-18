import groovyJCSP.*
import jcsp.lang.*

class DynNetManagerNonNet implements CSProcess{
    ChannelInput fromParser
    ChannelInput fromQuarantine
    ChannelInput fromNB
    ChannelOutput toQuarantine
    def done = 0
    void run(){
        def jobs = []
        def alt = new ALT([fromParser, fromQuarantine, fromNB])
        while(true) {
            def index = alt.select()
            switch (index){
                case 0:
                    jobs << fromParser.read()
                    break
                case 1:
                    def jobR = fromQuarantine.read()
                    if(jobR != 'NOC' && jobs.size() != 0){
                        println('this is true')
                        toQuarantine.write(jobs.pop() + '-' + done)
                    }else {
                        def preparedStatement = 'NOJ-' + done.toString()
                        toQuarantine.write(preparedStatement)
                        if(done > 0 )
                            done = done - 1
                    }
                    break
                case 2:
                    def newd = fromNB.read()
                    def toInt =  Integer.parseInt(newd.toString())
                    done = done + toInt
                    //println(done)
                    break
            }
        }
    }
}
