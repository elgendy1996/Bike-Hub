package nl.fontys.dbmethods;

public class Bike {
    private int id;
    private int barCode;
    private int lastLockLat;
    private int lastLockLng;
    private int currentLockLat;
    private int currentLockLng;
    private int totalDistanceTraveled;
    private boolean lockStatus;
    private int hub;

    public Bike(int id, int barCode, int lastLockLat, int lastLockLng, int currentLockLat,
                int currentLockLng, boolean lockStatus, int hub) {
        this.id = id;
        this.barCode = barCode;
        this.lastLockLat = lastLockLat;
        this.lastLockLng = lastLockLng;
        this.currentLockLat = currentLockLat;
        this.currentLockLng = currentLockLng;
        this.totalDistanceTraveled = 0;
        this.lockStatus = lockStatus;
        this.hub = hub;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public int getLastLockLat() {
        return lastLockLat;
    }

    public void setLastLockLat(int lastLockLat) {
        this.lastLockLat = lastLockLat;
    }

    public int getLastLockLng() {
        return lastLockLng;
    }

    public void setLastLockLng(int lastLockLng) {
        this.lastLockLng = lastLockLng;
    }

    public int getCurrentLockLat() {
        return currentLockLat;
    }

    public void setCurrentLockLat(int currentLockLat) {
        this.currentLockLat = currentLockLat;
    }

    public int getCurrentLockLng() {
        return currentLockLng;
    }

    public void setCurrentLockLng(int currentLockLng) {
        this.currentLockLng = currentLockLng;
    }

    public int getTotalDistanceTraveled() {
        return totalDistanceTraveled;
    }

    public void setTotalDistanceTraveled(int totalDistanceTraveled) {
        this.totalDistanceTraveled = totalDistanceTraveled;
    }

    public boolean getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    public int getHub() {
        return hub;
    }

    public void setHub(int hub) {
        this.hub = hub;
    }
}
