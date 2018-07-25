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

04/24/18 - Fixed movement issue with JavaXT, added text line for dialog, continued Gaia's legacy - JSON implementation. [BetaStar]

04/25/18 - Finished implementing basic form of JSON parser. Now retrieves Characters, Moods, and Dialogues. Auto fixes Moods when implementation of JSON only specifies one Mood for all characters. 3 characters can have all HAPPY Mood with single Mood in JSON [BetaStar]

04/26/18 - Fixed JSON bugs where it caused duplicates to show up when it reached last line of JSON. Completely reworked Waifu.java (deleted entire thing and started over) and made it into wrapper class of JSON parser to AnimationPane conversion. JSON -> Seperated into list of three seperate objects (Character, Mood, Dialogue) -> compiled and packaged with list of Waifu objects, sent off. Added elements for test purposes. [BetaStar]

04/27/18 - Restarted progress of Settings panel (ConfigPane). Fixed and enhanced dialoguebar. The placement is now relative. Lastly, start page buttons now react to mouse for better GUI experience. [BetaStar]

04/28/18 - Added "load" button to main screen. Added more elements in resource pool for future use (like "Save" image). [BetaStar]

05/02/18 - Properly set up Singleton to reduce the amount of dependency between classes. (No more passing around class objects of main!) Able to retrive variables from classes. Implemented back button of ConfigPane and responds to mouse clicks, however GUI get stuck. I can move to Stage 2 -> Stage 0 -> Stage 1 (I can go from Setting page to Main to Play field). Cleared useless codes / redundant lines [BetaStar]

07/02/18 - Implemented dialogue transparency in settings. Have tried LAF and Nimbus, and both of them are not aesthetically pleasing and not very customizable (like location). Messy implemention of slider in ConfigPane which has few layers. Most rear one shows how much slider can go, second will fill up to set value, and knob which reacts to mouse actions. Mouse does not need to stay on knob to change value. The ConfigPane's BG will be temporary disabled and dialogueBox will show up to demonstrate the opacity. AnimationPane will reflect opacity set value from ConfigPane. Added text text speed control as well in the ConfigPane. Deleted entire test graphics portion in AnimationPane (backed up in bak). Ready for LoadPane implementation. [BetaStar]

07/04/18 - Completely redesigned the custom sliders. Created new class called "CustomSlider" which clears up huge portion of mess in ConfigPane. Also bug fix on dialogueBar preview as it was retrieving the image file from disk everytime when slider was dragged thus creating lag and performance degradation. Now dialogueBar transparency / speed uses CustomSlider. Fixed dialogbar preview timer. Still needs numerical value beside the slider. [BetaStar]

07/05/18 - Implemented CustomButton to lessen the trashing of the Pane classes. Bug fixes with performance with CustomSlider. It was loading three seperate images of samething and IO access was dragging the program down. Also fixed the code where it changes each of its pixels to specific colour to mass change the bg colour with JavaXT. AnimationPane now uses CustomButton class to implement its buttons. Needs work with Change Listener. [BetaStar]

07/06/18 - The StartScreen now relies on CustomButton and removed whole lot of garbage codes. CustomButton now takes extra parameter for centered image for x, y. GUI.java has also been cleared out along with StartScreen. Now GUI.java only manages stage changes between panes. (Updates are in 07/07/18 since I uploaded too late) Also fixed one line in ConfigPane that set local stage instead of global stage. [BetaStar]

07/07/18 - AnimationPane got its update on character placement - especially multi character scenarios. Will now calculate where character will go through window calculation. Huge performance saver with implementation of framerate control. Currently running 2FPS as a debug. Will revert back to 30FPS when needed. [BetaStar]

07/08/18 - Implementation of ImageSlicer. Loading times of ConfigPane was annoying so I am implemented ImageSlider to grab needed image once (with all the buttons on it) and slice them up so that I can access on any Pane. [BetaStar]

07/13/18 - Cleaned out testers for ImageSlicer. Added FindFile.java to retrieve files from local directory instead of listing out and hard code file names for read out. CharMove has been added but still in progress. Performance fixes and flexibility for rest of the program. GUI Scaling has been added and fixed. No more double declaration with WaifuBrew.java (The bug fix). [BetaStar]

07/14/18 - Applying image loader to other classes to rely on. Fixed dialog bars not applying the settings and removed hard coded buttons in setting class (replaced with CustomButton). Release of v0.05 [BetaStar]

07/16/18 - Most classes including AnimationPane (character images) are using auto-loaded (sliced) images. Cleared out all unnecessary images in the resource folder. Added unhandled exception class to deal with wrong image retrieval. Numbers has been added to enums to simplify and add more functionality. [BetaStar]

07/19/18 - Started implementing Custom on/off switch for ConfigPane. A sample button now rests in ConfigPane to be implemented as intended. Added toggle knob for both CustomSlider and CustomOnOffSwitch [BetaStar]
