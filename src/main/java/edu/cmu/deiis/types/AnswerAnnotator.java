package edu.cmu.deiis.types;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

public class AnswerAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    
    //Take text document and annotate all answers
    
    String docText = aJCas.getDocumentText() ;
    String[] lines = docText.split("\n") ;
    String answer ;
    int offset = lines[0].length() + 1 ;
    for (int i = 1; i < lines.length; i++){
      Answer annotation = new Answer (aJCas) ;
      answer = lines[i] ;
      annotation.setBegin(4 + offset) ;
      annotation.setEnd(offset + answer.length() - 1) ;
      if(Character.getNumericValue(answer.charAt(2)) == 1)  
         annotation.setIsCorrect(true) ;
      else
        annotation.setIsCorrect(false) ;
      annotation.setCasProcessorId("AnswerAnnotator") ;
      annotation.setConfidence(1.0) ;
      annotation.addToIndexes() ;
      offset = offset + answer.length() + 1 ;
    }
    
    
  }

}
