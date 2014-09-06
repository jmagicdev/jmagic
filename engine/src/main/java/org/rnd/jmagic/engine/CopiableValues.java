package org.rnd.jmagic.engine;

public class CopiableValues
{
	public final Characteristics[] characteristics;

	boolean originalWasOnStack;
	java.util.Set<Characteristics.Characteristic> toCopy;

	// This isn't mentioned in the rules as being a copiable value, but it has
	// to be in order for copies of unflipped flip cards to work
	protected Characteristics bottomHalf;

	public CopiableValues(Game game, GameObject original, GameObject target)
	{
		this(game, original, target, false);
	}

	public CopiableValues(Game game, GameObject original, GameObject target, boolean include, Characteristics.Characteristic... types)
	{
		Characteristics[] originalCharacteristics = original.getCharacteristics();
		this.characteristics = new Characteristics[originalCharacteristics.length];
		for(int i = 0; i < this.characteristics.length; ++i)
			this.characteristics[i] = originalCharacteristics[i].create(target);

		this.originalWasOnStack = false;
		this.toCopy = java.util.EnumSet.allOf(Characteristics.Characteristic.class);

		java.util.EnumSet<Characteristics.Characteristic> toCopy = (types.length > 0 ? java.util.EnumSet.of(types[0], types) : java.util.EnumSet.noneOf(Characteristics.Characteristic.class));
		java.util.EnumSet<Characteristics.Characteristic> dontCopy;
		if(include)
		{
			this.toCopy = toCopy;
			dontCopy = java.util.EnumSet.complementOf(toCopy);
		}
		else
		{
			this.toCopy = java.util.EnumSet.complementOf(toCopy);
			dontCopy = toCopy;
		}

		// 706.8d When applying a copy effect that doesn't copy a certain
		// characteristic, retains an original value for a certain
		// characteristic, or modifies the final value of a certain
		// characteristic, any characteristic-defining ability (see rule
		// 604.3) of the object being copied that defines that
		// characteristic is not copied. Example: Quicksilver Gargantuan is
		// a creature that reads, "You may have Quicksilver Gargantuan enter
		// the battlefield as a copy of any creature on the battlefield,
		// except it's still 7/7." Quicksilver Gargantuan enters the
		// battlefield as a copy of Tarmogoyf, which has a
		// characteristic-defining ability that defines its power and
		// toughness. Quicksilver Gargantuan does not have that ability. It
		// will be 7/7.
		for(Characteristics characteristics: this.characteristics)
		{
			java.util.List<Integer> toRemove = new java.util.LinkedList<Integer>();
			for(int abilityID: characteristics.staticAbilities)
			{
				StaticAbility ability = game.physicalState.get(abilityID);
				java.util.Collection<Characteristics.Characteristic> defined = ability.definesCharacteristics();
				if(!java.util.Collections.disjoint(defined, dontCopy))
					toRemove.add(abilityID);
			}
			for(int abilityID: toRemove)
				characteristics.removeStaticAbility(abilityID);
		}

		Characteristics flipped = original.getBottomHalf();
		if(flipped == null)
			this.bottomHalf = null;
		else
		{
			this.bottomHalf = flipped.create(target);
			this.bottomHalf.colors = this.characteristics[0].colors;
			this.bottomHalf.manaCost = this.characteristics[0].manaCost;
		}
		this.originalWasOnStack = (original.zoneID == game.physicalState.stack().ID);
	}

	public void apply(GameState state, GameObject object)
	{
		if(object == null)
			return;

		object.applyCopiableValues(state, this);
	}
}
