/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.yugioh.laucher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mike
 */
public class Launcher {
    
    public void launch () {
        if (internetIsReachable()) {
            this.launchGame(true);
        } else {
            this.launchGame(false);
        }
    }
    
    private void launchGame (boolean online) {
        try {
            String param = online ? "online" : "offline";
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("java -cp exe/YugiohGame.jar mfiari.yugioh.game.jeu " + param);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while((line=input.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = pr.waitFor();
            System.out.println("Exited with error code "+exitVal);
            if (exitVal == 1) {
                input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
                while((line=input.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private boolean internetIsReachable () {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 localhost");
            int returnVal = p1.waitFor();
            return returnVal == 0;
        } catch (IOException | InterruptedException ex) {
            return false;
        }
    }
    
}
