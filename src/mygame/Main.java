package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetKey;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.CollisionResult;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import com.jme3.util.SkyFactory;
import com.jme3.light.DirectionalLight;
import com.jme3.system.AppSettings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    static String user;
    Boolean isRunning = true;
    Node shootables;
    Node keep;
    Geometry mark;
    //Vector3f[] position;
    float startz = -15f;
    float startx = 0f;
    float starty = -2f;
    float boxSize = 0.5f;
    Vector3f box1 = new Vector3f(0, 0, 0);
    //float moved[];
    private int word = 0;
    BitmapText nodeWord;
    BitmapText nodeScore;
    BitmapText nodeHealth;
    BitmapText wrongHit;
    BitmapText finalScore;
    private int curScore = 0;
    private int curHealth = 100;
    static String curWord = "Totallyasrs";
    GhostControl ghostScoreBox;
    BitmapText pauseText;
    ArrayList<Boxer> boxList = new ArrayList<Boxer>();
    int howManySent = 0;
    Connection conn = null;
    ResultSet rs = null;
    Statement stat = null;
    String alphabet = "abcdefghijklmopqrstuvwxyz";
    //String seen = "";
    boolean ifSeen[];
    Main currentApp;
    public static int score;
    public static int wordCount = 0;
    public static int timePlayed = 0;

    public Main() {
        super();

    }

    private class Boxer {

        char character;
        String id;
        // boolean scored = false;
        Geometry body;
        float moved = 0;
        Vector3f position;
    }
    static List<String> Words;

    void setUPdb() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:wordGame.db");
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void startMe(List<String> words, String name) {
        curWord = words.get(wordCount);
        Main app = new Main();
        user = name;
        AppSettings cfg = new AppSettings(false);

        cfg.setFrameRate(60); // set to less than or equal screen refresh rate
        cfg.setVSync(true);   // prevents page tearing
//cfg.setFrequency(60); // set to screen refresh rate
        cfg.setResolution(1366, 768);
        cfg.setFullscreen(true);
        cfg.setSamples(2);    // anti-aliasing
        cfg.setTitle("skyWord - Pre-Alpha Release v.0.0012"); // branding: window name
        try {
            // Branding: window icon
            cfg.setIcons(new BufferedImage[]{ImageIO.read(new File("assets/Interface/icon.gif"))});
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Icon missing.", ex);
        }
// branding: load splashscreen from assets
        cfg.setSettingsDialogImage("Interface/splash.png");
//app.setShowSettings(false); // or don't display splashscreen
        app.setSettings(cfg);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.start();
        //Main app = new Main();


        // app.setShowSettings(false);
        //  app.start();



    }
    int last = 0;

    boolean checkMatch(char check) {
        for (int i = 0; i < curWord.length(); i++) {
            if (curWord.charAt(i) == check) {
                // ifSeen[i] = true;
                last = i;
                return true;
            }
        }

        return false;
    }
