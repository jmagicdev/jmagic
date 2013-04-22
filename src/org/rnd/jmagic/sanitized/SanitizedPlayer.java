package org.rnd.jmagic.sanitized;

public class SanitizedPlayer extends SanitizedIdentified
{
	public static final long serialVersionUID = 2L;

	public final java.util.Collection<Integer> attachments;
	public final java.util.Collection<Integer> defendingIDs;
	public final int lifeTotal;
	public final int poisonCounters;
	public final java.util.List<org.rnd.jmagic.engine.Counter> nonPoisonCounters;

	public final java.util.List<Integer> nonStaticAbilities;
	public final java.util.List<Integer> staticAbilities;
	public final java.util.List<Integer> keywordAbilities;
	public final org.rnd.jmagic.engine.ManaPool pool;

	public final java.util.Set<Integer> opponents;

	public final int library, graveyard, hand, sideboard;

	public SanitizedPlayer(org.rnd.jmagic.engine.Player p)
	{
		super(p);

		this.attachments = new java.util.LinkedList<Integer>(p.attachments);
		this.defendingIDs = new java.util.LinkedList<Integer>(p.defendingIDs);

		this.lifeTotal = p.lifeTotal;
		this.poisonCounters = p.countPoisonCounters();

		this.nonPoisonCounters = new java.util.LinkedList<org.rnd.jmagic.engine.Counter>();
		for(org.rnd.jmagic.engine.Counter c: p.counters)
			if(c.getType() != org.rnd.jmagic.engine.Counter.CounterType.POISON)
				this.nonPoisonCounters.add(c);

		this.nonStaticAbilities = SanitizedGameState.IDs(p.getNonStaticAbilities());
		this.staticAbilities = SanitizedGameState.IDs(p.getStaticAbilities());
		this.keywordAbilities = SanitizedGameState.IDs(p.getKeywordAbilities());

		this.pool = p.pool;

		this.library = p.getLibraryID();
		this.graveyard = p.getGraveyardID();
		this.hand = p.getHandID();
		this.sideboard = p.getSideboardID();

		this.opponents = new java.util.HashSet<Integer>();
		for(org.rnd.jmagic.engine.Player opp: org.rnd.jmagic.engine.generators.OpponentsOf.instance(org.rnd.jmagic.engine.generators.This.instance()).evaluate(p.state, p).getAll(org.rnd.jmagic.engine.Player.class))
			this.opponents.add(opp.ID);
	}
}
