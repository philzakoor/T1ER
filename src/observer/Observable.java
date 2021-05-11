package observer;

import java.util.ArrayList;

import beans.Notification;

public interface Observable {

	public void registerObservers(ArrayList<Observer> o);
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	void notifyObservers(Notification notification);
	
}
