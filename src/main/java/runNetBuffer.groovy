import groovyJCSP.PAR
import jcsp.lang.Channel

def buffer2receiver = Channel.one2one()

def buffer = new NMBuffer(toReceiver: buffer2receiver.out())
def emailReceiver = new EmailReciever(channelInput: buffer2receiver.in())


def network = [buffer, emailReceiver]


new PAR ( network ).run()