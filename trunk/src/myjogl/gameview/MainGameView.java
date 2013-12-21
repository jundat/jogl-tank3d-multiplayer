/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import Testtool.CameraFo;
import myjogl.particles.ParticalManager;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import myjogl.*;
import myjogl.utils.*;
import myjogl.gameobjects.*;
import myjogl.particles.Explo;

/**
 *
 * @author Jundat
 */
public class MainGameView implements GameView {

    //
    public final static long TIME_CREATE_AI = 1234; //millisecond
    public final static int SCORE_DELTA = 10;
    public final static int NUMBER_OF_LIEF = 5;
    public final static int MAX_CURRENT_AI = 2; //4; //maximum current TankAI in 1 screen, at a moment
    public final static int DELAY_TIME = 0;
    public final static float DELTA_BETA = 0.0513f;
    public final static float DELTA_R = 0.25f;
    //
    public boolean isPause;
    //tankAI
    private int DEFAUL_LAST_TANK = 10;
    private int lastTanks; //so tang con lai, chwa dwa ra
    private int currentTank; //number of tank in screen at a moment
    //
    
    private int playerLife = NUMBER_OF_LIEF;
    private int opponentLife = NUMBER_OF_LIEF;
    
    private Tank tank1;
    private Tank tank2;
    private Tank playerTank;
    private Tank opponentTank;
    
    private Boss playerBoss;
    private Boss opponentBoss;
    private Vector3 playerBossPosition;
    private Vector3 opponentBossPosition;
    
    //
    private TankAI tankAis[];//-------------------------------------------------
    private SkyBox m_skybox;
    private Camera camera;
    private CameraFo cameraFo;
    boolean bTest = false;
    boolean bSliding = true;
    float deltaBeta = DELTA_BETA;
    float deltaR = DELTA_R;
    int delayTime = 0;
    private Writer writer;
    
    Point pLevel = new Point(5, 690);
    Point pAI = new Point(5, 650);
    Point pLife = new Point(5, 610);
    Point pScore = new Point(820 + 256, 690);
    Point pScoreValue = new Point(838 + 256, 650);
    
    //sound
    public Sound sBackground;

    public MainGameView() {
        super();
        //System.out.println("Go to main game!------------------------------------");
    }

    //
    //opponent
    //
    public void opponentTankFire() {
        if (opponentTank.isAlive()) {
            if (opponentTank.fire()) {
                GameEngine.sFire.clone().setVolume(6.0f);
                GameEngine.sFire.clone().play();
            }
        }
    }
    
    public void opponentHandleInput(long dt) {
        
        KeyboardState state = KeyboardState.getState();
        
        //up
        if (state.isDown(KeyEvent.VK_W)) {
            if (opponentTank.isAlive()) {
                opponentTank.move(CDirections.UP, dt);
                if (this.checkTankCollision(opponentTank)) {
                    this.opponentTank.rollBack();
                }
            }
        } //down
        if (state.isDown(KeyEvent.VK_S)) {
            if (opponentTank.isAlive()) {
                opponentTank.move(CDirections.DOWN, dt);
                if (this.checkTankCollision(opponentTank)) {
                    this.opponentTank.rollBack();
                }
            }
        }  //left
        if (state.isDown(KeyEvent.VK_A)) {
            if (opponentTank.isAlive()) {
                opponentTank.move(CDirections.LEFT, dt);
                if (this.checkTankCollision(opponentTank)) {
                    this.opponentTank.rollBack();
                }
            }
        }  //right
        if (state.isDown(KeyEvent.VK_D)) {
            if (opponentTank.isAlive()) {
                opponentTank.move(CDirections.RIGHT, dt);
                if (this.checkTankCollision(opponentTank)) {
                    this.opponentTank.rollBack();
                }
            }
        }
    }
    
    
    //
    // handle input
    //
    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (isPause) {
            return;
        }

