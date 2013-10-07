package edu.cmu.deiis.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.cleartk.ne.type.NamedEntity;
import org.cleartk.ne.type.NamedEntityMention;

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
    
    List<String> nemUnigrams = new ArrayList<String>() ;
    List<String> nemBigrams = new ArrayList<String>() ;
    List<String> nemTrigrams = new ArrayList<String>() ;
    
    FSIndex namedEntityIndex = aJCas.getAnnotationIndex(NamedEntityMention.type);
    Iterator nemIter = namedEntityIndex.iterator(); 
    while (nemIter.hasNext()) {
      NamedEntityMention nem = (NamedEntityMention) nemIter.next();
      String[] nemText = nem.getCoveredText().split("\\s+") ;
      if(nemText[0].matches("A") || nemText[0].matches("Q") || nemText[0].matches("1") || nemText[0].matches("0"))
        continue ;
      else if (nemText.length == 1)
        nemUnigrams.add(nemText.toString()) ;
      else if (nemText.length == 2)
        nemBigrams.add(nemText.toString()) ;
      else 
        nemTrigrams.add(nemText.toString()) ;
    }
    
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
     
     ansUnigrams.removeAll(nemUnigrams) ;
     ansBigrams.removeAll(nemBigrams) ;
     ansTrigrams.removeAll(nemTrigrams) ;
     
     answerScore = (double) ((double) ansUnigrams.size()*1.0/numUnigrams + (double) ansBigrams.size()*2.0/numBigrams + (double) ansTrigrams.size()*3.0/numTrigrams) ;
                  
     annotation = new AnswerScore (aJCas) ;
     annotation.setBegin(begin) ;
     annotation.setEnd(end) ;
     annotation.setCasProcessorId("AnswerScoreAnnotatorNE") ;
     annotation.setConfidence(1.0) ;
     annotation.setAnswer(answer) ;
     annotation.setScore(answerScore) ;
     annotation.addToIndexes() ;
    }
    
  }

}
