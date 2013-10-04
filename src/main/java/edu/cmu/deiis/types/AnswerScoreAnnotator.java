package edu.cmu.deiis.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

public class AnswerScoreAnnotator  extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    
    //Take Question, Answer and NGrams, and assign score to each answer
    
    int begin, end, numUnigrams, numBigrams, numTrigrams ;
    double answerScore ;
    List<String> quesUnigrams = new ArrayList<String>() ;
    List<String> quesBigrams = new ArrayList<String>() ;
    List<String> quesTrigrams = new ArrayList<String>() ;
    
    FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type) ;
    Iterator questionIter = questionIndex.iterator();
    Question question = (Question) questionIter.next();
    begin = question.getBegin() ;
    end = question.getEnd() ;
    
    FSIndex ngramIndex = aJCas.getAnnotationIndex(NGram.type) ;
    Iterator ngramIter = ngramIndex.iterator();
    while (ngramIter.hasNext()){
      NGram ngram = (NGram) ngramIter.next();
      if (ngram.getBegin() < begin || ngram.getEnd() > end)
        continue ;
      else if (ngram.getCasProcessorId().matches("TokenUniGramAnnotator"))
        quesUnigrams.add(ngram.getCoveredText()) ;
      else if (ngram.getCasProcessorId().matches("TokenBiGramAnnotator"))
        quesBigrams.add(ngram.getCoveredText()) ;
      else 
        quesTrigrams.add(ngram.getCoveredText()) ;
    }
        
    FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);
    Iterator answerIter = answerIndex.iterator(); 
    while (answerIter.hasNext()) {
     Answer answer = (Answer) answerIter.next();
     begin = answer.getBegin() ;
     end = answer.getEnd() ;
     
     List<String> ansUnigrams = new ArrayList<String>() ;
     List<String> ansBigrams = new ArrayList<String>() ;
     List<String> ansTrigrams = new ArrayList<String>() ;
     ngramIter = ngramIndex.iterator();
     while (ngramIter.hasNext()){
       NGram ngram = (NGram) ngramIter.next();
       if (ngram.getBegin() < begin || ngram.getEnd() > end)
         continue ;
       else if (ngram.getCasProcessorId().matches("TokenUniGramAnnotator"))
         ansUnigrams.add(ngram.getCoveredText()) ;
       else if (ngram.getCasProcessorId().matches("TokenBiGramAnnotator"))
         ansBigrams.add(ngram.getCoveredText()) ;
       else 
         ansTrigrams.add(ngram.getCoveredText()) ;
     }
     
     numUnigrams = ansUnigrams.size() ;
     numBigrams = ansBigrams.size();
     numTrigrams = ansTrigrams.size();
          
     ansUnigrams.retainAll(quesUnigrams) ;
     ansBigrams.retainAll(quesBigrams) ;
     ansTrigrams.retainAll(quesTrigrams) ;
     
     answerScore = (double) ((double) ansUnigrams.size()*1.0/numUnigrams + (double) ansBigrams.size()*2.0/numBigrams + (double) ansTrigrams.size()*3.0/numTrigrams) ;
     
     AnswerScore annotation = new AnswerScore (aJCas) ;
     annotation.setBegin(begin) ;
     annotation.setEnd(end) ;
     annotation.setCasProcessorId("AnswerScoreAnnotator") ;
     annotation.setConfidence(1.0) ;
     annotation.setAnswer(answer) ;
     annotation.setScore(answerScore) ;
     annotation.addToIndexes() ;
    }
    
  }

}
