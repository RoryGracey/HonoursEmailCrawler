import groovyJCSP.*
import jcsp.lang.*
import jcsp.net.Node
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.nio.channels.NetworkChannel


def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def resultToServer = Channel.one2one()
def Parser2Network = Channel.one2one()
def Network2Parser = Channel.one2one()
def checkerBuffer = Channel.any2one()
//def QNMToNM = Channel.one2one()
def receiverNode = new TCPIPNodeAddress(3000)
jcsp.net2.Node.getInstance().init(receiverNode)
String hostAddress = receiverNode.getIpAddress()
println("Host Address: " + hostAddress)
def buffer2receiver = Channel.one2one()

def checkerNum = 4
def checkerProcesses = []
def clientsAvailable = []

def chan1 = Channel.one2oneArray(checkerNum + 2)
def chan1Out = new ChannelOutputList(chan1)
def chan1In = new ChannelInputList(chan1)


def chan2 = Channel.one2oneArray(checkerNum + 2)
def chan2Out = new ChannelOutputList(chan2)
def chan2In = new ChannelInputList(chan2)



for(i in 1 .. checkerNum){
    def checkerProcess = new Checker(channelInput: chan1[i].in(), channelOutput: chan2[i].out(), toNetworkBuffer: checkerBuffer.out(), nodeID: i)
    checkerProcesses << checkerProcess
    clientsAvailable << checkerProcess.nodeID
}


def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def parser = new Parser(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), toNetworkManager: Parser2Network.out())
def NM = new NetworkManager(fromParser: Parser2Network.in(), toQuarantine: chan2[0].out())
def QNM = new QuarantineNetworkManager(fromNetworkManager: chan2[0].in(), jobRequest: QNMToNM.out(), fromChecker: chan2In, toChecker: chan1Out)
def buffer = new NMBuffer(fromCheckerProcesses: checkerBuffer.in(), toReceiver: buffer2receiver.out())
def emailReceiver = new EmailReciever(channelInput: buffer2receiver.in())


def network = [emailClient, parser, NM, QNM, buffer, emailReceiver]


new PAR ( network + checkerProcesses ).run()