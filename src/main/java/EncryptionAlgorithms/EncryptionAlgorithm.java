package EncryptionAlgorithms;

import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.Events;

import java.io.IOException;
import java.util.Observable;

public abstract class EncryptionAlgorithm extends Observable implements IEncryptionAlgorithm{
    private String nameOfEncryption;

    public EncryptionAlgorithm(String nameOfEncryption) {
        this.nameOfEncryption = nameOfEncryption;
    }

    public String getNameOfEncryption() {
        return nameOfEncryption;
    }

    @Override
    public int getKeyStrength(int encryptionKey) throws IOException {
        return (int) (Math.log10(encryptionKey)+1);
    }

    public void setEvent(String massage, Events event){
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(massage, event, time, this);
        setChanged();
        notifyObservers(eventsArgs);
    }

}
