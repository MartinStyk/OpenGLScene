package sk.styk.martin.pv112.project;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import sk.styk.martin.pv112.project.camera.Camera;
import sk.styk.martin.pv112.project.camera.CameraBoundChecker;
import sk.styk.martin.pv112.project.camera.MyCamera;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends javax.swing.JFrame {

    public static int WINDOW_HEIGHT = 1080;
    public static int WINDOW_WIDTH = 1920;
    private static boolean shouldMove = true;
    private GLJPanel panel;
    private Camera camera;
    private Scene scene;
    private FPSAnimator animator;
    private boolean fullscreen = false;
    private Robot robot;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        GLProfile profile = GLProfile.get(GLProfile.GL3);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        setTitle("Projekt");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        GLCapabilities capabilities =
                new GLCapabilities(profile);
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(16);
        panel = new GLJPanel(capabilities);
        panel.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);

        add(panel, BorderLayout.CENTER);

        animator = new FPSAnimator(panel, 60, true);
        camera = new MyCamera(new CameraBoundChecker(19.8f, 7.8f, 29.9f, -19.8f, -7.8f, -29.8f));
        scene = new Scene(animator, camera);

        panel.addGLEventListener(scene);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                MainWindow.this.keyPressed(e);
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                camera.onMouseMove(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                camera.onMousePress(evt.getX(), evt.getY(), evt.getButton(), true);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                camera.onMousePress(evt.getX(), evt.getY(), evt.getButton(), false);
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera.onMouseMove(e.getX(), e.getY());
            }
        });
        animator.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;

//            case KeyEvent.VK_A:
//                toggleAnimation();
//                break;

            case KeyEvent.VK_T:
                toggleFullScreen();
                break;

            case KeyEvent.VK_F:
                scene.toggleFill();
                break;

            case KeyEvent.VK_L:
                scene.toggleLines();
                break;

            case KeyEvent.VK_W:
                camera.onKeyPressed('w');
                break;
            case KeyEvent.VK_A:
                camera.onKeyPressed('a');
                break;
            case KeyEvent.VK_S:
                camera.onKeyPressed('s');
                break;
            case KeyEvent.VK_D:
                camera.onKeyPressed('d');
                break;
            case KeyEvent.VK_N:
                scene.moreLight();
                break;
            case KeyEvent.VK_M:
                scene.lessLight();
                break;
            case KeyEvent.VK_E:
                scene.interact();
                break;

        }

        panel.display();
    }

    private void toggleAnimation() {
        if (animator.isAnimating()) {
            animator.stop();
        } else {
            animator.start();
        }
    }

    private void toggleFullScreen() {
        fullscreen = !fullscreen;

        if (animator.isAnimating()) {
            animator.stop();
        }

        dispose();
        setUndecorated(fullscreen);
        pack();

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();

        if (fullscreen) {
            device.setFullScreenWindow(this);
        } else {
            device.setFullScreenWindow(null);
        }
        setVisible(true);
        animator.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
