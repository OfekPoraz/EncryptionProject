package exceptions;

import eventslogger.EncryptionLogger;

public class InvalidKeyException extends Exception{

    public InvalidKeyException(String message) {
        super();
        EncryptionLogger logger = new EncryptionLogger();
        logger.writeErrorLog(message);
    }
}

