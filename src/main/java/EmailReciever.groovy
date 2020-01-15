import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput

class EmailReciever implements CSProcess{
    ChannelInput channelInput
    void run(){
        while(true) {
            def emailRecieved = channelInput.read()
            //println("EMAIL RECEIVED: " + emailRecieved)
        }
    }
}

