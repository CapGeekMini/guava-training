package capgemini.training.guava.functionnal;

import java.util.AbstractMap;
import java.util.Map.Entry;

import capgemini.training.guava.basic.Bid;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public final class StandardAuction {
	
	/** 
	 * Comparaison de deux bid selon la r�gle standard.
	 * Tri par valeur de l'ench�re.
	 * Si deux ench�res sont de m�me valeur alors celle qui a �t� la premi�re dans le temps prend la priorit�.
	 */
	public static final Ordering<Bid> ORDERING = new Ordering<Bid>() {
			@Override
			public int compare(Bid left, Bid right) {
				return left.getValue().compareTo(right.getValue());
			}
		}.compound(new Ordering<Bid>() {
			@Override
			public int compare(Bid left, Bid right) {
				return right.getTime().compareTo(left.getTime());
			}
		});
	
	/**
	 * Fonction de validation pour une ench�re standard.
	 * Il doit exister au moins une ench�re (Le filtre sur le prix minimum est consid�r� comme fait).
	 */
	public static final Predicate<Iterable<Bid>> VALIDATION = new Predicate<Iterable<Bid>>() {

		@Override
		public boolean apply(Iterable<Bid> input) {
			return Lists.newArrayList(input).size() > 0;
		}
	};
	
	/**
	 * Fonction de recherche du couple enchereur gagnant / valeur de l'ench�re gagnant.
	 * 
	 * Sur une ench�re standard il s'agit de la personne et de la valeur de l'ench�re la plus �lev�e.
	 */
	public static final Function<Iterable<Bid>, Entry<String, Double>> WINNING_FUNCTION = new Function<Iterable<Bid>, Entry<String, Double>>() {

		@Override
		public Entry<String, Double> apply(Iterable<Bid> input) {
			if (Iterables.size(input) == 0) {
				return null;
			}
			Bid winnerBid = ORDERING.max(input);
			return new AbstractMap.SimpleEntry<String, Double>(winnerBid.getOwner(), winnerBid.getValue());
		}
	};

}
