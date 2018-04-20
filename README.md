# WaifuBrew

Still building...

WaifuBrew is standalone Java application mimicking visual novel. Main point of this project is to play around with Java for various of different aspects (Graphics, File, Audio, and program structure). Not only learning Java is learning objective, but also Github as well.

Logs:

12/02/17 - Initial commit. Basic character object as been made (and other attributes such as mood).

12/04/17 - Initial GUI layout. Waifu class is simplified.

02/21/18 - Fixed image path. (Absolute path to relative path declaration). [Gaia] Mouse events are implemented. Separations of WaifuBrewGUI.java into WaifuBrew.java & GUI.java [BetaStar]

03/07/18 - Start loading images and trial with layout managers. Implemented more robust solution for JFrame & try to load multiples of photos simultaneosly for layered Panels. Name fix for java files. [BetaStar] Menu.java was created with calculated positions and mouse events. [Gaia]

04/02/18 - Finished basic GUI (Layered Panel fully works & location for each picture is now fully calculated) [BetaStar] and basic JSON retrieval. [Gaia]

04/04/18 - GUI update. Introduced start screen. Implementation of buttons and stages. Resolution bugs (placement & scaling) were now properly calculated. The window (JFrame) is now locked since it adds no benefit and causes elements to break. [BetaStar]

04/05/18 - Placeholder buttons are working as they would. The start of Config page (only implemented BG and back button) [BetaStar]

04/06/18 - Animation now in test phase. Config stage is being used as animation testing. License changed to MIT. [BetaStar]

04/09/18 - Used simpler implementation for rotation for now since more advanced implementation doesn't seem to contribute solving the problem. [BetaStar]

04/20/18 - Changed vanilla Java application to Maven to have JavaXT dependency for image manipulation. Added test stage [BetaStar]
