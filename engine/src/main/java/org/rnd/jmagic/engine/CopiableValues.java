package org.rnd.jmagic.engine;

public class CopiableValues
{
	public final Characteristics characteristics;

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
		this.characteristics = original.getCharacteristics().create(target);

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
		java.util.List<Integer> toRemove = new java.util.LinkedList<Integer>();
		for(int abilityID: this.characteristics.staticAbilities)
		{
			StaticAbility ability = game.physicalState.get(abilityID);
			java.util.Collection<Characteristics.Characteristic> defined = ability.definesCharacteristics();
			if(!java.util.Collections.disjoint(defined, dontCopy))
				toRemove.add(abilityID);
		}
		for(int abilityID: toRemove)
			this.characteristics.removeStaticAbility(abilityID);

		Characteristics flipped = original.getBottomHalf();
		if(flipped == null)
			this.bottomHalf = null;
		else
		{
			this.bottomHalf = flipped.create(target);
			this.bottomHalf.colors = this.characteristics.colors;
			this.bottomHalf.manaCost = this.characteristics.manaCost;
		}
		this.originalWasOnStack = (original.zoneID == game.physicalState.stack().ID);
	}

	@SuppressWarnings("unchecked")
	public void apply(GameState state, GameObject object)
	{
		if(object == null)
			return;

		object.applyCopiableValues(state, this);

		/*
		Characteristics toApply = this.characteristics;
		if(this.bottomHalf != null && object.isFlipped())
			toApply = this.bottomHalf;

		if(this.toCopy.contains(Characteristics.Characteristic.NAME))
			object.setName(toApply.name);

		if(this.toCopy.contains(Characteristics.Characteristic.POWER))
			object.setPower(toApply.power);

		if(this.toCopy.contains(Characteristics.Characteristic.TOUGHNESS))
			object.setToughness(toApply.toughness);

		if(this.toCopy.contains(Characteristics.Characteristic.LOYALTY))
			object.setPrintedLoyalty(toApply.loyalty);

		if(this.toCopy.contains(Characteristics.Characteristic.MANA_COST))
		{
			if(toApply.manaCost != null)
				object.setManaCost(new ManaPool(toApply.manaCost));
			else
				object.setManaCost(null);
		}

		if(this.toCopy.contains(Characteristics.Characteristic.RULES_TEXT))
		{
			object.setMinimumX(toApply.minimumX);

			object.removeAllAbilities();
			{
				for(Integer abilityID: toApply.nonStaticAbilities)
					object.addAbility(state.<NonStaticAbility>get(abilityID));
				for(Integer abilityID: toApply.staticAbilities)
					object.addAbility(state.<StaticAbility>get(abilityID));

				// Add keywords without applying them, since the abilities they
				// would grant have already been taken care of.
				for(Integer abilityID: toApply.keywordAbilities)
					object.addAbility(state.<Keyword>get(abilityID), false);

				object.setAbilityIDsInOrder(new java.util.List[] {new java.util.LinkedList<Integer>(toApply.abilityIDsInOrder)});
			}

			object.clearCosts();
			{
				for(EventFactory cost: toApply.costs)
					object.getCosts().add(cost);
			}

			for(java.util.List<Mode> modes: object.getModes())
				modes.clear();
			{
				for(Mode mode: toApply.modes)
					object.getModes()[0].add(mode);
			}

			object.setBottomHalf(this.bottomHalf);
		}

		if(this.toCopy.contains(Characteristics.Characteristic.COLOR))
		{
			object.getColors().clear();
			object.getColors().addAll(toApply.colors);
			object.getColorIndicator().clear();
			object.getColorIndicator().addAll(toApply.colorIndicator);
		}

		if(this.toCopy.contains(Characteristics.Characteristic.TYPES))
		{
			object.removeSuperTypes(object.getSuperTypes());
			object.addSuperTypes(toApply.superTypes);

			object.removeTypes(object.getTypes());
			object.addTypes(toApply.types);

			object.removeSubTypes(object.getSubTypes());
			object.addSubTypes(toApply.subTypes);
		}

		if(this.originalWasOnStack)
		{
			if(this.toCopy.contains(Characteristics.Characteristic.CHOICES_MADE_WHEN_PLAYING))
			{
				object.setAlternateCost(toApply.alternateCost);

				for(java.util.Collection<CostCollection> optionalAdditional: object.getOptionalAdditionalCostsChosen())
					optionalAdditional.clear();
				for(CostCollection cost: toApply.optionalAdditionalCostsChosen)
					object.getOptionalAdditionalCostsChosen()[0].add(cost);

				for(java.util.List<Integer> selectedModeNumbers: object.getSelectedModeNumbers())
					selectedModeNumbers.clear();
				object.getSelectedModeNumbers()[0].addAll(toApply.selectedModeNumbers);

				object.setValueOfX(toApply.valueOfX);

				for(java.util.Map<Target, java.util.List<Target>> chosenTargets: object.getChosenTargets())
					chosenTargets.clear();
				object.getChosenTargets()[0].putAll(toApply.chosenTargets);
			}

			if(toApply.sourceID != -1 && (object.isActivatedAbility() || object.isTriggeredAbility()))
				((NonStaticAbility)object).sourceID = toApply.sourceID;
		}
		*/
	}
}
