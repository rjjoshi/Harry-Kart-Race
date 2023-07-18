package se.atg.harrykart.dto;

import java.util.List;

public class Ranking {
	private List<String> horses;

	public Ranking(List<String> horses) {
		this.horses = horses;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int position=0;
		sb.append("{ \"ranking\": [");
		for(String horse:horses)
		{
			sb.append("{\"position\": " + ++position + ", \"horse\": \"" + horse +"\"}");
			if(position<=horses.size()-1)
			{
				sb.append(",");
			}
		}
		sb.append("] }");
		return sb.toString();
	}

}
