import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pGarayFinalProject extends PApplet {


//declare variables
Quiz math;
Door door1;
Door door2;
Door door3;
SoundFile yuck;
SoundFile scream;
SoundFile tasty;
String[][][] questions = {{{"","","",""},{"","","",""}},{{"","","",""},{"","","",""}},{{"","","",""},{"","","",""}}};
Answer answer1;
Answer answer2;
Answer answer3;
Answer answer4;
PImage cake;
PImage poo;
PImage monster;
PImage[] imageOrder = new PImage[3];
PImage door1Image = null;
PImage door2Image = null;
PImage door3Image = null;
boolean quizRound = false;
boolean prizeRound = false;
int points = 0;
boolean pointsEarned = false;
int currentQuestion = 0;
String correctAnswer = "";
String message = "";
String endMessage = "";
boolean ended = false;
boolean started = true;




//setup
public void setup(){
  //size(900,700);
  
  frameRate(100);
  //instantiate quiz and create questions
  math = new Quiz(questions,currentQuestion);
  questions = math.buildQuiz();
  //load images
  imageOrder[0] = poo = loadImage("poop.jpg");
  imageOrder[1] = monster = loadImage("monster.jpg");
  imageOrder[2] = cake = loadImage("cake.jpg");
  //instantiate objects
  door1 = new Door(width/4, height-300, 1, width/4, imageOrder[PApplet.parseInt(random(0,3))], false, color(150,50,100));
  door2 = new Door(width/2, height-300, 2, width/2, imageOrder[PApplet.parseInt(random(0,3))], false,color(50,150,100));
  door3 = new Door(3*(width/4), height-300, 3, 3*(width/4), imageOrder[PApplet.parseInt(random(0,3))], false, color(100,50,150));
  yuck = new SoundFile(this, "stinky.mp3");
  scream = new SoundFile(this, "scary.mp3");
  tasty = new SoundFile(this, "tasty.mp3");
}

//helper function to check answers
public void checkAnswer(String answer){
  if(answer.equals(correctAnswer) == true){
    points++;
    pointsEarned = true;
  }
  if(currentQuestion<2){
    currentQuestion = currentQuestion+1;
  } else {
    quizRound = false;
    delay(1000);
    prizeRound=true;
  }
}

//helper function to display end message
public void showEndMessage(String endMessage){
  rectMode(CENTER);
    fill(0);
    rect(width/2,height/2,800,200);
    fill(255);
    textAlign(CENTER);
    textSize(50);
    text(endMessage, width/2, height/2);
    fill(255);
    rect((width/2)+300,(height/2)+50,100,50);
    textSize(30);
    fill(0);
    text("Reset",(width/2)+300,(height/2)+60);
}

//display start screen
public void startScreen(){
    background(0, 100, 175);
    textAlign(CENTER);
    fill(245);
    textSize(60);
    text("All this for cake?", width/2,80);
    fill(0);
    textSize(30);
    text("Answer questions correctly to earn points.", width/2, height/4-20);
    text("Points will give chances to unlock prize doors.", width/2, height/4+20);
    text("Click on prize doors to open.", width/2, height/4+60);
    text("Click to start", width/2, height/2);
    if(started == true && mousePressed == true){
      quizRound = true;
      started = false;
    }
}

//start quiz
public void quizScreen(){
    background(255);
    //display points area
    fill(255);
    rect(width/2, height-50, width, 100);
    fill(0);
    textSize(30);
    text("Points: "+points, 50, height-45);
    //display quiz question
    math.startQuiz(currentQuestion);
    //assign correct answer to variable for comparison
    correctAnswer = questions[currentQuestion][0][1];
    //instantiate answer choices
    answer1 = new Answer(questions[currentQuestion][1][0], width/5, height/2);
    answer2 = new Answer(questions[currentQuestion][1][1], 2*(width/5), height/2);
    answer3 = new Answer(questions[currentQuestion][1][2], 3*(width/5), height/2);
    answer4 = new Answer(questions[currentQuestion][1][3], 4*(width/5), height/2);
    //display answer choices
    answer1.displayAnswer(questions[currentQuestion][1][0]);
    answer2.displayAnswer(questions[currentQuestion][1][1]);
    answer3.displayAnswer(questions[currentQuestion][1][2]);
    answer4.displayAnswer(questions[currentQuestion][1][3]);
}

//start prize round
public void prizeScreen(){
    background(125);
    //display chances area
    fill(255);
    rect(width/2, height-50, width, 100);
    fill(0);
    textSize(30);
    text("Chances: "+points, 100, height-45);
    textSize(50);
    text("Can you find the cake?",width/2,50);
    textSize(20);
    text("Click doors to open.",width/2,90);
    textSize(30);
    fill(0,25,225);
    text(message,width/2,150);
    //display doors and enable actions
    door1.drawDoor();
    points = door1.openDoor(points);
    door1.closeDoor();
    door2.drawDoor();
    points = door2.openDoor(points);
    door2.closeDoor();
    door3.drawDoor();
    points = door3.openDoor(points);
    door3.closeDoor();
    if(door1.wasCakeFound() == true || door2.wasCakeFound() == true || door3.wasCakeFound() == true){
      if(frameCount%100==0){
        showEndMessage("You won!");
        ended = true;
        noLoop();
      }
    }
    if(points == 0 && pointsEarned == false){
      if(frameCount%100==0){
        showEndMessage("Sorry, you have no points");
        ended = true;
        noLoop();
      }
    }
    if(points == 0 && pointsEarned == true && !(door1.wasCakeFound() == true || door2.wasCakeFound() == true || door3.wasCakeFound() == true)){
      if(frameCount%100==0){
        showEndMessage("Sorry, you didn't find the cake");
        ended = true;
        noLoop();
      }
    }
}

//run program
public void draw(){
  if(quizRound == false && prizeRound == false && started == true){
    startScreen();
  }
  if(quizRound == true && prizeRound == false && started == false){
    //launch quiz round
    quizScreen();
  }
  if(quizRound == false && prizeRound == true && started == false){
    //launch prize round
    prizeScreen();
  }
}

//enable answer submition and checking
public void mousePressed(){
  if(quizRound == true && prizeRound == false){
      if(mouseX > (width/5)-15 && mouseX < (width/5)+15 && mouseY > height/2-15 && mouseY < height/2+15){
        checkAnswer(answer1.submitAnswer());
      }
      if(mouseX > (2*(width/5))-15 && mouseX < (2*(width/5))+15 && mouseY > height/2-15 && mouseY < height/2+15){
        checkAnswer(answer2.submitAnswer());
      }
      if(mouseX > (3*(width/5))-15 && mouseX < (3*(width/5))+15 && mouseY > height/2-15 && mouseY < height/2+15){
        checkAnswer(answer3.submitAnswer());
      }
      if(mouseX > (4*(width/5))-15 && mouseX < (4*(width/5))+15 && mouseY > height/2-15 && mouseY < height/2+15){
        checkAnswer(answer4.submitAnswer());
      }
  }
  if(ended == true){
    if(mouseX > (width/2)+250 && mouseX < (width/2)+350 && mouseY > (height/2)+25 && mouseY < (height/2)+75){
      //reset variables
      quizRound = false;
      prizeRound = false;
      started = true;
      points = 0;
      pointsEarned = false;
      currentQuestion = 0;
      correctAnswer = "";
      message = "";
      ended = false;
      //restart sketch with new quiz
      loop();
      frameCount = -1;
    }
  }
}
class Answer{
  //instance variables
  String answer;
  int aX,aY;
  
  //constructor
  Answer(String answer,int aX, int aY){
    this.answer=answer;
    this.aX=aX;
    this.aY=aY;
  }
  
  //display answer
  public void displayAnswer(String answer){
    if(prizeRound==false){
      fill(255);
      rectMode(CENTER);
      rect(aX,aY,30,30);
      textAlign(LEFT);
      fill(0);
      textSize(20);
      text(answer,aX+25,aY+10);
    }
  }
  
  //confirm answer
  public String submitAnswer(){
        return answer;
  }
}
class Door{
  //instance variables
  //state, location, image, color
  boolean open = false;
  boolean alreadyOpened = false;
  boolean cakeFound = false;
  int dx,dy,dn;
  float doorX;
  PImage img;
  int dColor;
  
  //constructor
  Door(int dx,int dy,int dn, float doorX, PImage img, boolean open, int dColor){
    this.dx=dx;
    this.dy=dy;
    this.dn=dn;
    this.doorX=doorX;
    this.img=img;
    this.open=open;
    this.dColor=dColor;
  }
  
  //display door
  public void drawDoor(){
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
  public int openDoor(int points){
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
  public void closeDoor(){
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
  public void changeMessage(PImage doorImage, boolean door){
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
  public boolean wasCakeFound(){
    return cakeFound;
  }
}
class Quiz{
  //instance variables
  String[][][] questions;
  String[] operations = {"-","+","*","/"};
  int questionNumber = 0;
  
  //helper functions
  public String[][] createQuestion(){
    int num1 = PApplet.parseInt(random(0,11));
    int num2 = PApplet.parseInt(random(0,11));
    String[][] question = new String[2][4];
    String operation = operations[PApplet.parseInt(random(0,4))];
    question[0][0] = num1+" "+operation+" "+num2;
    if(operation == "+"){question[0][1] = str(num1+num2);}
    if(operation == "-"){question[0][1] = str(round(PApplet.parseFloat(num1)-PApplet.parseFloat(num2)));}
    if(operation == "*"){question[0][1] = str(num1*num2);}
    if(operation == "/"){
      if(num1!=0&&num2!=0){
          question[0][1] = str(PApplet.parseFloat(num1)/PApplet.parseFloat(num2));
        }
      if(num1==0||num2==0){
        question[0][1] = "0";
      }
    }
    question[1] = createAnswers(num1,num2);
    return question;
  }
  
  public boolean isUnique(String[][] question){
    int matches = 0;
    if(questions!=null){
    for(int i=0;i<questions.length;i++){
      if(question==questions[i]){
        matches++;
      }
    }
    }
    return !(matches>0);
  }
  
  public int nextEmptyIndex(String[] array){
    int emptyIndex = 0;
    for(int i=0;i<array.length;i++){
      if(array[i]==""||array[i]==null){
        emptyIndex = i;
      }
    }
    return emptyIndex;
  }
  
  public String[] createAnswers(int num1,int num2){
    String[] temp = new String[4];
    temp[PApplet.parseInt(random(0,4))] = str(num1+num2);
    temp[nextEmptyIndex(temp)] = str(round(PApplet.parseFloat(num1)-PApplet.parseFloat(num2)));
    temp[nextEmptyIndex(temp)] = str(num1*num2);
    if(num1!=0&&num2!=0){
      temp[nextEmptyIndex(temp)] = str(PApplet.parseFloat(num1)/PApplet.parseFloat(num2));
    }
    if(num1==0||num2==0){
      temp[nextEmptyIndex(temp)] = "0";
    }
    return temp;
  }
  
  //constructor
  Quiz(String[][][] questions,int questionNumber){
    this.questions=questions;
    this.questionNumber=questionNumber;
  }
  
  //load quiz questions
  public String[][][] buildQuiz(){
    int i = 0;
    while(i<3){
      String[][] question = createQuestion();
      if(isUnique(question)){
        questions[i] = question;
        i++;
      }
    }
    return questions;
  }
  
  //start/display quiz
  public void startQuiz(int questionNumber){
    fill(0);
    textSize(30);
    textAlign(CENTER);
    text("Click box to select answer. You cannot change answer, so choose carefully.",width/2,60);
    text(questions[questionNumber][0][0],width/2,height/4);
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pGarayFinalProject" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
