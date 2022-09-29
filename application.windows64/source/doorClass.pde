class Door{
  //instance variables
  //state, location, image, color
  boolean open = false;
  boolean alreadyOpened = false;
  boolean cakeFound = false;
  int dx,dy,dn;
  float doorX;
  PImage img;
  color dColor;
  
  //constructor
  Door(int dx,int dy,int dn, float doorX, PImage img, boolean open, color dColor){
    this.dx=dx;
    this.dy=dy;
    this.dn=dn;
    this.doorX=doorX;
    this.img=img;
    this.open=open;
    this.dColor=dColor;
  }
  
  //display door
  void drawDoor(){
    fill(dColor);
    rectMode(CENTER);
    rect(dx,dy,200,400);
    imageMode(CENTER);
    image(img,dx,dy,198,398);
    rect(doorX,dy,198,398);
    rect(doorX,dy-150,160,80);
    rect(doorX-15,dy-45,130,95);
    rect(doorX,dy+100,160,180);
    ellipse(doorX+85,dy-20,15,25);
    fill(0);
    textAlign(CENTER);
    textSize(25);
    text(dn,dx,dy-225);
  }
  
  //open door, reveal image
  int openDoor(int points){
    if(prizeRound==true&&points>0&&mousePressed==true&&open==false&&alreadyOpened==false){
      if(mouseX>dx-100&&mouseX<dx+100&&mouseY>dy-200&&mouseY<dy+100&&open==false){
        open=true;
        doorX=doorX-200;
        points--;
        changeMessage(img, open);
      }
    }
    return points;
  }
  
  //close door, hide image
  void closeDoor(){
    if(prizeRound==true&&mousePressed == false){
      if(open==true){
        open=false;
        alreadyOpened=true;
        doorX=doorX+200;
        delay(1000);
      }
    }
  }
  
  //helper function for sound and messages
  void changeMessage(PImage doorImage, boolean door){
    if(doorImage == poo){
      message = "Well, you can eat it, but it tastes like...";
      if(door == true){
        yuck.play();
      }
    }
    if(doorImage == monster){
      message = "That's more likely to eat YOU.";
      if(door == true){
        scream.play();
      }
    }
    if(doorImage == cake){
      message = "Congratulations! The cake is real, and yours to enjoy.";
      tasty.play();
      cakeFound = true;
    }
  }
  
  //report if cake was found
  boolean wasCakeFound(){
    return cakeFound;
  }
}
