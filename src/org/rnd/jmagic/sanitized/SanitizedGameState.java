package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedGameState implements java.util.Map<Integer, SanitizedIdentified>, java.io.Serializable
{
	private static final long serialVersionUID = 5L;

	public static java.util.List<Integer> IDs(Iterable<? extends org.rnd.jmagic.engine.Identified> list)
	{
		java.util.List<Integer> ret = new java.util.LinkedList<Integer>();
		for(org.rnd.jmagic.engine.Identified i: list)
			ret.add(i.ID);
		return java.util.Collections.unmodifiableList(ret);
	}

	private final java.util.Map<Integer, SanitizedIdentified> actual;

	public final int battlefield;

	public final Phase.PhaseType phase;

	public final java.util.List<Integer> players;

	public final int commandZone;

	public final int exileZone;

	public final int stack;

	/**
	 * The type of step the game is currently in.
	 */
	public final Step.StepType step;

	/**
	 * The ID of the player who controls this turn.
	 */
	public final int turn;

	public SanitizedGameState(GameState full, Player whoFor, int... ensurePresent)
	{
		this.actual = new java.util.HashMap<Integer, SanitizedIdentified>();

		for(Zone zone: full.getAll(Zone.class))
			this.addZoneAndObjects(whoFor, zone);

		for(Player p: full.players)
		{
			this.actual.put(p.ID, new SanitizedPlayer(p));
			for(Keyword k: p.getKeywordAbilities())
				this.actual.put(k.ID, new SanitizedIdentified(k));
			for(NonStaticAbility a: p.getNonStaticAbilities())
				this.actual.put(a.ID, a.sanitize(full, whoFor));
		}

		Zone battlefield = full.battlefield();
		this.battlefield = battlefield.ID;

		Zone commandZone = full.commandZone();
		this.commandZone = commandZone.ID;

		Phase currentPhase = full.currentPhase();
		if(null != currentPhase)
			this.phase = currentPhase.type;
		else
			this.phase = null;

		Zone exileZone = full.exileZone();
		this.exileZone = exileZone.ID;

		this.players = IDs(full.players);

		Zone stack = full.stack();
		this.stack = stack.ID;

		Step currentStep = full.currentStep();
		if(null != currentStep)
			this.step = currentStep.type;
		else
			this.step = null;

		Turn currentTurn = full.currentTurn();
		if(null != currentTurn)
			this.turn = currentTurn.getOwner(full).ID;
		else
			this.turn = -1;

		for(int i: ensurePresent)
			if(!this.containsKey(i))
			{
				Identified id = (full.containsIdentified(i) ? full.get(i) : full.game.physicalState.get(i));

				this.actual.put(i, (SanitizedIdentified)((Sanitizable)id).sanitize(full.game.physicalState, whoFor));
			}
	}

	private void addObject(GameObject o, Player whoFor)
	{
		if(o instanceof NonStaticAbility)
			this.put(o.ID, o.sanitize(o.state, whoFor));
		else
		{
			SanitizedGameObject sanitizedObject = new SanitizedGameObject(o, whoFor);
			this.put(o.ID, sanitizedObject);
			for(SanitizedIdentified a: sanitizedObject.sanitizeAbilities(o.state, whoFor))
				this.put(a.ID, a);
		}
	}

	private void addZoneAndObjects(Player whoFor, Zone z)
	{
		this.actual.put(z.ID, new SanitizedZone(z));

		for(GameObject o: z)
			if(o.isVisibleTo(whoFor))
				addObject(o, whoFor);
	}

	@Override
	public void clear()
	{
		this.actual.clear();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return this.actual.containsKey(key);
	}

	public java.util.List<Integer> getPlayerCycle(int firstPlayer)
	{
		java.util.List<Integer> ret = new java.util.LinkedList<Integer>(this.players);
		if(ret.size() > 0 && ret.contains(firstPlayer))
			while(ret.get(0) != firstPlayer)
				ret.add(ret.remove(0));
		return ret;
	}

	@Override
	public boolean containsValue(Object value)
	{
		return this.actual.containsValue(value);
	}

	@Override
	public java.util.Set<java.util.Map.Entry<Integer, SanitizedIdentified>> entrySet()
	{
		return this.actual.entrySet();
	}

	@Override
	public SanitizedIdentified get(Object key)
	{
		return this.actual.get(key);
	}

	@Override
	public boolean isEmpty()
	{
		return this.actual.isEmpty();
	}

	@Override
	public java.util.Set<Integer> keySet()
	{
		return this.actual.keySet();
	}

	@Override
	public SanitizedIdentified put(Integer key, SanitizedIdentified value)
	{
		return this.actual.put(key, value);
	}

	@Override
	public void putAll(java.util.Map<? extends Integer, ? extends SanitizedIdentified> m)
	{
		this.actual.putAll(m);
	}

	@Override
	public SanitizedIdentified remove(Object key)
	{
		return this.actual.remove(key);
	}

	@Override
	public int size()
	{
		return this.actual.size();
	}

	@Override
	public java.util.Collection<SanitizedIdentified> values()
	{
		return this.actual.values();
	}
}
