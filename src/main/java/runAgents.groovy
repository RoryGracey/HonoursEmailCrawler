import groovyJCSP.*
import jcsp.lang.*
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.nio.channels.NetworkChannel


def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def serverUserToParser = Channel.one2one()
def serverSubjectToParser = Channel.one2one()
def serverBodyToParser = Channel.one2one()
def resultToServer = Channel.one2one()
def server2receiver = Channel.one2one()
def Parser2Network = Channel.one2one()
def Network2Parser = Channel.one2one()
def QNetwork2Network = Channel.one2one()



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
    println(i)
    def checkerProcess = new Checker(channelInput: chan1[i].in(), channelOutput: chan2[i].out(), nodeID: i)
    checkerProcesses << checkerProcess
    clientsAvailable << checkerProcess.nodeID
}


def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def server = new Server(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), userOut: serverUserToParser.out(), subjectOut: serverSubjectToParser.out(), bodyOut: serverBodyToParser.out(), result: resultToServer.in(), toReciever: server2receiver.out())
def parser = new Parser(userIn: serverUserToParser.in(), subjectIn: serverSubjectToParser.in(), bodyIn: serverBodyToParser.in(), toNetworkManager: Parser2Network.out(), fromNetworkManager: Network2Parser.in(), channelOutput: resultToServer.out())
def NM = new NetworkManager(toParser: Network2Parser.out(), fromParser: Parser2Network.in(), toQuarantine: chan2[0].out(), fromQuarantine: QNetwork2Network.in())
def QNM = new QuarantineNetworkManager(fromNetworkManager: chan2[0].in(), toNetworkManager: QNetwork2Network.out(), fromChecker: chan2In, toChecker: chan1Out)
def emailReceiver = new EmailReciever(channelInput: server2receiver.in())


def network = [emailClient, server, parser, NM, QNM]


new PAR ( network + checkerProcesses ).run()