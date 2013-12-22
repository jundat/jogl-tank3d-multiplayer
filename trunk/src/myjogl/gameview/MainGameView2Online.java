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
public class MainGameView2Online extends MainGameView2Offline {

    //sliding...
//    public final static int SCORE_DELTA = 10;
//    public final static int NUMBER_OF_LIEF = 5;
//    public final static int DELAY_TIME = 0;
//    public final static float DELTA_BETA = 0.0513f;
//    public final static float DELTA_R = 0.25f;
//    //
//    public boolean isPause;    
//    public int playerLife = NUMBER_OF_LIEF;
//    public int opponentLife = NUMBER_OF_LIEF;
//    
//    public Tank playerTank;
//    public Tank opponentTank;
//    
//    public Boss playerBoss;
//    public Boss opponentBoss;
//    public Vector3 playerBossPosition;
//    public Vector3 opponentBossPosition;
//    
//    //
//    public SkyBox m_skybox;
//    public Camera camera;
//    public CameraFo cameraFo;
//    public boolean bSliding = true;
//    public float deltaBeta = DELTA_BETA;
//    public float deltaR = DELTA_R;
//    public int delayTime = 0;
//    public Writer writer;
//    
//    public Point pLevel = new Point(5, 690);
//    public Point pAI = new Point(5, 650);
//    public Point pLife = new Point(5, 610);
//    public Point pScore = new Point(820 + 256, 690);
//    public Point pScoreValue = new Point(838 + 256, 650);
//    
//    public Sound sBackground;

    public MainGameView2Online() {
        super();
    }

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
   
    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (isPause) {
            return;
        }

