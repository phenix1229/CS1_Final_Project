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
  void displayAnswer(String answer){
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
  String submitAnswer(){
        return answer;
  }
}
