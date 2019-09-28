import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput

class EmailReciever implements CSProcess{
    ChannelInput channelInput
    void run(){
        def emailRecieved = channelInput.read()
        println("EMAIL RECEIVED")
        println(emailRecieved)
    }
}

