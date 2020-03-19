# ShowControl4J
ShowControl4J is an open sourced, mavenized Java library for controlling show elements with a variety of microcontrollers.

Master: [![Build Status](http://showcontrol4j.ddns.net:8080/buildStatus/icon?job=ShowControl4J%2Fmaster)](http://showcontrol4j.ddns.net:8080/job/ShowControl4J/job/master/)

# Author
James Hare

Email: harejamesm@gmail.com

LinkedIn: https://www.linkedin.com/in/jameshareuk

# Technical Design
## Functionality
ShowControl4J provides a mechanism to connect Show Elements (common microcontroller components such as LEDs, Speakers, Servos, Motors, etc.) to Show Triggers (Common microcontroller components such as Push Buttons, Motion Detectors, RFID tags, IO Keyboards, etc.).

## Investigation
This project bridges a gap in connecting microcontrollers together to better synchronize elements of a themed attraction and provide a better overall experience. By using RabbitMQ and common Java libraries for microcontrollers, it will be possible to send GO, IDLE and STOP commands to Show Elements from Show Triggers as Messages.

## Prerequisits

-- [RabbitMQ-Server](https://www.rabbitmq.com/download.html) must be installed and running on a node that is part of the network.

## Implementation
### Class Diagrams
##### ShowControl4J-Core
![http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/JamesHare/ShowControl4J/master/showcontrol4j-core/showcontrol4j-core.plantuml](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/JamesHare/ShowControl4J/master/showcontrol4j-core/showcontrol4j-core.plantuml)

##### ShowControl4J-Element
![http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/JamesHare/ShowControl4J/master/showcontrol4j-element/showcontrol4j-element.plantuml](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/JamesHare/ShowControl4J/master/showcontrol4j-element/showcontrol4j-element.plantuml)

##### ShowControl4J-Trigger
![http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/JamesHare/ShowControl4J/master/showcontrol4j-trigger/showcontrol4j-trigger.plantuml](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/JamesHare/ShowControl4J/master/showcontrol4j-trigger/showcontrol4j-trigger.plantuml)

### Important Classes

##### ShowElementBase

The ShowElementBase class is the parent class for all show elements. Extending this class will give an element the full ability to connect to a RabbitMQ message exchange and listen for show commands from a trigger publishing to the same message exchange. Implementations of the showSequence(), idleLoop() and shutdownProcedure() methods must be provided.

Example Usage
```java
private final String elementName = "show element name";
private final Long elementId = 123456L;
private final MessageExchange messageExchange = new MessageExchange.Builder().withName("example").build();
private final BrokerConnectionFactory brokerConnectionFactory = new BrokerConnectionFactory.Builder().withHostname("127.0.0.1").build();

ShowElement showElement = new ShowElementBase(elementName, elementId, messageExchange, brokerConnectionFactory) {
    @Override
    protected void showSequence() throws InterruptedException {
        // happens when ShowCommand.GO SCFJMessage is received
    }

    @Override
    protected void idleLoop() throws InterruptedException {
        // happens when ShowCommand.IDLE SCFJMessage is received
    }

    @Override
    protected void shutdownProcedure() {
        // happens when ShowCommand.STOP SCFJMessage is received
    }
};
```

##### ShowTriggerBase
The ShowTriggerBase class is the parent class for all show triggers. Extending this class will give a trigger the full ability to connect to a RabbitMQ message exchange and publish show commands to all show elements that are listening on the same message exchange. Implementations of the showSequence(), idleLoop() and shutdownProcedure() methods must be provided.

Example Usage
```java
private final String triggerName = "show trigger name";
private final Long triggerId = 123456L;
private final Long syncTimeout = 10000L; // in milliseconds.
private final MessageExchange messageExchange = new MessageExchange.Builder().withName("example").build();
private final BrokerConnectionFactory brokerConnectionFactory = new BrokerConnectionFactory.Builder().withHostname("127.0.0.1").build();


ShowTrigger showTrigger = new ShowTriggerBase(triggerName, triggerId, syncTimeout, messageExchange, brokerConnectionFactory) {
    @Override
    protected void startListener() {
        // Implemented by child classes. Defines the actions to attach trigger to microcontroller component.
    }
};
```

##### MessageExchange
A Plain Old Java Object class that defines information about a RabbitMQ message exchange. This class makes use of the Builder Pattern.

Example Usage
```java
private final MessageExchange messageExchange = new MessageExchange.Builder().withName("example").build();
```

##### BrokerConnectionFactory
A wrapper class for a RabbitMQ BrokerConnectionFactory. Provides the mechanism to get a new RabbitMQ connection. Must have a hostname pointing to the node where RabbitMQ is installed and running. This class makes use of the Builder Pattern.

Example Usage
```java
private final BrokerConnectionFactory brokerConnectionFactory = new BrokerConnectionFactory.Builder().withHostname("127.0.0.1").build();
```

##### SCFJMessage
A Plain Old Java Object class that is used to build out and set a standard for messages sent in the ShowControl4J library.  At the very least, this object must have a show command (i.e. GO, IDLE or STOP). Optionally can have a Long object representing the system time stamp that a receiving show element must wait for before executing its show sequence. This class makes use of the Builder Pattern.

Example Usage
```java
private final String command = "TEST";
private final Long startTime = 1234567891234L;

SCFJMessage scfjMessage = new SCFJMessage.Builder().withCommand(command).withStartTime(startTime).build();
```

##### ShowCommand
A helper class that provides static methods that return commonly used show commands as SCFJMessage objects as Strings.

Example Usage
```java
String goCommand = ShowCommand.GO();
String goCommandWithTimeout = ShowCommand.GO(10000L); // Tells the receiving Show Element to start its Show Sequence 10 seconds after current time.

String idleCommand = ShowCommand.IDLE();
String idleCommandWithTimeout = ShowCommand.IDLE(10000L); // Tells the receiving Show Element to start its Idle Loop 10 seconds after current time.

String stopCommand = ShowCommand.STOP(); // Receiving Show Elements should always process stop immediately.
```

# Workflow
Coming Soon

# Demo
Coming Soon