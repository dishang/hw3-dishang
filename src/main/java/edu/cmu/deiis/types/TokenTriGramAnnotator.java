package edu.cmu.deiis.types;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

public class TokenTriGramAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    
    //Take text document and annotate all trigrams
    
    String docText = aJCas.getDocumentText() ;
    String[] lines = docText.split("\n") ;
    String[] tokens ;
    int offset = 2;
    for(int i = 0; i < lines.length; i++){
      tokens = lines[i].split("\\s+") ;
      for(int j = 0; j < (tokens.length - 2); j++){
          if(j < 1)
            continue;
          else if (i > 0 && j < 2)
            continue ;
          NGram annotation = new NGram (aJCas) ;
          annotation.setBegin(offset) ;
          if(j != tokens.length -3) {
            annotation.setEnd(offset + tokens[j].length() + tokens[j+1].length() + tokens[j+2].length() + 2) ;
            offset = offset + tokens[j].length() + 1 ;
          }
          else {
            annotation.setEnd(offset + tokens[j].length() + tokens[j+1].length() + tokens[j+2].length() + 2 - 1)  ;
            offset = offset + tokens[j].length() + 1 + tokens[j+1].length() + 1 + tokens[j+2].length() + 2 + 4 ; 
          }
          annotation.setCasProcessorId("TokenTriGramAnnotator") ;
          annotation.setConfidence(1.0) ;
          annotation.addToIndexes() ;
          
      }
      
    }
    
  }

}
