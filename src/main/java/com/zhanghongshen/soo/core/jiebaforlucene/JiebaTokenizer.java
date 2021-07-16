package com.zhanghongshen.soo.core.jiebaforlucene;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.SegmentingTokenizerBase;
import org.apache.lucene.util.AttributeFactory;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/21
 */
public class JiebaTokenizer extends SegmentingTokenizerBase {
    /** used for breaking the text into sentences */
    private static final BreakIterator SENTENCE_PROTO = BreakIterator.getSentenceInstance(Locale.ROOT);

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

    /** Jieba Segmenter and tokens */
    private final JiebaSegmenter segmenter = new JiebaSegmenter();
    private Iterator<SegToken> tokenIter;

    /** jieba  segMode */
    private final SegMode segMode;

    /** record  field sentence offset */
    private int sentenceStart = 0;
    private int sentenceEnd = 0;

    /**
     * used for sentence witch length > 1024
     * if sentence length greater than 1024, setNextSentence method parameter sentenceStart will be 0
     * fieldIdCounter is filed id counter,
     * curFieldId
     * */
    private long fieldIdCounter = 0;
    private final static long MAX_FIELD_ID=1000000;
    private long curFieldId = 0;
    private int fieldOffset = 0;

    /** Creates a new JiebaTokenizer */
    public JiebaTokenizer(SegMode segMode) {
        super(SENTENCE_PROTO);
        this.segMode = segMode;
    }

    /** Creates a new JiebaTokenizer, supplying the AttributeFactory */
    public JiebaTokenizer(SegMode segMode, AttributeFactory factory) {
        super(factory, (BreakIterator)SENTENCE_PROTO.clone());
        this.segMode = segMode;
    }

    @Override
    protected void setNextSentence(int sentenceStart, int sentenceEnd) {
        //after reset, new field start.
        if (curFieldId != fieldIdCounter){
            curFieldId = fieldIdCounter;
            this.sentenceStart = sentenceStart;
            this.sentenceEnd = sentenceEnd;
        }else{//field not change
            if (sentenceStart == 0){
                fieldOffset = this.sentenceEnd;
            }
            this.sentenceStart = sentenceStart + fieldOffset;
            this.sentenceEnd = sentenceEnd + fieldOffset;
        }

        String sentence = new String(buffer, sentenceStart, sentenceEnd - sentenceStart);
        List<SegToken> tokens = segmenter.process(sentence, segMode);

        //need order SegTokens by startOffset
        Collections.sort(tokens,(o1,o2)->o1.startOffset - o2.startOffset);
        tokenIter = tokens.iterator();
    }

    @Override
    protected boolean incrementWord() {
        if(tokenIter != null && tokenIter.hasNext()){
            SegToken token = tokenIter.next();
            clearAttributes();
            termAtt.copyBuffer(token.word.toCharArray(), 0, token.word.length());
            int startOffset = sentenceStart + token.startOffset;
            int endOffset = sentenceStart + token.endOffset;
            offsetAtt.setOffset(startOffset, endOffset);
            typeAtt.setType("Jieba Word");
            return true;
        }
        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        fieldIdCounter = (++fieldIdCounter % MAX_FIELD_ID);
        tokenIter = null;
        sentenceStart = 0;
        sentenceEnd = 0;
        fieldOffset = 0;
    }
}
