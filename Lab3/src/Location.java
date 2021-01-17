public enum Location {
    HOME ("у себя дома"),
    FOREST("над лесом"),
    GLADE("на лесную полянку"),
    ROOF("к крыше");

    private String location;

    Location (String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
}
