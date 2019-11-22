import groovyJCSP.*
import jcsp.lang.*
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.nio.channels.NetworkChannel


def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def resultToServer = Channel.one2one()
def Parser2Network = Channel.one2one()
def Network2Parser = Channel.one2one()
def QNMToBuffer = Channel.one2one()
def bufferReceiver = Channel.one2one()
def QNMToNM = Channel.one2one()

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
    def checkerProcess = new Checker(channelInput: chan1[i].in(), channelOutput: chan2[i].out(), nodeID: i)
    checkerProcesses << checkerProcess
    clientsAvailable << checkerProcess.nodeID
}


def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def parser = new Parser(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), toNetworkManager: Parser2Network.out())
def NM = new NetworkManager(fromParser: Parser2Network.in(), toQuarantine: chan2[0].out(), fromQuarantine: QNMToNM.in())
def QNM = new QuarantineNetworkManager(fromNetworkManager: chan2[0].in(), jobRequest: QNMToNM.out(), toBuffer: QNMToBuffer.out(), fromChecker: chan2In, toChecker: chan1Out)
def buffer = new NMBuffer(fromQNM: QNMToBuffer.in(), toReceiver: bufferReceiver.out())
def emailReceiver = new EmailReciever(channelInput: bufferReceiver.in())


def network = [emailClient, parser, NM, QNM, buffer, emailReceiver]


new PAR ( network + checkerProcesses ).run()