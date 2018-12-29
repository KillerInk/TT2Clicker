package clickerbot.com.troop.clickerbot;

/**
 * Created by troop on 20.12.2016.
 */

public class CmdBuilder
{
    final static int EV_ABS = 3;
    final static int EV_SYN = 0;

    final static int SYN_REPORT = 0;

    final static int ABS_MT_SLOT		=    0x2f;	/* MT slot being modified */
    final static int ABS_MT_TOUCH_MAJOR =	0x30;	/* Major axis of touching ellipse */
    //        #define ABS_MT_TOUCH_MINOR	0x31	/* Minor axis (omit if circular) */
    final static int ABS_MT_WIDTH_MAJOR =	0x32;	/* Major axis of approaching ellipse */
    final static int ABS_MT_WIDTH_MINOR	= 0x33;	/* Minor axis (omit if circular) */
    final static int ABS_MT_ORIENTATION	=0x34;	/* Ellipse orientation */
    final static int ABS_MT_POSITION_X	=    0x35;	/* Center X touch position */
    final static int ABS_MT_POSITION_Y	=    0x36;	/* Center Y touch position */
    //        #define ABS_MT_TOOL_TYPE	    0x37	/* Type of touching device */
//        #define ABS_MT_BLOB_ID		0x38	/* Group a set of packets as a blob */
    final static int ABS_MT_TRACKING_ID = 0x39;	/* Unique ID of initiated contact */
    final static int ABS_MT_PRESSURE	=	0x3a;	/* Pressure on contact area */
    //        #define ABS_MT_DISTANCE		0x3b	/* Contact hover distance */
//        #define ABS_MT_TOOL_X		    0x3c	/* Center X tool position */
//        #define ABS_MT_TOOL_Y	        0x3d /* Center Y tool position */
    final static int maxint = 0xffffffff;


    private final static String sendEvent = "sendevent /dev/input/event0 ";

