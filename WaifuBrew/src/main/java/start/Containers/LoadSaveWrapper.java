package start.Containers;

public class LoadSaveWrapper {
    private String saveDate;
    private int panelLocation;
    private int advancerDialogue;
    private String routeStory;
    private String thumbnailFile;

    public LoadSaveWrapper(String saveDate, int panelLocation, int advancerDialogue, String routeStory, String thumbnailFile) {
        this.saveDate = saveDate;
        this.panelLocation = panelLocation;
        this.advancerDialogue = advancerDialogue;
        this.routeStory = routeStory;
        this.thumbnailFile = thumbnailFile;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public void setPanelLocation(int panelLocation) {
        this.panelLocation = panelLocation;
    }

    public void setAdvancerDialogue(int advancerDialogue) {
        this.advancerDialogue = advancerDialogue;
    }

    public void setRouteStory(String routeStory) {
        this.routeStory = routeStory;
    }

    public void setThumbnailFile(String thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public int getPanelLocation() {
        return panelLocation;
    }

    public int getAdvancerDialogue() {
        return advancerDialogue;
    }

    public String getRouteStory() {
        return routeStory;
    }

    public String getThumbnailFile() {
        return thumbnailFile;
    }
}

