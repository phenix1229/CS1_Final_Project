import processing.sound.*;
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
void setup(){
  //size(900,700);
  fullScreen();
  frameRate(100);
  //instantiate quiz and create questions
  math = new Quiz(questions,currentQuestion);
  questions = math.buildQuiz();
  //load images
  imageOrder[0] = poo = loadImage("poop.jpg");
  imageOrder[1] = monster = loadImage("monster.jpg");
  imageOrder[2] = cake = loadImage("cake.jpg");
  //instantiate objects
  door1 = new Door(width/4, height-300, 1, width/4, imageOrder[int(random(0,3))], false, color(150,50,100));
  door2 = new Door(width/2, height-300, 2, width/2, imageOrder[int(random(0,3))], false,color(50,150,100));
  door3 = new Door(3*(width/4), height-300, 3, 3*(width/4), imageOrder[int(random(0,3))], false, color(100,50,150));
  yuck = new SoundFile(this, "stinky.mp3");
  scream = new SoundFile(this, "scary.mp3");
  tasty = new SoundFile(this, "tasty.mp3");
}

//helper function to check answers
void checkAnswer(String answer){
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
void showEndMessage(String endMessage){
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
void startScreen(){
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
void quizScreen(){
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
void prizeScreen(){
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
void draw(){
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
void mousePressed(){
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
