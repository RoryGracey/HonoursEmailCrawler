import groovyJCSP.*
import jcsp.lang.*


def outputSubject = Channel.one2one()
def outputBody = Channel.one2one()
def inputChannel = Channel.one2one()

def server = new Server(outputSubject: outputSubject.out(), outputBody: outputBody.out(), inputChannel: inputChannel.in())
def parser = new Parser(subjectIn: outputSubject.in(), bodyIn: outputBody.in(), channelOutput: inputChannel.out())

def processList = [server, parser]

new PAR (processList).run()
