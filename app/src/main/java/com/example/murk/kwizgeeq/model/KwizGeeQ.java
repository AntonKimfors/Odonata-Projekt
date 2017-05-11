package com.example.murk.kwizgeeq.model;



import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ extends Observable{

    public ArrayList<Quiz> quizzList;
    public Quiz activeQuiz;
    public int activeQuestionIndex;
    private static KwizGeeQ singletonInstance = null;


    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            singletonInstance = new KwizGeeQ();
        }

        return singletonInstance;
    }

    private KwizGeeQ(){
        quizzList = new ArrayList<Quiz>();
        activeQuestionIndex = 1;
    }

    public Quiz getQuiz(int index){
        return quizzList.get(index);
    }

    public Question getQuestion(int quizIndex, int questionIndex){
        return quizzList.get(quizIndex).getQuestion(questionIndex);
    }

    /*public void addQuestion(int quizIndex, int questionIndex, Question question){
        Quiz quiz = quizzList.get(quizIndex);

        if(quiz== null){
            throw new IndexOutOfBoundsException("Quiz doesn't exist");
        }

        quiz.addQuestionOnIndex(questionIndex,question);
    }*/

    public int getQuizSize(int quizIndex){
        Quiz quiz = quizzList.get(quizIndex);
        return quiz.getSize();
    }

    public String getQuizName(int quizIndex){
        return quizzList.get(quizIndex).getName();
    }

    public void createUserQuestion(int quizIndex, int questionIndex, String questionStr,
                                   String questionImg, double xPosition, double yPosition,
                                   String audioFile){
        Quiz quiz = quizzList.get(quizIndex);
    }

    public void setUserQuestionText (int quizIndex, int questionIndex, String questionText){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionString(questionIndex,questionText);
    }

    public void setUserQuestionImagePath (int quizIndex, int questionIndex, String imagePath){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionImagePath(questionIndex,imagePath);

        UserQuestion userQuestion = (UserQuestion)getQuestion(quizIndex,questionIndex);
        setChanged();
        notifyObservers();
    }

    public void setUserQuestionAudioPath (int quizIndex, int questionIndex, String audioPath){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionImagePath(questionIndex,audioPath);
    }

    public void setUserQuestionPosition (int quizIndex, int questionIndex, double x, double y){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionPosition(questionIndex,x,y);
    }

    public void addTextAnswer(int quizIndex, int questionIndex, String answerText,
                                boolean isCorrect){
        Quiz quiz = quizzList.get(quizIndex);
        quiz.addTextAnswer(questionIndex,answerText,isCorrect);
    }

    private UserQuiz getUserQuiz(int quizIndex){
        Quiz quiz = quizzList.get(quizIndex);

        if(quiz== null){
            throw new IndexOutOfBoundsException("Quiz on index "+ quizIndex +" does not exist.");
        }

        if(quiz instanceof UserQuiz){
            return (UserQuiz)quiz;
        }

        throw new IllegalArgumentException("Requested quiz is not editible for users");
    }

    /**
     *  This method both returns value and has side effects
     *  but I have not came up with a better solution so far
     * @param name
     * @param color
     * @return the index of the created quiz
     */
    public int createUserQuiz(String name, int color){
        UserQuiz userQuiz = new UserQuiz(name, color);
        quizzList.add(userQuiz);
        return quizzList.indexOf(userQuiz);
    }

    public void firePlayQuiz(Quiz quiz){
        activeQuiz = quiz;

    }

    public void fireEditQuiz(Quiz quiz){
        activeQuiz = quiz;
    };

    public Iterator<Answer> getShuffledAnswers(int quizIndex, int questionIndex){
        return quizzList.get(quizIndex).getQuestion(questionIndex).answerIterator(true);
    }

    public boolean checkAnswerIsCorrect(Answer answer){
        return answer.isCorrect();
    }

    public void incActiveQuestion(){
        activeQuestionIndex++;
    }

    public int getAmountOfQuestions(int quizIndex){
        return getQuiz(quizIndex).getQuestions().size();
    }


}
