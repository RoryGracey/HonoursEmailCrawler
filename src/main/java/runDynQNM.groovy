import groovyJCSP.*
import jcsp.lang.*
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

def checker2qnm = Channel.any2one()

def nodeAddr = new TCPIPNodeAddress(4002)
jcsp.net2.Node.getInstance().init(nodeAddr)
def controllerIP = '146.176.75.72'

def toNM = new TCPIPNodeAddress(controllerIP, 3002)
def toNMChan = NetChannel.any2net(toNM, 50)

def fromNM = NetChannel.net2one()
def fromNMLoc = fromNM.getLocation()


def QNM = new DynQNM(fromNMC: fromNM, fromNMLocation: fromNMLoc, toNMC: toNMChan, fromCheckers: checker2qnm.in(), ChanOutPass: checker2qnm)


def network = [QNM]


new PAR ( network ).run()
