package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedCharacteristics implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	public String name;
	public java.util.Collection<String> costs;
	public ManaPool manaCost;

	public java.util.Set<SuperType> superTypes;
	public java.util.Set<Type> types;
	public java.util.Set<SubType> subTypes;
	public java.util.Set<Color> colors;
	public java.util.Set<Color> colorIndicator;

	public int power, toughness;
	public int printedLoyalty;

	public java.util.List<Integer> abilities;
	public java.util.List<Integer> hiddenAbilities;

	public java.util.List<SanitizedMode> modes;
	public java.util.List<SanitizedMode> selectedModes;
	public Set numModes;
	public final java.util.Map<SanitizedTarget, java.util.List<SanitizedTarget>> chosenTargets;

	// this method takes a game state because it needs to get the keyword
	// abilities from the object
	public SanitizedCharacteristics(Characteristics c, GameState state)
	{
		this.name = c.name;

		this.costs = new java.util.LinkedList<String>();
		for(EventFactory f: c.costs)
			this.costs.add(f.name);
		this.manaCost = c.manaCost;

		this.superTypes = c.superTypes;
		this.types = c.types;
		this.subTypes = c.subTypes;
		this.colors = java.util.EnumSet.copyOf(c.colors);
		this.colorIndicator = java.util.EnumSet.copyOf(c.colorIndicator);

		this.power = c.power;
		this.toughness = c.toughness;

		this.printedLoyalty = c.loyalty;

		this.abilities = new java.util.LinkedList<Integer>();
		this.hiddenAbilities = new java.util.LinkedList<Integer>();
		this.abilities.addAll(c.abilityIDsInOrder);

		java.util.List<Integer> keywordIDs = c.keywordAbilities;
		for(int keywordID: keywordIDs)
		{
			Keyword k = state.get(keywordID);
			for(Identified a: k.getAbilitiesGranted().getAll(Identified.class))
			{
				this.abilities.remove((Integer)(a.ID));
				this.hiddenAbilities.add(a.ID);
			}
		}

		this.modes = new java.util.LinkedList<SanitizedMode>();
		this.selectedModes = new java.util.LinkedList<SanitizedMode>();
		this.chosenTargets = new java.util.HashMap<SanitizedTarget, java.util.List<SanitizedTarget>>();
		int index = 0;
		for(Mode m: c.modes)
		{
			SanitizedMode sanitizedMode = new SanitizedMode(m, m.sourceID, index);
			this.modes.add(sanitizedMode);

			if(c.selectedModes.contains(m))
			{
				this.selectedModes.add(sanitizedMode);

				for(int targetIndex = 0; targetIndex < m.targets.size(); ++targetIndex)
				{
					Target target = m.targets.get(targetIndex);
					if(c.chosenTargets.containsKey(target))
					{
						java.util.List<SanitizedTarget> sanitizedChosenTargets = new java.util.LinkedList<SanitizedTarget>();
						for(Target t: c.chosenTargets.get(target))
							if(null != t)
								sanitizedChosenTargets.add(new SanitizedTarget(t));
						this.chosenTargets.put(sanitizedMode.targets.get(targetIndex), sanitizedChosenTargets);
					}
				}
			}

			++index;
		}
		this.numModes = c.numModes;
	}
}
