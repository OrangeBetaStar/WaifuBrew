package start.Containers;

import java.util.Date;

public class LoadSaveWrapper {
    private Date saveDate;
    private int panelLocation;
    private int advancerDialogue;
    private String routeStory;
    private String thumbnailFile;

    public LoadSaveWrapper(Date saveDate, int panelLocation, int advancerDialogue, String routeStory, String thumbnailFile) {
        this.saveDate = saveDate;
        this.panelLocation = panelLocation;
        this.advancerDialogue = advancerDialogue;
        this.routeStory = routeStory;
        this.thumbnailFile = thumbnailFile;
    }

    public void setSaveDate(Date saveDate) {
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

    public Date getSaveDate() {
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

