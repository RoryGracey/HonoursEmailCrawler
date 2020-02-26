import groovyJCSP.*
import jcsp.lang.*
import jcsp.net.Node
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.nio.channels.NetworkChannel

def checkerBuffer = Channel.any2one()
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
    def portnum = 4000 + i
    def vcn = 51 + i
    def checkerProcess = new Checker(channelInput: chan1[i].in(), channelOutput: chan2[i].out(), toNetworkBuffer: checkerBuffer.out(), nodeID: i, portNumber: portnum, vcnNum: vcn)
    checkerProcesses << checkerProcess
    clientsAvailable << checkerProcess.nodeID
}


def QNM = new QuarantineNetworkManager(fromNetworkManager: chan2[0].in(), fromChecker: chan2In, toChecker: chan1Out)


def network = [QNM]


new PAR ( network + checkerProcesses ).run()