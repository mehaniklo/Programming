public enum Location {
    /**
     Enum for list of possible locations
     */
    HOME ("у себя дома"),
    FOREST("над лесом"),
    GLADE("на лесную полянку"),
    ROOF("на крышу"),
    PIPE("в трубу"),
    LAND("на землю");

    private String location;

    Location (String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
}
