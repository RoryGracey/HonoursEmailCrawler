import jcsp.lang.*

class NMBuffer implements CSProcess{
    ChannelInput fromCheckerProcesses
    ChannelOutput toReceiver
    void run() {
    while(true){
        def result = fromCheckerProcesses.read()
        //println('Got good from: ' + result)
        toReceiver.write(result)
    }
    }
}