//    void setPositions(int _count) {
//
//        position = new Vector3f[_count];
//        int k = 0;
//        position[0] = new Vector3f(0, starty, startz);
//        for (int i = 1; i < _count; i++) {
//            position[i] = new Vector3f();
//            moved[i] = 0.0f;
//            position[i].z = startz;
//            if (i % 2 == 0) {
//                //k = i;
//                position[i].x = (-k);
//            } else {
//                k = i + 1;
//                position[i].x = (k);
//            }
//            position[i].y = starty;
//
//        }
//
//    }
    /**
     * A cube object for target practice
     */
    int k = 0;

    protected Geometry makeCube(int i, char type, boolean real) {
        int k = 0;
        Boxer temp = new Boxer();
        temp.character = type;
        temp.moved = 0f;
        // temp.scored = real;
        temp.position = new Vector3f();
        if (i % 2 == 0) {
            temp.position.x = -k;
        } else {
            k = i + 1;
            temp.position.x = k;
        }
        temp.position.y = starty;
        temp.position.z = startz;
        Box box = new Box(temp.position, boxSize, boxSize, boxSize);

        temp.body = new Geometry(i + "", box);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex_ml;
        tex_ml = assetManager.loadTexture("Textures/" + type + ".png");
        mat1.setTexture("ColorMap", tex_ml);
        temp.body.setMaterial(mat1);
        temp.id = temp.body.getName();
        System.out.println("this box is intially created with name: " + temp.id);
        boxList.add(temp);
        //Box box = new Box(position[i], boxSize, boxSize, boxSize);
        // Geometry cube = new Geometry(i + "", box);


        //mat1.setColor("Color", ColorRGBA.randomColor());
        //cube.setMaterial(mat1);
        //  shootables.attachChild(cube);
        return temp.body;
        // System.out.println("Number of cube added: " + shootables.getQuantity());
    }

    public void updateScore() {

        System.out.println("UpdateScore");
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery("INSERT INTO ScoreBank ( Score, PlayerID, NumWordsCorrect, Time ) VALUES ( " + curScore
                    + ",( SELECT PlayerID FROM PlayerBank WHERE PlayerName = '" + user + "' ),"
                    + wordCount + "," + timer.getTimeInSeconds() + ");");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UpdateScore Error line 757");
        }
    }

    @Override
    public void simpleInitApp() {
        setUPdb();
        word = 5;
        initCrossHairs(); // a "+" in the middle of the screen to help aiming
        initKeys();       // load custom key mappings
        initMark();       // a red sphere to mark the hit
        initWrong();
        //moved = new float[curWord.length()];
        //setPositions(curWord.length());

        /**
         * create four colored boxes and a floor to shoot at:
         */
        shootables = new Node("Shootables");
        keep = new Node("object");
        rootNode.attachChild(SkyFactory.createSky(
                assetManager, "Textures/Sky/SkyBox/SkyBox.dds", false));
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);


        rootNode.attachChild(shootables);
        rootNode.attachChild(keep);
        /*
         * Initalized our output buffer
         */
        char temp;
        Geometry tempGeo;
        ifSeen = new boolean[curWord.length()];
        for (int i = 0; i < word; i++) {
            Random r = new Random();
            if (r.nextBoolean()) {
                int tempInt = r.nextInt(curWord.length());
                temp = curWord.toLowerCase().charAt(tempInt);
                while (ifSeen[tempInt]) {
                    tempInt = r.nextInt(curWord.length());
                    temp = curWord.toLowerCase().charAt(tempInt);
                }
                //ifSeen[tempInt] = true;
                tempGeo = makeCube(i, temp, true);
                shootables.attachChild(tempGeo);
                System.out.println("Real box " + temp + " was made. name: " + tempGeo.getName());

                howManySent++;
            } else {
                temp = alphabet.charAt(r.nextInt(alphabet.length()));
                while (curWord.contains(temp + "")) {
                    temp = alphabet.charAt(r.nextInt(alphabet.length()));
                }
                tempGeo = makeCube(i, temp, false);
                shootables.attachChild(tempGeo);
                System.out.println("Fake box " + temp + " was made. hash: " + tempGeo.getName());

            }
        }

        keep.attachChild(makeFloor());

        /*
         * Setting the GUI
         */

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        nodeHealth = new BitmapText(guiFont, true);
        nodeScore = new BitmapText(guiFont, true);
        nodeWord = new BitmapText(guiFont, true);
        nodeHealth.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        nodeScore.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        nodeWord.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        nodeHealth.setText("Health: " + Integer.toString(curHealth));
        nodeScore.setText("Score: " + Integer.toString(curScore));
        nodeWord.setText(curWord);
        nodeHealth.setLocalTranslation(
                0,
                nodeHealth.getHeight(),
                0);
        nodeScore.setLocalTranslation(
                settings.getWidth() - nodeScore.getLineWidth(),
                nodeScore.getHeight(),
                0);
        nodeWord.setLocalTranslation(
                settings.getWidth() / 2 - nodeWord.getLineWidth() / 2,
                settings.getHeight(),
                0);
        nodeScore.setColor(ColorRGBA.Pink);
        nodeHealth.setColor(ColorRGBA.Red);
        nodeWord.setColor(ColorRGBA.Orange);
        guiNode.attachChild(nodeScore);
        guiNode.attachChild(nodeHealth);
        guiNode.attachChild(nodeWord);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");

        pauseText = new BitmapText(guiFont, false);
        pauseText.setSize(guiFont.getCharSet().getRenderedSize() * 4);
        pauseText.setText("---YOU LOSE---");
        pauseText.setLocalTranslation(
                settings.getWidth() / 2 - pauseText.getLineWidth() / 2, settings.getHeight() - pauseText.getLineHeight(), 0);
        // Box b = new Box(5, 5, 5);
        //  Geometry geom = new Geometry("Box", b);
        //  Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");

    }

    public boolean checkWin() {
        int count = 0;
        for (int i = 0; i < curWord.length(); i++) {
            if (ifSeen[i]) {
                count++;
            }
        }
        if (count == curWord.length() - 1) {
            wordCount++;
            //TODO - increment word
            updateWord();

            return true;
        }
        return false;
    }

    void updateWord() {
        curWord = UI.Words.get(wordCount);
        ifSeen = new boolean[curWord.length()];
        for (int i = 0; i < curWord.length(); i++) {
            ifSeen[i] = false;
        }
    }

    
       float moveSpeed = 0.05f;
    boolean increase = false;
     boolean increase2 = false;
     
    @Override
    public void simpleUpdate(float tpf) {
        if (isRunning) {
            System.out.println("Current time: "+ timer.getTimeInSeconds());
            if(timer.getTimeInSeconds()>10&&!increase){
                moveSpeed+=0.05;
                increase = true;
            }
            if(timer.getTimeInSeconds()>20&&!increase2){
                moveSpeed+=0.05;
                increase2 = true;
            }
            if (time != 0 && (time + 20) > timer.getTimeInSeconds()) {
                wrongHit.removeFromParent();
                time = 0;
            }
            pauseText.removeFromParent();
            for (int i = 0; i < shootables.getQuantity(); i++) {
                shootables.getChild(i).move(0f, 0f, moveSpeed);
                boxList.get(i).moved += 0.05f;
                if (boxList.get(i).moved >= 18.0f) {


                    // boxList.get(i).moved = 0;
                    //shootables.getChild(i).move(0f, 0f, -18f);
                    if (checkMatch(boxList.get(i).character)) {


                        ifSeen[last] = true;
                        curScore += 100;
                        curHealth += 1;
                        if (curHealth >= 100) {
                            curHealth = 100;
                        }
                        if (checkWin()) {
                            System.out.println("NEXT WORD!");
                        }
                    } else {
                        curScore -= 10;
                        curHealth -= 20;
                        if (curHealth <= 0) {
                            curHealth = 0;
                            isRunning = false;
                            score = curScore;
                            //  UI.numCorrect = wordCount;
                            // UI.scoreRecord = score;
                            //UI.timeRecord = timer.getTimeInSeconds();
                            /**
                             * Uses Texture from jme3-test-data library!
                             */
                            updateScore();
                            try {
                                conn.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
                            Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
                            //mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
                            fire.setMaterial(mat_red);
                            fire.setImagesX(1);
                            fire.setImagesY(1); // 2x2 texture animation
                            fire.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
                            fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
                            fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
                            fire.setStartSize(0.6f);
                            fire.setEndSize(0.1f);
                            fire.setGravity(0f, 0f, 0f);
                            fire.setLowLife(0.5f);
                            fire.setHighLife(3f);
                            fire.getParticleInfluencer().setVelocityVariation(0.3f);
                            fire.setLocalTranslation(0f, -2f, 0f);
                            rootNode.attachChild(fire);

                        }
                    }
                    shootables.detachChild(shootables.getChild(boxList.get(i).id));
                    shootables.attachChild(setWord(boxList.get(i).id));
                }

            }
            nodeScore.setLocalTranslation(
                    settings.getWidth() - nodeScore.getLineWidth(),
                    nodeScore.getHeight(),
                    0);
            nodeScore.setText("Score: " + curScore);
            nodeHealth.setText("Health: " + curHealth);
            nodeWord.setText(curWord);
        } else {
            /**
             * Write text on the screen (HUD)
             */
            // guiNode.detachAllChildren();
            guiNode.attachChild(pauseText);

        }
    }

    private char getLetter() {
        char nextLetter;
        Random r = new Random();
        if (r.nextBoolean()) {
            int tempInt = r.nextInt(curWord.length());
            nextLetter = curWord.toLowerCase().charAt(tempInt);
            while (ifSeen[tempInt]) {
                tempInt = r.nextInt(curWord.length());
                nextLetter = curWord.toLowerCase().charAt(tempInt);
            }

            System.out.println("Real box " + nextLetter + " was made");
            howManySent++;
            return nextLetter;
        } else {
            nextLetter = alphabet.charAt(r.nextInt(alphabet.length()));
            while (curWord.contains(nextLetter + "")) {
                nextLetter = alphabet.charAt(r.nextInt(alphabet.length()));
            }
            System.out.println("Fake box " + nextLetter + " was made.");
        }
        return nextLetter;
    }

    private Geometry setWord(String input) {
        Iterator it = boxList.iterator();
        k = 0;

        while (it.hasNext()) {
            Boxer check = (Boxer) it.next();
            if (check.id.equals(input)) {
                Boxer temp = new Boxer();

                temp = check;
                boxList.remove(check);
                int i = Integer.parseInt(temp.id);
                temp.moved = 0f;
                // temp.scored = getLetter();
                temp.character = getLetter();
                temp.position = new Vector3f();
                if (i % 2 == 0) {
                    if (i == 0) {
                        temp.position.x = 0;
                    } else {
                        temp.position.x = -(k + i);
                    }
                } else {
                    k = i + 1;
                    temp.position.x = k;
                }
                temp.position.y = starty;
                temp.position.z = startz;
                Box box = new Box(temp.position, boxSize, boxSize, boxSize);
                System.out.println("New position: " + temp.position);
                temp.body = new Geometry(i + "", box);
                Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                Texture tex_ml;
                tex_ml = assetManager.loadTexture("Textures/" + temp.character + ".png");
                mat1.setTexture("ColorMap", tex_ml);
                temp.body.setMaterial(mat1);
                temp.id = temp.body.getName();
                System.out.println("this box is intially created with name: " + temp.id);
                boxList.add(temp);
                return temp.body;
            }
        }

        return null;
    }

    private void initKeys() {
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Shoot",
                new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
        inputManager.addMapping("quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addListener(actionListener, "Shoot");
        inputManager.addListener(actionListener, "quit");
        inputManager.addListener(actionListener, new String[]{"Pause"});
    }
    /**
     * Defining the "Shoot" action: Determine what was hit and how to respond.
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("quit") && !keyPressed) {
                stop();
            }
            if (name.equals("Pause") && !keyPressed) {
                isRunning = !isRunning;
                score = curScore;
            }
            if (isRunning) {
                if (name.equals("Shoot") && !keyPressed) {
                    String hit = "";
                    // 1. Reset results list.
                    CollisionResults results = new CollisionResults();
                    // 2. Aim the ray from cam loc to cam direction.
                    Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                    // 3. Collect intersections between Ray and Shootables in results list.
                    shootables.collideWith(ray, results);
                    // 4. Print the results
                    System.out.println("----- Collisions? " + results.size() + "-----");
                    for (int i = 0; i < results.size(); i++) {
                        // For each hit, we know distance, impact point, name of geometry.
                        float dist = results.getCollision(i).getDistance();
                        Vector3f pt = results.getCollision(i).getContactPoint();
                        hit = results.getCollision(i).getGeometry().getName();
                        System.out.println("* Collision #" + i);
                        System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
                    }
                    // 5. Use the results (we mark the hit object)
                    if (results.size() > 0) {
                        Iterator it = boxList.iterator();
                        while (it.hasNext()) {
                            Boxer temp = (Boxer) it.next();
                            if (temp.id.equals(hit)) {
                                CollisionResult closest = results.getClosestCollision();
                                Material material = closest.getGeometry().getMaterial();
                                if (checkMatch(temp.character)) {
                                    guiNode.attachChild(wrongHit);
                                    time = timer.getTimeInSeconds();
                                    curHealth -= 5;
                                    if (curHealth < 0) {
                                        isRunning = false;
                                        score = curScore;
                                    }


                                } else {
                                    curHealth += 1;
                                    if (curHealth > 100) {
                                        curHealth = 100;
                                    }
                                    curScore += 20;
                                }
                                //shootables.detachChild(closest.getGeometry());
                                System.out.println("A HIT IS A MATCH");
                                shootables.detachChild(shootables.getChild(hit));
                                shootables.attachChild(setWord(hit));
                                // setWord(hit);
                                break;
                            }
                        }
                        // The closest collision point is what was truly hit:

                        //  material.setColor("Color", ColorRGBA.randomColor());

                        // shootables.getChild(hit).move(0, 0, -5f);
                        curScore++;

                    }
                }
            }
        }
    };

    /**
     * A floor to show that the "shot" can go through several objects.
     */
    protected Geometry makeFloor() {

        Box box = new Box(new Vector3f(0, -4, -5), 15, .2f, 15);
        Geometry floor = new Geometry("the Floor", box);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex_ml;
        tex_ml = assetManager.loadTexture("Textures/floor.jpg");
        mat1.setTexture("ColorMap", tex_ml);
        floor.setMaterial(mat1);
        //mat1.setColor("Color", ColorRGBA.Gray);
        floor.setMaterial(mat1);
        return floor;
    }

    /**
     * A red ball that marks the last spot that was "hit" by the "shot".
     */
    protected void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }

    /**
     * A centred plus sign to help the player aim.
     */
    protected void initCrossHairs() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }
    float time = 0;

    protected void initWrong() {

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        wrongHit = new BitmapText(guiFont, false);
        wrongHit.setSize(guiFont.getCharSet().getRenderedSize() * 8);
        wrongHit.setText("x"); // crosshairs
        wrongHit.setColor(ColorRGBA.Red);
        wrongHit.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + wrongHit.getLineHeight() / 2, 0);

    }
}