        //common
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.isPause = true;
            GameEngine.getInst().attach(new PauseView(this));
        }

        //playerTank
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (playerTank.isAlive()) {
                if (playerTank.fire()) {
                    GameEngine.sFire.clone().setVolume(6.0f);
                    GameEngine.sFire.clone().play();
                }
            }
        }
        
        //opponentTank
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            opponentTankFire();
        }
        

//        else if (e.getKeyCode() == KeyEvent.VK_T) {
//            bTest = !bTest;
//        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
//            cameraFo.r += 2;
//        } else if (e.getKeyCode() == KeyEvent.VK_X) {
//            cameraFo.r -= 2;
//        }
//
//        //cheat
//        if (e.getKeyCode() == KeyEvent.VK_N) {
//            this.loadLevel(Global.level + 1);
//        } else if (e.getKeyCode() == KeyEvent.VK_P) {
//            this.loadLevel(Global.level - 1);
//        }
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
//        if (!bTest) {
//            return;
//        }
//
//        int x = e.getXOnScreen();
//        int y = e.getYOnScreen();
//        //System.err.print("\n" + x + " " + y);
//        int mid_x = Global.wndWidth / 2;
//        int mid_y = Global.wndHeight / 2;
//        if ((x == mid_x) && (y == mid_y)) {
//            return;
//        }
//
//        Robot r;
//        try {
//            r = new Robot();
//            r.mouseMove(mid_x, mid_y);
//        } catch (AWTException ex) {
//            Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        // Get the direction from the mouse cursor, set a resonable maneuvering speed
//        float angle_x = (float) ((mid_x - x)) / 1000;
//        float angle_y = (float) ((mid_y - y)) / 1000;
//        cameraFo.alpha += angle_y * 0.5f;
//        cameraFo.beta -= angle_x * 0.5f;
    }

    public void pointerReleased(MouseEvent e) {
    }

    private void handleInput(long dt) {
        if (isPause) {
            return;
        }

        KeyboardState state = KeyboardState.getState();

        //playerTank
        //up
        if (state.isDown(KeyEvent.VK_UP)) {
            if (playerTank.isAlive()) {
                playerTank.move(CDirections.UP, dt);
                if (this.checkTankCollision(playerTank)) {
                    this.playerTank.rollBack();
                }
            }
        } //down
        if (state.isDown(KeyEvent.VK_DOWN)) {
            if (playerTank.isAlive()) {
                playerTank.move(CDirections.DOWN, dt);
                if (this.checkTankCollision(playerTank)) {
                    this.playerTank.rollBack();
                }
            }
        }  //left
        if (state.isDown(KeyEvent.VK_LEFT)) {
            if (playerTank.isAlive()) {
                playerTank.move(CDirections.LEFT, dt);
                if (this.checkTankCollision(playerTank)) {
                    this.playerTank.rollBack();
                }
            }
        }  //right
        if (state.isDown(KeyEvent.VK_RIGHT)) {
            if (playerTank.isAlive()) {
                playerTank.move(CDirections.RIGHT, dt);
                if (this.checkTankCollision(playerTank)) {
                    this.playerTank.rollBack();
                }
            }
        }
        
        //this.camera.SetViewPoint(playerTank.getCenter().x, playerTank.getCenter().y + 3, playerTank.getCenter().z);
        //this.camera.SetEyePoint(playerTank.getCenter().x, playerTank.getCenter().y + 5, playerTank.getCenter().z + 8);

    }

    //
    // end - handle input
    //
    //
    // initialize
    //
    public void loadLevel(int level) {
        Global.level = level;
        bTest = false;
        bSliding = true;
        deltaBeta = DELTA_BETA;
        deltaR = DELTA_R;
        delayTime = 0;

        try {
            //init map
            TankMap.getInst().LoadMap(level);

            //playerBoss
            this.playerBossPosition = TankMap.getInst().bossPosition.Clone();
            this.playerBoss.reset(playerBossPosition, CDirections.UP);

            //opponentBoss
            this.opponentBossPosition = TankMap.getInst().bossAiPosition.Clone();
            this.opponentBoss.reset(opponentBossPosition, CDirections.UP);
            
            //playerTank
            Vector3 v = ((Vector3) TankMap.getInst().listTankPosition.get(0)).Clone();
            playerTank.reset(v.Clone(), CDirections.RIGHT);
            
            //opponentTank
            v = ((Vector3) TankMap.getInst().listTankAiPosition.get(0)).Clone();
            opponentTank.reset(v.Clone(), CDirections.LEFT);
            
            

            playerLife = NUMBER_OF_LIEF;
            opponentLife = NUMBER_OF_LIEF;

//            lastTanks = DEFAUL_LAST_TANK; //so tank chua ra
//            currentTank = 0; //so tank dang online
//
//            for (int i = 0; i < MAX_CURRENT_AI; i++) {
//                tankAis[i].reset(ID.TANK_AI);
//            }

            //reset particle
            ParticalManager.getInstance().Clear();

            sBackground.setVolume(Sound.MAX_VOLUME);
            //
        } catch (Exception e) {
            //System.out.println("Can not file map: MAP" + Global.level);
        }
        
        float mapW = TankMap.getInst().width;
        float mapH = TankMap.getInst().height;
        camera.Position_Camera(
                mapW/2, 28.869976f, mapH + 0.8f, 
                mapW/2, 27.494007f, mapH + 0.0f, 
                0.0f, 1.0f, 0.0f
        );
        
        cameraFo.SetPosition(
                mapW/2, 0, mapW/2, 
                Math.toRadians(45), Math.toRadians(0), 5, 
                0, 1, 0
        );
    }

    public void load() {
        isPause = false;

        camera = new Camera(); //init position when load map
        cameraFo = new CameraFo(1, 1, 1, Math.toRadians(45), Math.toRadians(0), 5, 0, 1, 0);
        
        //skybox
        m_skybox = new SkyBox();
        m_skybox.Initialize(5.0f);
        m_skybox.LoadTextures(
                "data/skybox/top.jpg", "data/skybox/bottom.jpg",
                "data/skybox/front.jpg", "data/skybox/back.jpg",
                "data/skybox/left.jpg", "data/skybox/right.jpg");

        //writer
        writer = new Writer("data/font/Motorwerk_80.fnt");
        //sound
        sBackground = ResourceManager.getInst().getSound("sound/bg_game.wav", true);
        sBackground.stop();
        sBackground.play();
        //----------------

        //playerBoss
        playerBoss = new Boss();
        playerBoss.load();
        
        //opponentBoss
        opponentBoss = new Boss();
        opponentBoss.load();
                
        //playerTank
        playerTank = new Tank();
        playerTank.load();
        
        //opponentTank
        opponentTank = new Tank();
        opponentTank.load();

//        this.tankAis = new TankAI[MAX_CURRENT_AI];
//        for (int i = 0; i < MAX_CURRENT_AI; i++) {
//            tankAis[i] = new TankAI(ID.TANK_AI);
//            tankAis[i].load();
//            tankAis[i].setAlive(false);
//        }

        //init map
        this.loadLevel(Global.level); //start at Global.level 0
    }

    public void unload() {
        GameEngine.getInst().tank3d.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        //pre-load main game

        //skybox
        //ResourceManager.getInst().deleteTexture("data/skybox/top.jpg");
        //ResourceManager.getInst().deleteTexture("data/skybox/bottom.jpg");
        //ResourceManager.getInst().deleteTexture("data/skybox/left.jpg");
        //ResourceManager.getInst().deleteTexture("data/skybox/right.jpg");
        //ResourceManager.getInst().deleteTexture("data/skybox/front.jpg");
        //ResourceManager.getInst().deleteTexture("data/skybox/back.jpg");
    }
    
