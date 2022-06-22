import processing.core.PApplet;
import processing.core.PVector;

/** The object which is every projectile
 * They can be friendly or hostile
 * They move across the screen and collide with ships
 */
class Shot{
  PVector shotPos; //Position of the shot
  float shotAngle, //Angle for direction that it's moving
  shotVel, //The velocity of the shot
  shotDiam; //The diameter size of the shot
  float shotDamage;
  boolean shotHostile; //Weather the shot is hostile to the player ship

  //Creates a new shot for given x and y positions, angle, velocity, diameter and hostility
  Shot(float x, float y, float angle, float velocity, float diameter, boolean hostile, float damage){
    //Set the properties of the shot to the specified ones
    shotPos = new PVector(x, y);
    shotAngle = angle;
    shotVel = velocity;
    shotDiam = diameter;
    shotHostile = hostile;
    shotDamage = damage;
  }
  
  //Draw the shot on the screen
  void drawShot(MainSketch canvas){
    if(shotHostile){
    	canvas.fill(255, 0, 0); //Red
    }
    else{
    	canvas.fill(100, 150, 255); //Blue
    }
    canvas.ellipse(shotPos.x, shotPos.y, shotDiam, shotDiam);
  }
  
  //Move the shot based of its own velocity
  void moveShot(){
    shotPos.x += Math.cos(shotAngle) * shotVel;
    shotPos.y += Math.sin(shotAngle) * shotVel;
  }
  
  //Check if the shot is when a specified box
  boolean within(float x, float y, float rad){
    return Math.abs(shotPos.x - x) < rad && Math.abs(shotPos.y - y) < rad;
  }
  
  //Check if the shot is colliding with a ship of given location, size and hostility
  boolean touching(float shipX, float shipY, float shipDiam, boolean yourShip){
    return (yourShip == shotHostile) && Math.hypot(shipX - shotPos.x, shipY - shotPos.y) < (shipDiam + shotDiam)/2;
  }
  
  boolean notOnScreen(PVector screenPos, float width, float height){
    return shotPos.x > -screenPos.x + width || shotPos.x < -screenPos.x || shotPos.y > -screenPos.y + height || shotPos.y < -screenPos.y;
  }
  
  float getDamage(){
    return shotDamage;
  }
}