        //common
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && bSliding == false) {
            GameEngine.sClick.play();
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
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
    }

    public void handleInput(long dt) {
        if (isPause) {
            return;
        }
        KeyboardState state = KeyboardState.getState();
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

    public void loadLevel(int level) {
        Global.level = level;
        bSliding = true;
        deltaBeta = DELTA_BETA;
        deltaR = DELTA_R;
        delayTime = 0;

        try {
            //init map
            TankMap.getInst().LoadMap(level);

            //playerBoss
            this.playerBossPosition = TankMap.getInst().bossPosition.Clone();
            this.playerBoss.reset(playerBossPosition, CDirections.UP, playerBoss.isClientBoss);

            //opponentBoss
            this.opponentBossPosition = TankMap.getInst().bossAiPosition.Clone();
            this.opponentBoss.reset(opponentBossPosition, CDirections.UP, opponentBoss.isClientBoss);
            
            //playerTank
            Vector3 v = ((Vector3) TankMap.getInst().listTankPosition.get(0)).Clone();
            playerTank.reset(v.Clone(), CDirections.RIGHT);
            
            //opponentTank
            v = ((Vector3) TankMap.getInst().listTankAiPosition.get(0)).Clone();
            opponentTank.reset(v.Clone(), CDirections.LEFT);
     
            playerLife = NUMBER_OF_LIEF;
            opponentLife = NUMBER_OF_LIEF;
            ParticalManager.getInstance().Clear();
            sBackground.setVolume(Sound.MAX_VOLUME);
        } catch (Exception e) {
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
        
        //playerBoss
        playerBoss = new Boss(true);
        playerBoss.load();
        
        //opponentBoss
        opponentBoss = new Boss(false);
        opponentBoss.load();
                
        //playerTank
        playerTank = new Tank(true);
        playerTank.load();
        
        //opponentTank
        opponentTank = new Tank(false);
        opponentTank.load();

        //init map
        this.loadLevel(Global.level); //start at Global.level 0
    }

    public void unload() {
        GameEngine.getInst().tank3d.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    public void checkPlayerLose() {
        if (playerLife <= 0) { //player lose
            GameEngine.sFire.clone().play();
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
                    particleNewTank(v);
                    break;
                }
            }
        }
    }
    
    public void checkOpponentLose() {
        if (opponentLife <= 0) { //opponent lose
            GameEngine.sFire.clone().play();
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
                    particleNewTank(v);
                    break;
                }
            }
        }
    }

    public boolean checkTankCollision(Tank tank) {
        CRectangle rectTank = tank.getBound();
        if (tank.isAlive() == false) {
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
            if (opponentTank.isAlive()) {
                if (rectTank.isIntersect(opponentTank.getBound())) {
                    return true;
                }
            }
        } else if (tank == opponentTank) { //opponentTank
            //vs playerTank
            if (playerTank.isAlive()) {
                if (rectTank.isIntersect(playerTank.getBound())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkBulletCollision() {
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
                        GameEngine.sFire.clone().play();
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
                        GameEngine.sFire.clone().play();
                        GameEngine.getInst().attach(new GameOverView(this, 1));
                        return;
                    }
                }

                if (opponentTank.isAlive() && bullet.isAlive) {
                    if (opponentTank.getBound().isIntersect(bullet.getBound())) {
                        bullet.isAlive = false;
                        if (opponentTank.hit()) {
                            opponentTank.explode();
                            Global.playerScore += SCORE_DELTA;
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
                            aiBullet.isAlive = false;
                            bullet.isAlive = false;
                            bullet.explode();
                            aiBullet.explode();
                            break;
                        }
                    }
                }
            }
        }
        
        ////////////////////////////////////////////////////////////////////////

        //opponent's Bullets
        for (int i = 0; i < Tank.TANK_NUMBER_BULLETS; i++) {
            TankBullet bullet = opponentTank.bullets[i];

            if (bullet.isAlive) {
                if (bullet.isAlive && playerBoss.isAlive) {
                    if (bullet.getBound().isIntersect(playerBoss.getBound())) {
                        playerBoss.explode();
                        bullet.isAlive = false;
                        playerBoss.isAlive = false;
                        this.isPause = true;
                        GameEngine.sFire.clone().play();
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
                        GameEngine.sFire.clone().play();
                        GameEngine.getInst().attach(new GameOverView(this, 1));
                        return;
                    }
                }

                //vs playerTank
                if (playerTank.isAlive() && bullet.isAlive) {
                    if (playerTank.getBound().isIntersect(bullet.getBound())) {
                        bullet.isAlive = false;
                        if (playerTank.hit()) {
                            playerTank.explode();
                            Global.opponentScore += SCORE_DELTA;
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
                            aiBullet.isAlive = false;
                            bullet.isAlive = false;
                            bullet.explode();
                            aiBullet.explode();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void particleStartGame() {
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

    public void update(long dt) {
        if (isPause) {
            return;
        }
        
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
        } else {
            handleInput(dt);
            opponentHandleInput(dt);
            
            playerTank.update(dt);
            opponentTank.update(dt);

            this.checkBulletCollision();
        }
        //particle
        ParticalManager.getInstance().Update();
    }

    public void display() {
        //
        GL gl = Global.drawable.getGL();
        GLU glu = new GLU();
        gl.glLoadIdentity();
        gl.glDisable(GL.GL_LIGHTING);
        gl.glDisable(GL.GL_BLEND);
        gl.glDisable(GL.GL_MULTISAMPLE);

        if (bSliding) {
            glu.gluLookAt(cameraFo.x, cameraFo.y, cameraFo.z, cameraFo.lookAtX, cameraFo.lookAtY, cameraFo.lookAtZ, cameraFo.upX, cameraFo.upY, cameraFo.upZ);
        } else {
            glu.gluLookAt(
                    camera.mPos.x, camera.mPos.y, camera.mPos.z,
                    camera.mView.x, camera.mView.y, camera.mView.z,
                    camera.mUp.x, camera.mUp.y, camera.mUp.z);
        }

        m_skybox.Render(camera.mPos.x, camera.mPos.y, camera.mPos.z);

        //map
        TankMap.getInst().Render();

        playerTank.draw();
        opponentTank.draw();

        playerBoss.draw();
        opponentBoss.draw();

        //particle
        ParticalManager.getInstance().Draw(gl, camera);

        //draw info
        float scale = 0.7f;
        //writer.Render("LEVEL  " + Global.level, pLevel.x, pLevel.y, scale, scale, 1, 1, 1);
        //writer.Render("AI  " + lastTanks, pAI.x, pAI.y, scale, scale, 1, 1, 1);
        writer.Render("LIFE " + playerLife, pLevel.x, pLevel.y, scale, scale, 1, 1, 1);
        writer.Render("LIFE " + opponentLife, pScore.x, pScore.y, scale, scale, 1, 1, 1);
        //writer.Render("" + Global.playerScore, pScoreValue.x, pScoreValue.y, scale, scale, 1, 1, 1);
    }
}
