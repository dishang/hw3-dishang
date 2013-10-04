package edu.cmu.deiis.types;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

public class Evaluator extends JCasAnnotator_ImplBase{

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    
    //Compute precision @ N
    
    int numCorrect  = 0 , rankCorrect = 0 ;
    double precision = 0.0 ;
    Answer[] answers ;
    Map<Answer, Double> answerScoreMap = new HashMap<Answer,Double> () ;
    
    FSIndex answerScoreIndex = aJCas.getAnnotationIndex(AnswerScore.type) ;
    Iterator answerScoreIter = answerScoreIndex.iterator();
    while(answerScoreIter.hasNext()){
      AnswerScore answerScore = (AnswerScore) answerScoreIter.next();
      answerScoreMap.put(answerScore.getAnswer(), answerScore.getScore()) ;
      if(answerScore.getAnswer().getIsCorrect())
        numCorrect = numCorrect + 1 ;
    }
    answers = answerScoreMap.keySet().toArray(new Answer[0]) ;
    Arrays.sort(answers, new AnswerComparator (answerScoreMap)) ;
    
    for (int i = 0; i < numCorrect ; i ++){
      if (answers[i].getIsCorrect())
        rankCorrect = rankCorrect + 1 ;
      System.out.println(answers[i].getCoveredText() + " , " + answerScoreMap.get(answers[i])) ;
    }
     
    precision = (double) (rankCorrect*1.0/numCorrect) ;
    
    Evaluate annotation = new Evaluate (aJCas) ;
    annotation.setBegin(0) ;
    annotation.setEnd(aJCas.getDocumentText().length()) ;
    annotation.setPrecision(precision) ;
    annotation.setCasProcessorId("Evaluator") ;
    annotation.setConfidence(1.0) ;
    annotation.addToIndexes() ;
    
    
    System.out.println(precision) ;
  }
  
  private static class AnswerComparator implements Comparator<Answer> {
    
    private Map<Answer, Double> _answerScoreMap ;
    
    public AnswerComparator(Map<Answer, Double> answerScoreMap) {
      _answerScoreMap = answerScoreMap ;
    }
    
    @Override
    public int compare(Answer o1, Answer o2) {
      // TODO Auto-generated method stub
      
      int compResult ;
      
      if (_answerScoreMap.get(o2) - _answerScoreMap.get(o1) > 0.0)
        compResult = 1 ;
      else if (_answerScoreMap.get(o2) - _answerScoreMap.get(o1) == 0.0)
        compResult = 0 ;
      else 
        compResult = -1 ;
      
      return compResult ;
    }
    
  }
}