//    long timeCreateAi = System.currentTimeMillis();
//
//    private void createNewAi() {
//        if (System.currentTimeMillis() - timeCreateAi >= TIME_CREATE_AI) {
//            timeCreateAi = System.currentTimeMillis();
//
//            //tankAI
//            if (currentTank < MAX_CURRENT_AI && lastTanks > 0) { //create new
//                for (int i = 0; i < MAX_CURRENT_AI; i++) {
//                    if (tankAis[i].isAlive() == false) {
//                        boolean isok = false; //check if have a position for it
//
//                        //get position
//                        for (Object v : TankMap.getInst().listTankAiPosition) {
//                            Vector3 pos = new Vector3((Vector3) v);
//                            tankAis[i].setPosition(pos);
//
//                            //check collision
//                            //player tank
//                            if (tankAis[i].getBound().isIntersect(playerTank.getBound())) {
//                                continue;
//                            } else { //list current tank AI
//                                boolean isok2 = true;
//                                for (int j = 0; j < MAX_CURRENT_AI; j++) {
//                                    if (tankAis[j].isAlive() == true) {
//                                        if (tankAis[i].getBound().isIntersect(tankAis[j].getBound())) {
//                                            isok2 = false;
//                                            break;
//                                        }
//                                    }
//                                }
//
//                                if (isok2) {
//                                    isok = true;
//                                    break;
//                                }
//                            }
//                        }
//
//                        if (isok) {
//                            if (TankMap.getInst().hasTankAIFast && TankMap.getInst().hasTankAISlow == false) { //fast
//                                tankAis[i].reset(Global.random.nextInt(2) + ID.TANK_AI);
//                            } else if (TankMap.getInst().hasTankAIFast && TankMap.getInst().hasTankAISlow) {
//                                tankAis[i].reset(Global.random.nextInt(3) + ID.TANK_AI);
//                            }
//
//                            tankAis[i].setAlive(true);
//                            tankAis[i].setDirection(Global.random.nextInt(CDirections.NUMBER_DIRECTION));
//                            lastTanks--;
//                            currentTank++;
//                            break;
//                        }
//                    }
//                }
//
//            }
//        }
//    }

    //
    // end initialize
    //
    //
    // check game change
    //
    private void checkPlayerLose() {
        if (playerLife <= 0) { //player lose
            GameEngine.getInst().attach(new GameOverView(this, 0));
        } else { // reset new life

            for (Object o : TankMap.getInst().listTankPosition) {
                Vector3 v = (Vector3) o;

                playerTank.reset(v, CDirections.RIGHT);

                boolean isOK = true;
                
                //check valid position
                if (opponentTank.isAlive()) {
                    if (opponentTank.getBound().isIntersect(playerTank.getBound())) {
                        isOK = false;
                    }
                }

                if (isOK == true) {
                    playerLife--;

                    //-----particle
                    particleNewTank(v);
                    //-----particle
                    break;
                }
            }
        }
    }
    
    private void checkOpponentLose() {
        if (opponentLife <= 0) { //opponent lose
            GameEngine.getInst().attach(new GameOverView(this, 1));
        } else { // reset new life

            for (Object o : TankMap.getInst().listTankAiPosition) {
                Vector3 v = (Vector3) o;

                opponentTank.reset(v, CDirections.LEFT);

                boolean isOK = true;
                
                //check valid position
                if (playerTank.isAlive()) {
                    if (playerTank.getBound().isIntersect( opponentTank.getBound())) {
                        isOK = false;
                    }
                }

                if (isOK == true) {
                    opponentLife--;

                    //-----particle
                    particleNewTank(v);
                    //-----particle
                    break;
                }
            }
        }
    }

