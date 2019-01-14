package factory.VertexFactory;

import vertex.Word;

public class WordVertexFactory extends VertexFactory {

	@Override
	public Word createVertex(String label, String[] args) {
		Word word = new Word(label);
		word.fillVertexInfo(args);
		return word;
	}



}
