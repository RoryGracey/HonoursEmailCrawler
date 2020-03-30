import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput

class Cons implements CSProcess{
    def ChannelInput inChannel
    void run(){
        def i = 100
        while(i < 100){
            i = inChannel.read()
            println("The value was: " + i)
        }
        println("Done")
    }
}