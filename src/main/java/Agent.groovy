import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput
import groovyJCSP.MobileAgent

class Agent implements MobileAgent{
    def ChannelOutput toLocal
    def ChannelInput fromLocal
    def result
    def connect( c ){
        this.toLocal = c[0]
        this.fromLocal = c[1]
    }
    def disconnect(){
        toLocal = null
        fromLocal = null
    }
    void run(){
        toLocal.write("Hello This is coming from agent")
        result = fromLocal.read()
    }

}