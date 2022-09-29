class Quiz{
  //instance variables
  String[][][] questions;
  String[] operations = {"-","+","*","/"};
  int questionNumber = 0;
  
  //helper functions
  String[][] createQuestion(){
    int num1 = int(random(0,11));
    int num2 = int(random(0,11));
    String[][] question = new String[2][4];
    String operation = operations[int(random(0,4))];
    question[0][0] = num1+" "+operation+" "+num2;
    if(operation == "+"){question[0][1] = str(num1+num2);}
    if(operation == "-"){question[0][1] = str(round(float(num1)-float(num2)));}
    if(operation == "*"){question[0][1] = str(num1*num2);}
    if(operation == "/"){
      if(num1!=0&&num2!=0){
          question[0][1] = str(float(num1)/float(num2));
        }
      if(num1==0||num2==0){
        question[0][1] = "0";
      }
    }
    question[1] = createAnswers(num1,num2);
    return question;
  }
  
  boolean isUnique(String[][] question){
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
  
  int nextEmptyIndex(String[] array){
    int emptyIndex = 0;
    for(int i=0;i<array.length;i++){
      if(array[i]==""||array[i]==null){
        emptyIndex = i;
      }
    }
    return emptyIndex;
  }
  
  String[] createAnswers(int num1,int num2){
    String[] temp = new String[4];
    temp[int(random(0,4))] = str(num1+num2);
    temp[nextEmptyIndex(temp)] = str(round(float(num1)-float(num2)));
    temp[nextEmptyIndex(temp)] = str(num1*num2);
    if(num1!=0&&num2!=0){
      temp[nextEmptyIndex(temp)] = str(float(num1)/float(num2));
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
  String[][][] buildQuiz(){
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
  void startQuiz(int questionNumber){
    fill(0);
    textSize(30);
    textAlign(CENTER);
    text("Click box to select answer. You cannot change answer, so choose carefully.",width/2,60);
    text(questions[questionNumber][0][0],width/2,height/4);
  }
}