//    private void checkLevelComplete() {
//        if (lastTanks <= 0 && currentTank <= 0) { //complete
//            GameEngine.getInst().attach(new NextLevelView(this));
//        }
//    }

    //
    // end check game change
    //
    //
    // check collision
    //
    private boolean checkTankCollision(Tank tank) {
        CRectangle rectTank = tank.getBound();
        if (tank.isAlive() == false) {
            //System.out.println("check collision DEAD tank @@@@@@@@@@@@@@@@@@@@@@");
            return false;
        }

        //playerBoss
        if (playerBoss.isAlive && rectTank.isIntersect(playerBoss.getBound())) {
            return true;
        }
        
        //opponentBoss
        if (opponentBoss.isAlive && rectTank.isIntersect(opponentBoss.getBound())) {
            return true;
        }

        //playerTank
        if (tank == playerTank) { //check player vs opponentTank
            //for (int i = 0; i < MAX_CURRENT_AI; i++) {
                if (opponentTank.isAlive()) { //is alive
                    if (rectTank.isIntersect(opponentTank.getBound())) {
                        return true;
                    }
                }
            //}
        } else if (tank == opponentTank) { //opponentTank
            //vs playerTank
            if (playerTank.isAlive()) {
                if (rectTank.isIntersect(playerTank.getBound())) {
                    return true;
                }
            }

//            //tankAi vs tankAi
//            for (int i = 0; i < MAX_CURRENT_AI; i++) {
//                if (tankAis[i].isAlive()) { //is alive
//                    if (tankAis[i] != tank) { //not the same
//                        if (rectTank.isIntersect(tankAis[i].getBound())) {
//                            return true;
//                        }
//                    }
//                }
//            }
        }

        return false;
    }

    private void checkBulletCollision() {
        //player's Bullets
        for (int i = 0; i < Tank.TANK_NUMBER_BULLETS; i++) {
            TankBullet bullet = playerTank.bullets[i];

            if (bullet.isAlive) {

                //vs playerBoss
                if (bullet.isAlive && playerBoss.isAlive) {
                    if (bullet.getBound().isIntersect(playerBoss.getBound())) {
                        playerBoss.explode();
                        bullet.isAlive = false;
                        playerBoss.isAlive = false;
                        this.isPause = true;

                        //System.out.println("COLLISION! ");
                        //System.out.println("BULLET: " + bullet.getBound().toString());
                        //System.out.println("BOSS: " + boss.getBound().toString());
                        GameEngine.getInst().attach(new GameOverView(this, 0));
                        return;
                    }
                }
                
                //vs opponentBoss
                if (bullet.isAlive && opponentBoss.isAlive) {
                    if (bullet.getBound().isIntersect(opponentBoss.getBound())) {
                        opponentBoss.explode();
                        bullet.isAlive = false;
                        opponentBoss.isAlive = false;
                        this.isPause = true;

                        //System.out.println("COLLISION! ");
                        //System.out.println("BULLET: " + bullet.getBound().toString());
                        //System.out.println("BOSS: " + boss.getBound().toString());
                        GameEngine.getInst().attach(new GameOverView(this, 1));
                        return;
                    }
                }

                //opponentTank
                //for (int j = 0; j < MAX_CURRENT_AI; j++) {

                    //vs opponentTank
                    //TankAI tankAi = tankAis[j];
                    if (opponentTank.isAlive() && bullet.isAlive) {
                        if (opponentTank.getBound().isIntersect(bullet.getBound())) {
                            //set isdead
                            bullet.isAlive = false;
                            if (opponentTank.hit()) {
                                //currentTank--;
                                opponentTank.explode();
                                Global.playerScore += SCORE_DELTA;
//                                this.checkLevelComplete();
                                
                                this.checkOpponentLose();
                            } else {
                                bullet.explode();
                            }
                            
                            continue; //bullet is dead, next to another bullet
                        }
                    }

                    //vs opponent's bullets
                    for (int k = 0; k < Tank.TANK_NUMBER_BULLETS; k++) {
                        TankBullet aiBullet = opponentTank.bullets[k];
                        if (aiBullet.isAlive && bullet.isAlive) {
                            if (aiBullet.getBound().isIntersect(bullet.getBound())) {
                                //set is dead
                                aiBullet.isAlive = false;
                                bullet.isAlive = false;

                                //particle
                                bullet.explode();
                                aiBullet.explode();

                                break;
                            }
                        }
                    }

//                    //optimize
//                    if (bullet.isAlive == false) {
//                        continue;
//                    }
                //}
            }
        }
        
        ////////////////////////////////////////////////////////////////////////

        //opponent's Bullets
        for (int i = 0; i < Tank.TANK_NUMBER_BULLETS; i++) {
            TankBullet bullet = opponentTank.bullets[i];

            if (bullet.isAlive) {

                //vs playerBoss
                if (bullet.isAlive && playerBoss.isAlive) {
                    if (bullet.getBound().isIntersect(playerBoss.getBound())) {
                        playerBoss.explode();
                        bullet.isAlive = false;
                        playerBoss.isAlive = false;
                        this.isPause = true;

                        //System.out.println("COLLISION! ");
                        //System.out.println("BULLET: " + bullet.getBound().toString());
                        //System.out.println("BOSS: " + boss.getBound().toString());
                        GameEngine.getInst().attach(new GameOverView(this, 0));
                        return;
                    }
                }
                
                //vs opponentBoss
                if (bullet.isAlive && opponentBoss.isAlive) {
                    if (bullet.getBound().isIntersect(opponentBoss.getBound())) {
                        opponentBoss.explode();
                        bullet.isAlive = false;
                        opponentBoss.isAlive = false;
                        this.isPause = true;

                        //System.out.println("COLLISION! ");
                        //System.out.println("BULLET: " + bullet.getBound().toString());
                        //System.out.println("BOSS: " + boss.getBound().toString());
                        GameEngine.getInst().attach(new GameOverView(this, 1));
                        return;
                    }
                }

                //playerTank
                //for (int j = 0; j < MAX_CURRENT_AI; j++) {

                    //vs playerTank
                    //TankAI tankAi = tankAis[j];
                    if (playerTank.isAlive() && bullet.isAlive) {
                        if (playerTank.getBound().isIntersect(bullet.getBound())) {
                            //set isdead
                            bullet.isAlive = false;
                            if (playerTank.hit()) {
                                //currentTank--;
                                playerTank.explode();
                                Global.opponentScore += SCORE_DELTA;
//                                this.checkLevelComplete();
                                
                                this.checkPlayerLose();
                            } else {
                                bullet.explode();
                            }
                            
                            continue; //bullet is dead, next to another bullet
                        }
                    }

                    //vs player's bullets
                    for (int k = 0; k < Tank.TANK_NUMBER_BULLETS; k++) {
                        TankBullet aiBullet = playerTank.bullets[k];
                        if (aiBullet.isAlive && bullet.isAlive) {
                            if (aiBullet.getBound().isIntersect(bullet.getBound())) {
                                //set is dead
                                aiBullet.isAlive = false;
                                bullet.isAlive = false;

                                //particle
                                bullet.explode();
                                aiBullet.explode();

                                break;
                            }
                        }
                    }

//                    //optimize
//                    if (bullet.isAlive == false) {
//                        continue;
//                    }
                //}
            }
        }
    }

    //
    // end check collision
    //
    private void particleStartGame() {
        float mapW = TankMap.getInst().width;
        float mapH = TankMap.getInst().height;
        
        float d = 5;
        
        Vector3 a = new Vector3(d, 0, d);
        float scale = 0.4f;
        float time = 0.02f;
        Explo shootParticle = new Explo(a.Clone(), time, scale);
        shootParticle.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle);

        a = new Vector3(mapW - d, 0, d);
        Explo shootParticle1 = new Explo(a.Clone(), time, scale);
        shootParticle1.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle1);
        
        a = new Vector3(mapW - d, 0, mapH - d);
        Explo shootParticle2 = new Explo(a.Clone(), time, scale);
        shootParticle2.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle2);
        
        a = new Vector3(d, 0, mapH - d);
        Explo shootParticle3 = new Explo(a.Clone(), time, scale);
        shootParticle3.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle3);
    }

    public void particleNewTank(Vector3 position) {
        float scale = 0.4f;
        float time = 0.01f;
        Explo shootParticle = new Explo(position.Clone(), time, scale);
        shootParticle.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle);
    }

    /**
     *
     * @param dt
     */
    public void update(long dt) {
        if (isPause) {
            return;
        }

        //

        //

        cameraFo.Update();

        if (bSliding) {
            cameraFo.beta -= deltaBeta;
            cameraFo.r += deltaR;
            if (cameraFo.r > 15.0f) {
                deltaBeta -= 0.0005;
            }
            if (deltaBeta < 0) {
                deltaBeta = 0;
                deltaR = 0;
                delayTime++;
                if (delayTime > DELAY_TIME) {
                    bSliding = false;
                    particleStartGame();
                }
            }
            //System.out.println(cameraFo.r);
        } else {
            handleInput(dt);
            opponentHandleInput(dt);
            
            playerTank.update(dt);
            opponentTank.update(dt);


            //check bullet collisiotn
            this.checkBulletCollision();

//            this.createNewAi();

//            //update ai
//            for (int i = 0; i < MAX_CURRENT_AI; i++) {
//                tankAis[i].update(dt);
//
//                if (tankAis[i].isAlive()) {
//                    if (this.checkTankCollision(tankAis[i])) {
//                        tankAis[i].rollBack();
//                        tankAis[i].randomNewDirection();
//                    }
//                }
//            }
        }
        //particle
        ParticalManager.getInstance().Update();
    }

    public void display() {
        //
        GL gl = Global.drawable.getGL();
        GLU glu = new GLU();
        gl.glLoadIdentity();
        //gl.glEnable(GL.GL_LIGHTING);

        gl.glDisable(GL.GL_LIGHTING);

        //gl.glEnable(GL.GL_LINE_SMOOTH);
        //gl.glEnable(GL.GL_POLYGON_SMOOTH);
        //gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
        //gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);

        gl.glDisable(GL.GL_BLEND);
        //gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        gl.glDisable(GL.GL_MULTISAMPLE);

        if (bTest || bSliding) {
            glu.gluLookAt(cameraFo.x, cameraFo.y, cameraFo.z, cameraFo.lookAtX, cameraFo.lookAtY, cameraFo.lookAtZ, cameraFo.upX, cameraFo.upY, cameraFo.upZ);
        } else {
            glu.gluLookAt(
                    camera.mPos.x, camera.mPos.y, camera.mPos.z,
                    camera.mView.x, camera.mView.y, camera.mView.z,
                    camera.mUp.x, camera.mUp.y, camera.mUp.z);
        }

        // skybox origin should be same as camera position
        m_skybox.Render(camera.mPos.x, camera.mPos.y, camera.mPos.z);

        //map
        TankMap.getInst().Render();

        playerTank.draw();
        opponentTank.draw();


//        //tankAis
//        for (int i = 0; i < MAX_CURRENT_AI; i++) {
//            tankAis[i].draw();
//        }

        playerBoss.draw();
        opponentBoss.draw();

        //particle
        ParticalManager.getInstance().Draw(gl, camera);

        //draw info
        float scale = 0.7f;
        writer.Render("LEVEL  " + Global.level, pLevel.x, pLevel.y, scale, scale, 1, 1, 1);
        writer.Render("AI  " + lastTanks, pAI.x, pAI.y, scale, scale, 1, 1, 1);
        writer.Render("LIFE " + playerLife, pLife.x, pLife.y, scale, scale, 1, 1, 1);
        writer.Render("SCORE", pScore.x, pScore.y, scale, scale, 1, 1, 1);
        writer.Render("" + Global.playerScore, pScoreValue.x, pScoreValue.y, scale, scale, 1, 1, 1);
    }
}