    private final static String cmdUp =
            createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,maxint)+
            createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String getFirstDown(int x,int y,int id) {
        return  createSendEvent(EV_ABS, ABS_MT_TRACKING_ID, id) + /* touch id*/ //0003 0039 00000000
                createSendEvent(EV_ABS, ABS_MT_POSITION_X, x) + /* x*/ //0003 0035 000003ce
                createSendEvent(EV_ABS, ABS_MT_POSITION_Y, y) + /* y*/  //0003 0036 000003c4
                createSendEvent(EV_SYN, SYN_REPORT, 0);
    }

    public final static String getAppendSecondDown(int x,int y,int id, int x0) {
        return  createSendEvent(EV_ABS,ABS_MT_SLOT,1)+
                createSendEvent(EV_ABS, ABS_MT_TRACKING_ID, id) + /* touch id*/ //0003 0039 00000000
                createSendEvent(EV_ABS, ABS_MT_POSITION_X, x) + /* x*/ //0003 0035 000003ce
                createSendEvent(EV_ABS, ABS_MT_POSITION_Y, y) + /* y*/  //0003 0036 000003c4
                createSendEvent(EV_ABS,ABS_MT_SLOT,0)+
                createSendEvent(EV_ABS, ABS_MT_POSITION_X, x0) +
                createSendEvent(EV_SYN, SYN_REPORT, 0);
    }

    public final static String getupdateFirst(int x)
    {
        return createSendEvent(EV_ABS,ABS_MT_SLOT,0)+
                createSendEvent(EV_ABS, ABS_MT_POSITION_X, x) +
                createSendEvent(EV_SYN, SYN_REPORT,0);
    }

    public final static String getCloseFirst =
            createSendEvent(EV_ABS,ABS_MT_SLOT,0)+
            createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,maxint)+
            createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String updateSecond(int x)
    {
        return createSendEvent(EV_ABS,ABS_MT_SLOT,1)+
                createSendEvent(EV_ABS, ABS_MT_POSITION_X, x) +
                createSendEvent(EV_SYN, SYN_REPORT,0);

    }

    public final static String getCloseSecond =
                    createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,maxint)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);


    public static String createSendEvent(int type, int command, int value)
    {
        return sendEvent + type + " " + command + " " + value + "\n";
    }

    public static String getTouchDown(int x, int y, int id)
    {
        return
                createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,id) + /* touch id*/ //0003 0039 00000000
                createSendEvent(EV_ABS, ABS_MT_POSITION_X,x) + /* x*/ //0003 0035 000003ce
                createSendEvent(EV_ABS, ABS_MT_POSITION_Y,y) + /* y*/  //0003 0036 000003c4
                createSendEvent(EV_SYN, SYN_REPORT,0);
    }

    public static String gettap(int x, int y)
    {
        return "input tap " + x + " " +y +"\n";
    }

    public static String getswipe(int x, int y, int x2, int y2, int duration)
    {
        return "input swipe " + x + " " +y + " " + x2 + " " +y2 + " "+duration+"\n";
    }

    public static String getTouchUp()
    {
        return cmdUp;
    }


    public final static String first =
            createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,0)+
                    createSendEvent(EV_ABS, ABS_MT_POSITION_X,0x1e4)+
                    createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4be)+
                    createSendEvent(EV_ABS, ABS_MT_PRESSURE,0x38)+
                    createSendEvent(EV_ABS, ABS_MT_WIDTH_MAJOR,7)+
                    createSendEvent(EV_ABS, ABS_MT_WIDTH_MINOR,7)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String second =
            createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4bd)+
            createSendEvent(EV_ABS, ABS_MT_PRESSURE,55)+
            createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String third =
            createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4bc)+
                    createSendEvent(EV_ABS, ABS_MT_PRESSURE,56)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String fourth =
            createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4bb)+
                    createSendEvent(EV_ABS, ABS_MT_PRESSURE,0x3a)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String fitht =
            createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4ba)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String sixth =
            createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4b9)+
                    createSendEvent(EV_ABS, ABS_MT_PRESSURE,0x3b)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String eight =
            createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4b8)+
                    createSendEvent(EV_ABS, ABS_MT_PRESSURE,0x3c)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);



    public final static String nine =
                    createSendEvent(EV_ABS, ABS_MT_PRESSURE,55)+
                    createSendEvent(EV_ABS, ABS_MT_WIDTH_MAJOR,6)+
                    createSendEvent(EV_ABS, ABS_MT_WIDTH_MINOR,6)+

                    createSendEvent(EV_ABS, ABS_MT_SLOT,1)+
                    createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,1)+
                    createSendEvent(EV_ABS, ABS_MT_POSITION_X,0x35c)+
                    createSendEvent(EV_ABS, ABS_MT_POSITION_Y,0x4a4)+
                    createSendEvent(EV_ABS, ABS_MT_PRESSURE,55)+
                    createSendEvent(EV_ABS, ABS_MT_WIDTH_MAJOR,6)+
                    createSendEvent(EV_ABS, ABS_MT_WIDTH_MINOR,6)+
                    createSendEvent(EV_ABS, ABS_MT_ORIENTATION,1)+
                    createSendEvent(EV_SYN, SYN_REPORT,0);

    public final static String tenth =
            createSendEvent(EV_ABS, ABS_MT_SLOT,0)+
            createSendEvent(EV_ABS, ABS_MT_POSITION_X,0x1e2c)+
            createSendEvent(EV_ABS, ABS_MT_PRESSURE,0x2f)+
            createSendEvent(EV_ABS, ABS_MT_WIDTH_MAJOR,5)+
            createSendEvent(EV_ABS, ABS_MT_WIDTH_MINOR,5)+

            createSendEvent(EV_ABS, ABS_MT_SLOT,1)+
            createSendEvent(EV_ABS, ABS_MT_POSITION_X,0x35b)+
            createSendEvent(EV_ABS, ABS_MT_PRESSURE,0x34)+
            createSendEvent(EV_SYN, SYN_REPORT,0);




}
