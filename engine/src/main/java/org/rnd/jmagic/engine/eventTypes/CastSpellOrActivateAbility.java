package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CastSpellOrActivateAbility extends EventType
{
	public static final EventType INSTANCE = new CastSpellOrActivateAbility();

	private CastSpellOrActivateAbility()
	{
		super("CAST_SPELL_OR_ACTIVATE_ABILITY");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

		boolean validTargets = true;

		java.util.List<Mode>[] allModes = object.getModes();
		for(int i = 0; i < allModes.length; ++i)
		{
			java.util.List<Mode> modes = allModes[i];
			if(!modes.isEmpty())
			{
				validTargets = false;

				// TODO : Is this code duplication? Does this functionality
				// differ from mode.canBeChosen()? If so, how is the code
				// different, and what is the reason for the difference?
				// -RulesGuru
				int legalModes = 0;
				for(Mode mode: modes)
				{
					java.util.LinkedList<Target> targets = new java.util.LinkedList<Target>();
					for(Target target: mode.targets)
						targets.add(target);
					if(this.checkTargets(game, object, targets))
						legalModes++;
				}

				Integer minimum = Minimum.get(object.getNumModes()[i]);
				if(minimum != null && legalModes >= minimum)
				{
					validTargets = true;
					break;
				}
			}
		}

		if(!validTargets)
			return false;

		if(parameters.containsKey(Parameter.ALTERNATE_COST))
		{
			// if it hasn't been set, GameObject.getDefinedX returns -1,
			// which still works for this.
			// From Gatherer Rulings: If another spell or ability instructs
			// you to cast Mind Grind "without paying its mana cost," you
			// won't be able to. You must pick 0 as the value of X in the
			// mana cost of a spell being cast "without paying its mana
			// cost," but the X in Mind Grind's mana cost can't be 0.

			boolean zeroIsValidX = false;

			int[] minimums = object.getMinimumX();
			for(int i = 0; i < minimums.length; ++i)
				if(minimums[i] <= 0 || object.getDefinedX() >= minimums[i])
				{
					zeroIsValidX = true;
					break;
				}

			if(!zeroIsValidX)
				return false;

			java.util.Set<EventFactory> factories = new java.util.HashSet<EventFactory>();
			factories.addAll(parameters.get(Parameter.ALTERNATE_COST).getAll(EventFactory.class));

			CostCollection costs = parameters.get(Parameter.ALTERNATE_COST).getOne(CostCollection.class);
			if(costs != null)
				factories.addAll(costs.events);

			for(EventFactory cost: factories)
				if(!cost.createEvent(event.game, null).attempt(event))
					return false;
		}

		return true;
	}

	public boolean checkTargets(Game game, GameObject object, java.util.List<Target> targets)
	{
		return this.checkTargets(game, object, targets, new java.util.HashSet<Integer>());
	}

	public boolean checkTargets(Game game, GameObject object, java.util.List<Target> targets, java.util.Set<Integer> ignoreThese)
	{
		if(targets.size() == 0)
			return true;

		Target target = targets.remove(0);
		Set choices = target.legalChoicesNow(game, object);

		for(Identified potentialTarget: choices.getAll(Identified.class))
		{
			if(ignoreThese.contains(potentialTarget.ID))
				continue;
			if(target.restrictFromLaterTargets)
				ignoreThese.add(potentialTarget.ID);
			if(this.checkTargets(game, object, targets, ignoreThese))
				return true;
			ignoreThese.remove(potentialTarget.ID);
		}

		targets.add(0, target);

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		// Rule 409!
		GameObject beingPlayed = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		// 601.2a The player announces that he or she is casting the spell.
		// That card (or that copy of a card) physically moves from the zone
		// it's in to the stack. It becomes the topmost object on the stack.
		// 602.2a The player announces that he or she is activating the
		// ability. If an activated ability is being played from a hidden
		// zone, the card that has that ability is revealed. That ability is
		// created on the stack as an object that's not a card. It becomes
		// the topmost object on the stack.
		// TODO : If the ability is being played from a hidden zone,
		// reveal the source of the ability.
		Player playerActing = parameters.get(Parameter.PLAYER).getOne(Player.class).getActual();
		GameObject onStack = null;
		if(parameters.containsKey(Parameter.FACE_DOWN))
			onStack = beingPlayed.putOnStack(playerActing, parameters.get(Parameter.FACE_DOWN).getOne(Class.class));
		else
		{
			// ask the player which side to cast.
			java.util.Set<Integer> splitIndices = parameters.get(Parameter.EFFECT).getAll(Integer.class);
			java.util.List<Characteristics> characteristics = java.util.Arrays.asList(beingPlayed.getCharacteristics());
			boolean canFuse = beingPlayed.fuseable && (splitIndices.size() == characteristics.size());

			java.util.Set<Integer> splitDecision = //
			playerActing.sanitizeAndChoose(game.actualState, 1, canFuse ? characteristics.size() : 1, characteristics,//
			PlayerInterface.ChoiceType.CHARACTERISTICS, PlayerInterface.ChooseReason.CAST_SPLIT_CARD)//
			.stream().map(c -> characteristics.indexOf(c))//
			.collect(java.util.stream.Collectors.toSet());

			onStack = beingPlayed.putOnStack(playerActing, splitDecision);
		}
		GameObject physicalOnStack = onStack.getPhysical();

		if(parameters.containsKey(Parameter.ACTION))
			physicalOnStack.castAction = parameters.get(Parameter.ACTION).getOne(CastSpellAction.class);

		playerActing = playerActing.getActual();

		if(onStack.isManaAbility())
			game.physicalState.currentlyResolvingManaAbilities.add((NonStaticAbility)onStack);
		else if(onStack.isSpell())
		{
			onStack.zoneCastFrom = beingPlayed.zoneID;
			onStack.playerCasting = playerActing.ID;
			physicalOnStack.zoneCastFrom = beingPlayed.zoneID;
			physicalOnStack.playerCasting = playerActing.ID;
		}
		// physicalOnStack.setController(playerActing);
		// onStack.setController(playerActing);
		// [a spell/ability's controller is now required to be set by the
		// time GameObject.putOnStack finishes. -RulesGuru]

		// 602.2b The remainder of the process for activating an ability is
		// identical to the process for casting a spell listed in rules
		// 601.2b-h.

		// 601.2b -- Modal spells, X in cost, alternate costs, choosing
		// which color to pay for a hybrid cost, splice
		java.util.Set<EventFactory> factories = new java.util.LinkedHashSet<EventFactory>();
		java.util.Set<Event> costs = new java.util.LinkedHashSet<Event>();
		ManaPool totalManaCost = new ManaPool();

		Set forcedAltCost = parameters.get(Parameter.ALTERNATE_COST);

		if(forcedAltCost != null)
		{
			// alternate mana costs are handled below
			factories.addAll(forcedAltCost.getAll(EventFactory.class));

			java.util.Set<ManaSymbol> altMana = forcedAltCost.getAll(ManaSymbol.class);
			totalManaCost = new ManaPool(altMana);

			CostCollection chosenCost = forcedAltCost.getOne(CostCollection.class);
			if(chosenCost != null)
			{
				onStack.setAlternateCost(chosenCost);
				physicalOnStack.setAlternateCost(chosenCost);

				factories.addAll(chosenCost.events);
				totalManaCost.addAll(chosenCost.manaCost);
			}
		}
		else
		{
			ManaPool accumulatedManaCost = new ManaPool();
			for(ManaPool pool: onStack.getManaCost())
			{
				if(null == pool)
				{
					accumulatedManaCost = null;
					break;
				}

				accumulatedManaCost.addAll(pool);
			}

			CostCollection chosenCost = (accumulatedManaCost == null ? null : new CostCollection(CostCollection.TYPE_MANA, accumulatedManaCost));
			if(onStack.alternateCosts != null && !onStack.alternateCosts.isEmpty())
			{
				java.util.Set<CostCollection> choices = new java.util.HashSet<CostCollection>();
				for(AlternateCost alt: onStack.alternateCosts)
					if(alt.playersMayPay.contains(playerActing))
						choices.add(alt.cost);
				if(chosenCost != null)
					choices.add(chosenCost);
				chosenCost = playerActing.sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.ALTERNATE_COST, PlayerInterface.ChooseReason.OPTIONAL_ALTERNATE_COST).get(0);
				if(!chosenCost.type.equals(CostCollection.TYPE_MANA))
				{
					onStack.setAlternateCost(chosenCost);
					physicalOnStack.setAlternateCost(chosenCost);
				}
			}

			if(chosenCost == null)
				return false;

			if(!chosenCost.manaCost.isEmpty())
				totalManaCost.addAll(chosenCost.manaCost);

			factories.addAll(chosenCost.events);
		}

		if(!onStack.optionalAdditionalCosts.isEmpty())
		{
			boolean valid = false;
			java.util.Collection<CostCollection> extras = null;
			java.util.Map<CostCollection, Integer> frequencies = null;
			while(!valid)
			{
				PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(0, null, PlayerInterface.ChoiceType.ALTERNATE_COST, PlayerInterface.ChooseReason.OPTIONAL_ADDITIONAL_COST);
				chooseParameters.allowMultiples = true;
				extras = playerActing.sanitizeAndChoose(game.actualState, onStack.optionalAdditionalCosts, chooseParameters);
				frequencies = new java.util.HashMap<CostCollection, Integer>();

				// validate that the player only multiply chose costs if
				// it's allowed
				valid = true;
				for(CostCollection extra: extras)
				{
					if(frequencies.containsKey(extra))
					{
						if(!extra.allowMultiples)
						{
							valid = false;
							break;
						}
						frequencies.put(extra, frequencies.get(extra) + 1);
					}
					else
						frequencies.put(extra, 1);
				}
			}

			for(java.util.Map.Entry<CostCollection, Integer> entry: frequencies.entrySet())
			{
				totalManaCost.addAll(entry.getKey().manaCost.duplicate(entry.getValue()));
				factories.addAll(entry.getKey().events);
			}
			for(GameObject o: onStack.andPhysical())
				o.getOptionalAdditionalCostsChosen()[0].addAll(extras);
		}

		// "As an additional cost to cast..." costs apply even when a forced
		// alternate cost is specified.
		factories.addAll(onStack.getCosts());

		// Splice
		ManaPool spliceMana = new ManaPool();
		boolean spliced = false;
		if(onStack.isSpell())
		{
			java.util.Map<GameObject, CostCollection> splicables = new java.util.HashMap<GameObject, CostCollection>();
			cards: for(GameObject card: playerActing.getHand(game.actualState).objects)
				for(Keyword k: card.getKeywordAbilities())
					if(k.isSplice())
					{
						org.rnd.jmagic.abilities.keywords.Splice splice = (org.rnd.jmagic.abilities.keywords.Splice)k;
						if(onStack.getSubTypes().contains(splice.getSubType()))
						{
							if(card.getModes()[0].iterator().next().canBeChosen(game, onStack))
								splicables.put(card, splice.getCost());
							continue cards;
						}
					}

			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(0, splicables.size(), PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.SPLICE_OBJECTS);
			chooseParameters.thisID = onStack.ID;
			java.util.List<GameObject> splice = playerActing.sanitizeAndChoose(game.actualState, splicables.keySet(), chooseParameters);

			if(!splice.isEmpty())
			{
				java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
				revealParameters.put(Parameter.CAUSE, new Set(game));
				revealParameters.put(Parameter.OBJECT, Set.fromCollection(splice));
				Event revealSplices = createEvent(game, "Reveal " + splice, REVEAL, revealParameters);
				revealSplices.perform(event, true);

				playerActing = playerActing.getActual();
				onStack = onStack.getActual();

				// 702.44b ... If you're splicing more than one card onto a
				// spell, reveal them all at once and choose the order in
				// which their instructions will be followed.
				if(splice.size() > 1)
					splice = playerActing.sanitizeAndChoose(game.actualState, splice.size(), splice, PlayerInterface.ChoiceType.OBJECTS_ORDERED, PlayerInterface.ChooseReason.SPLICE_ORDER);

				for(GameObject object: splice)
				{
					spliced = true;
					object.getActual().spliceOnto(physicalOnStack);

					CostCollection spliceCost = splicables.get(object);
					spliceMana.addAll(spliceCost.manaCost);
					for(EventFactory cost: spliceCost.events)
						costs.add(cost.createEvent(game, onStack));
				}
			}
		}

		// A value of X must be chosen if the mana component of the cost
		// includes X ...
		boolean usesX = totalManaCost.usesX();
		// ... or if some other cost includes X.
		if(!usesX)
			for(EventFactory factory: factories)
				if(factory.willUseX())
				{
					usesX = true;
					break;
				}
		// If a value of X must be chosen, choose it now. This must be done
		// before mode selection so that modes whose selectability depends
		// on the value of X (i.e., Repeal, Profane Command) can determine
		// their selectability correctly. Same with other variable costs.
		if(usesX)
		{
			int newX = onStack.getDefinedX();
			// if X is locked to 'undefined' (like prototype portal with no
			// exiled card), the cost is undefined can't be paid:
			if(newX == -2)
				return false;

			int minX = java.util.Arrays.stream(onStack.getMinimumX()).reduce((l, r) -> {
				return l < r ? r : l;
			}).orElse(0);

			if(newX == -1)
				newX = playerActing.chooseNumber(new org.rnd.util.NumberRange(minX, null), "Choose a value for X.");
			else if(newX < minX)
				return false;
			onStack.setValueOfX(newX);
			onStack.getPhysical().setValueOfX(newX);
			totalManaCost = totalManaCost.expandX(onStack.getValueOfX(), onStack.xRestriction);
		}

		CostCollection chosenManaCost = null;
		{
			java.util.Set<CostCollection> manaCostExplosions = totalManaCost.explode(CostCollection.TYPE_MANA);
			chosenManaCost = manaCostExplosions.iterator().next();
			if(manaCostExplosions.size() > 1)
				chosenManaCost = playerActing.sanitizeAndChoose(game.actualState, 1, manaCostExplosions, PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.CHOOSE_MANA_COST).get(0);
		}

		totalManaCost.clear();
		if(!chosenManaCost.manaCost.isEmpty())
			totalManaCost.addAll(chosenManaCost.manaCost);
		if(!spliceMana.isEmpty())
			totalManaCost.addAll(spliceMana);
		factories.addAll(chosenManaCost.events);

		java.util.Map<Event, EventFactory> generatedCosts = new java.util.HashMap<Event, EventFactory>();

		for(EventFactory costFactory: factories)
		{
			Event cost = costFactory.createEvent(game, onStack);
			costs.add(cost);
			generatedCosts.put(cost, costFactory);
			physicalOnStack.paidCost(costFactory, cost);
		}
		for(Event cost: costs)
			if(!cost.makeChoices(event))
				return false;

		game.refreshActualState();
		onStack = onStack.getActual();
		playerActing = playerActing.getActual();

		Set[] numModes = onStack.getNumModes();
		if(null == Minimum.get(numModes[0]))
		{
			// permanent spell, don't bother selecting modes
		}
		else
		{
			// If there are more modes than the spell is allowed to perform and
			// no splice is involved (PLEASE NO MODAL SPLICE SPELLS OH GOD), ask
			// the player to choose the modes
			int minimum = java.util.Arrays.stream(numModes).mapToInt(t -> Minimum.get(t)).sum();
			int countTotalModes = java.util.Arrays.stream(onStack.getModes()).mapToInt(t -> t.size()).sum();
			if(!spliced && countTotalModes > minimum)
			{
				physicalOnStack.selectModes();

				// It's possible that not enough modes were chosen, for example,
				// if Branching Bolt is cast with no creatures in play
				java.util.List<Integer>[] selectedModeNumbers = physicalOnStack.getSelectedModeNumbers();
				for(int i = 0; i < selectedModeNumbers.length; ++i)
					// Use Intersect with Sets here instead of contains because
					// Intersect does magic with ranges.
					if(Intersect.get(onStack.getNumModes()[i], new Set(selectedModeNumbers[i].size())).isEmpty())
						return false;
			}
			// Otherwise, choose all the modes
			else
			{
				java.util.List<Mode>[] modes = onStack.getModes();
				for(int i = 0; i < modes.length; ++i)
				{
					int n = 1;
					for(Mode mode: modes[i])
					{
						// If we have to choose all the modes, and even one
						// can't be
						// chosen, fail.
						if(!mode.canBeChosen(game, onStack))
							return false;
						physicalOnStack.getSelectedModeNumbers()[i].add(n);
						n++;
					}
				}
			}
		}

		// Copy the selected modes to the actual version
		onStack.setSelectedModeNumbers(physicalOnStack.getSelectedModeNumbers());

		// 601.2c -- Choose targets
		game.refreshActualState();
		onStack = game.actualState.getByIDObject(onStack.ID);

		if(!onStack.selectTargets())
			return false;

		java.util.List<Mode>[] modes = onStack.getModes();
		for(int i = 0; i < modes.length; ++i)
		{
			int modeNumber = 1;
			for(Mode mode: modes[i])
			{
				if(!onStack.getSelectedModeNumbers()[i].contains(modeNumber))
					continue;
				if(null == mode.targets)
					return false;
				for(Target target: mode.targets)
				{
					if(null == target)
						return false;
					java.util.List<Target> chosenTargets = new java.util.LinkedList<Target>(onStack.getChosenTargets()[i].get(target));
					onStack.getPhysical().getChosenTargets()[i].put(target, chosenTargets);
				}
				modeNumber++;
			}
		}

		game.refreshActualState();
		onStack = onStack.getActual();
		playerActing = playerActing.getActual();

		// 601.2d -- Divisions (like Violent Eruption)
		modes = onStack.getModes();
		for(int sideIndex = 0; sideIndex < modes.length; ++sideIndex)
		{
			java.util.List<Integer> selectedModeNumbers = onStack.getSelectedModeNumbers()[sideIndex];
			for(int modeIndex = 0; modeIndex < onStack.getModes()[sideIndex].size(); modeIndex++)
			{
				if(!selectedModeNumbers.contains(modeIndex + 1))
					continue;

				Set division = onStack.getModes()[sideIndex].get(modeIndex).division.evaluate(game, onStack);
				int divisionAmount = division.getOne(Integer.class);
				if(divisionAmount != 0)
				{
					java.util.LinkedList<Target> targets = new java.util.LinkedList<Target>();
					for(Target possibleTarget: onStack.getPhysical().getModes()[sideIndex].get(modeIndex).targets)
						for(Target chosenTarget: onStack.getPhysical().getChosenTargets()[sideIndex].get(possibleTarget))
							targets.add(chosenTarget);
					playerActing.divide(divisionAmount, 1, onStack.ID, division.getOne(String.class), targets);
				}
			}
		}

		// 601.2e -- Determine total cost

		if(onStack.isActivatedAbility())
		{
			ActivatedAbility abilityOnStack = (ActivatedAbility)onStack;
			if(abilityOnStack.costsTap || abilityOnStack.costsUntap)
			{
				// get the physical version of the source in case it changes
				// (IE; BECOMES A GHOST)
				Identified sourceIdentified = ((ActivatedAbility)onStack).getSource(game.physicalState);
				if(!sourceIdentified.isGameObject())
					throw new IllegalStateException(onStack + ": Attempt to " + (abilityOnStack.costsTap ? "tap " : "untap ") + sourceIdentified + " which isn't an object");

				GameObject source = ((GameObject)sourceIdentified);
				if(source.zoneID != game.actualState.battlefield().ID)
					throw new IllegalStateException(onStack + ": Attempt to " + (abilityOnStack.costsTap ? "tap " : "untap ") + source + " which isn't a permanent");

				java.util.Map<Parameter, Set> costParameters = new java.util.HashMap<Parameter, Set>();
				costParameters.put(Parameter.CAUSE, new Set(playerActing));
				costParameters.put(Parameter.OBJECT, new Set(source));

				if(abilityOnStack.costsTap)
				{
					Event cost = createEvent(game, "(T)", EventType.TAP_PERMANENTS, costParameters);
					costs.add(cost);
					physicalOnStack.paidCost(GameObject.TAP_COST_FACTORY, cost);
				}
				if(abilityOnStack.costsUntap)
				{
					Event cost = createEvent(game, "(Q)", EventType.UNTAP_PERMANENTS, costParameters);
					costs.add(cost);
					physicalOnStack.paidCost(GameObject.UNTAP_COST_FACTORY, cost);
				}
			}
		}

		// These two things can be affected by choices made for additional
		// costs.
		// (damn you convoke)
		game.refreshActualState();
		playerActing = playerActing.getActual();

		// additional costs
		for(java.util.Map.Entry<Set, ManaPool> costAddition: game.actualState.manaCostAdditions.entrySet())
			if(costAddition.getKey().contains(onStack))
				totalManaCost.addAll(costAddition.getValue());

		// cost reductions that only reduce colored mana
		for(java.util.Map.Entry<Set, ManaPool> costReduction: game.actualState.manaCostColoredReductions.entrySet())
			if(costReduction.getKey().contains(onStack))
			{
				ManaPool reduction = costReduction.getValue();
				reduction = playerActing.sanitizeAndChoose(game.actualState, 1, reduction.explode(CostCollection.TYPE_REDUCE_COST), PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.COST_REDUCTION).get(0).manaCost;
				totalManaCost.reduceColored(reduction);
			}

		// cost reductions that can't reduce to less than one mana
		for(java.util.Map.Entry<Set, ManaPool> costReduction: game.actualState.manaCostRestrictedReductions.entrySet())
			if(costReduction.getKey().contains(onStack))
			{
				ManaPool reduction = costReduction.getValue();
				reduction = playerActing.sanitizeAndChoose(game.actualState, 1, reduction.explode(CostCollection.TYPE_REDUCE_COST), PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.COST_REDUCTION).get(0).manaCost;
				totalManaCost.reduce(reduction);
				if(totalManaCost.converted() < 1)
					totalManaCost.add(new ManaSymbol(ManaSymbol.ManaType.COLORLESS));
			}

		// cost reductions
		for(java.util.Map.Entry<Set, ManaPool> costReduction: game.actualState.manaCostReductions.entrySet())
			if(costReduction.getKey().contains(onStack))
			{
				ManaPool reduction = costReduction.getValue();
				reduction = playerActing.sanitizeAndChoose(game.actualState, 1, reduction.explode(CostCollection.TYPE_REDUCE_COST), PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.COST_REDUCTION).get(0).manaCost;
				totalManaCost.reduce(reduction);
			}

		// trinisphere's effect
		for(java.util.Map.Entry<Set, Integer> costMinimum: game.actualState.manaCostMinimums.entrySet())
			if(costMinimum.getKey().contains(onStack))
				totalManaCost.minimum(costMinimum.getValue());

		// set up the mana cost payment event
		for(ManaSymbol m: totalManaCost)
			m.sourceID = onStack.ID;

		if(!totalManaCost.isEmpty())
		{
			java.util.Map<Parameter, Set> payManaParameters = new java.util.HashMap<Parameter, Set>();
			payManaParameters.put(Parameter.CAUSE, new Set(game));
			payManaParameters.put(Parameter.OBJECT, new Set(onStack));
			payManaParameters.put(Parameter.COST, Set.fromCollection(totalManaCost));
			payManaParameters.put(Parameter.PLAYER, new Set(playerActing));

			Event payMana = createEvent(game, "Pay " + totalManaCost, PAY_MANA, payManaParameters);
			costs.add(payMana);
			physicalOnStack.paidCost(GameObject.MANA_COST_FACTORY, payMana);

			// 601.2f If the total cost includes a mana payment, the player
			// then has a chance to activate mana abilities
			playerActing.mayActivateManaAbilities();
		}

		// 601.2g -- The player pays the costs in the order of his or
		// her choosing
		if(0 < costs.size())
		{
			int numCosts = costs.size();
			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(numCosts, numCosts, PlayerInterface.ChoiceType.COSTS, PlayerInterface.ChooseReason.ORDER_COSTS);
			chooseParameters.thisID = onStack.ID;
			for(Event cost: playerActing.getActual().sanitizeAndChoose(game.actualState, costs, chooseParameters))
			{
				cost.isCost = true;
				cost.isEffect = false;
				if(!cost.perform(event, true))
					return false;
			}
		}

		// 601.2h Once the steps described in 601.2a-g are completed, the
		// spell becomes cast. Any abilities that trigger when a spell is
		// cast or put onto the stack trigger at this time.
		java.util.Map<Parameter, Set> becomesPlayedParameters = new java.util.HashMap<Parameter, Set>();
		becomesPlayedParameters.put(Parameter.OBJECT, new Set(onStack));
		becomesPlayedParameters.put(Parameter.PLAYER, new Set(playerActing));
		createEvent(game, onStack + " has been played.", BECOMES_PLAYED, becomesPlayedParameters).perform(event, false);

		event.setResult(Identity.instance(onStack));
		return true;
	}
}