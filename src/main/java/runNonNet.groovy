import groovyJCSP.*
import jcsp.lang.*

def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def NB2Receiver = Channel.one2one()
def Parser2Network = Channel.one2one()
def QNetwork2Network = Channel.one2one()
def checker2Buffer = Channel.any2one()
def C2QNM = Channel.any2one()

def checkerNum = 2
def checkerProcesses = []
def clientsAvailable = []

def avCheckers = []

def chan1 = Channel.one2oneArray(checkerNum + 2)
def chan1Out = new ChannelOutputList(chan1)

def NM2QNM = Channel.one2one()

for(i in 1 .. checkerNum){
    def checkerProcess = new Checker(channelInput: chan1[i].in(), toNetworkBuffer: checker2Buffer.out(),
            nodeID: i, AvCheckers: avCheckers)
    checkerProcesses << checkerProcess
    clientsAvailable << checkerProcess.nodeID
}


def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def parser = new Parser(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), toNetworkManager: Parser2Network.out())
def NM = new NetworkManagerNonNet(fromParser: Parser2Network.in(), toQuarantine: NM2QNM.out(), fromQuarantine: QNetwork2Network.in())
def QNM = new QNMNonNet(fromNetworkManager: NM2QNM.in(), jobRequest: QNetwork2Network.out(), toChecker: chan1Out)
def NB = new NetBufferNonNet(fromCheckerProcesses: checker2Buffer.in(), toReceiver: NB2Receiver.out())
def emailReceiver = new EmailReciever(channelInput: NB2Receiver.in())


def network = [emailClient, parser, NM, QNM, NB, emailReceiver]


new PAR ( network + checkerProcesses ).run()